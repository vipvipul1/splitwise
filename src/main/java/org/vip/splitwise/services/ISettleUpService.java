package org.vip.splitwise.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vip.splitwise.dtos.SettleUpResponseDto;
import org.vip.splitwise.dtos.SettleUpTrxn;
import org.vip.splitwise.exceptions.GroupNotFoundException;
import org.vip.splitwise.models.Expense;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.GroupUser;
import org.vip.splitwise.models.UserExpense;
import org.vip.splitwise.repositories.GroupRepository;
import org.vip.splitwise.repositories.GroupUserRepository;
import org.vip.splitwise.repositories.UserExpenseRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ISettleUpService implements SettleUpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IGroupService.class);

    private UserExpenseRepository userExpenseRepo;
    private GroupUserRepository groupUserRepo;
    private GroupRepository groupRepo;

    @Autowired
    public ISettleUpService(UserExpenseRepository userExpenseRepo, GroupUserRepository groupUserRepo, GroupRepository groupRepo) {
        this.userExpenseRepo = userExpenseRepo;
        this.groupUserRepo = groupUserRepo;
        this.groupRepo = groupRepo;
    }

    @Override
    @Transactional
    public SettleUpResponseDto getUserSettleUpTransactionsAll(String userId) {
        SettleUpResponseDto responseDto = new SettleUpResponseDto();
        List<SettleUpTrxn> settleUpTrxns = new ArrayList<>();
        try {
            List<UserExpense> groupUserExpenses = new ArrayList<>();
            List<GroupUser> groupUsers = groupUserRepo.findAllByUserId(userId);
            if (!groupUsers.isEmpty()) {
                List<Group> groups = groupUsers.stream()
                        .map(GroupUser::getGroup)
                        .toList();
                groupUserExpenses = userExpenseRepo.findAllUserExpensesByGroups(groups);
                List<SettleUpTrxn> groupTrxns = populateTransactions(groupUserExpenses, userId);
                settleUpTrxns.addAll(groupTrxns);
            }

            Set<Expense> groupExpenses = groupUserExpenses.stream()
                    .map(UserExpense::getExpense)
                    .collect(Collectors.toSet());
            if (groupExpenses.isEmpty())
                groupExpenses = null;
            List<UserExpense> nonGroupUserExpenses = userExpenseRepo.findAllByUserIdNotInGroupExpense(userId, groupExpenses);
            List<SettleUpTrxn> nonGroupTrxns = populateTransactions(nonGroupUserExpenses, userId);
            settleUpTrxns.addAll(nonGroupTrxns);
        } catch (Exception e) {
            LOGGER.error("Error in ISettleUpService -> getAllUserSettleUpTransactions() : " + e.getMessage());
            throw e;
        }
        responseDto.setSettleUpTrxns(settleUpTrxns);
        return responseDto;
    }

    @Override
    @Transactional
    public SettleUpResponseDto getGroupSettleUpTransactionsAll(String groupId) throws GroupNotFoundException {
        SettleUpResponseDto responseDto = new SettleUpResponseDto();
        try {
            Optional<Group> group = groupRepo.findById(groupId);
            if (group.isEmpty())
                throw new GroupNotFoundException("Group doesn't exist! Try with valid Group.");
            responseDto.setGroup(group.get());
            List<UserExpense> userExpenses = userExpenseRepo.findAllByGroupId(groupId);
            List<SettleUpTrxn> settleUpTrxns = getSettleUpTrxns(userExpenses);
            responseDto.setSettleUpTrxns(settleUpTrxns);
        } catch (Exception e) {
            LOGGER.error("Error in ISettleUpService -> getAllGroupSettleUpTransactions() : " + e.getMessage());
            throw e;
        }
        return responseDto;
    }

    private List<SettleUpTrxn> populateTransactions(List<UserExpense> allUserExpenses, String userId) {
        List<SettleUpTrxn> settleUpTrxns = new ArrayList<>();
        List<SettleUpTrxn> allSettleUpTrxns = getSettleUpTrxns(allUserExpenses);
        for (SettleUpTrxn trxn: allSettleUpTrxns) {
            if (trxn.getToUserId().equals(userId) || trxn.getFromUserId().equals(userId)) {
                settleUpTrxns.add(trxn);
            }
        }
        return settleUpTrxns;
    }

    private List<SettleUpTrxn> getSettleUpTrxns(List<UserExpense> userExpenses) {
        Map<String, Double> paidUsersTotal = new HashMap<>();
        Map<String, Double> hadToPayUsersTotal = new HashMap<>();
        for (UserExpense userExpense: userExpenses) {
            String userId = userExpense.getUser().getId();
            paidUsersTotal.put(userId, paidUsersTotal.getOrDefault(userId, 0D) + userExpense.getPaid());
            hadToPayUsersTotal.put(userId, hadToPayUsersTotal.getOrDefault(userId, 0D) + userExpense.getHadToPay());
        }

        PriorityQueue<Pair<String, Double>> toReceiveUsers
                = new PriorityQueue<>((p1, p2) -> p2.getSecond().compareTo(p1.getSecond()));
        PriorityQueue<Pair<String, Double>> toGiveUsers
                = new PriorityQueue<>((p1, p2) -> p2.getSecond().compareTo(p1.getSecond()));
        for (String paidUserId : paidUsersTotal.keySet()) {
            Double paidAmount = paidUsersTotal.get(paidUserId);
            Double hadToPayAmount = hadToPayUsersTotal.get(paidUserId);
            double diff = paidAmount - hadToPayAmount;
            if (diff != 0) {
                if (diff > 0) {
                    toReceiveUsers.add(Pair.of(paidUserId, diff));
                } else {
                    toGiveUsers.add(Pair.of(paidUserId, -diff));
                }
            }
        }
        List<SettleUpTrxn> settleUpTrxns = new ArrayList<>();
        while (!toReceiveUsers.isEmpty()) {
            SettleUpTrxn trxn = new SettleUpTrxn();
            Pair<String, Double> toReceive = toReceiveUsers.remove();
            Pair<String, Double> toGive = toGiveUsers.remove();
            double diff = toReceive.getSecond() - toGive.getSecond();
            trxn.setFromUserId(toGive.getFirst());
            trxn.setToUserId(toReceive.getFirst());
            if (diff > 0) {
                trxn.setAmount(toGive.getSecond());
                toReceiveUsers.add(Pair.of(toReceive.getFirst(), diff));
            } else if (diff < 0) {
                trxn.setAmount(toReceive.getSecond());
                toGiveUsers.add(Pair.of(toGive.getFirst(), -diff));
            } else {
                trxn.setAmount(toReceive.getSecond());
            }
            settleUpTrxns.add(trxn);
        }
        return settleUpTrxns;
    }

    @Override
    @Transactional
    public Double getUserTotalOutstanding(String userId) {
        double outstanding;
        try {
            List<SettleUpTrxn> settleUpTrxns = getUserSettleUpTransactionsAll(userId).getSettleUpTrxns();
            double toGive = 0.0, toReceive = 0.0;
            for (SettleUpTrxn trxn : settleUpTrxns) {
                if (trxn.getFromUserId().equals(userId)) {
                    toGive += trxn.getAmount();
                } else {
                    toReceive += trxn.getAmount();
                }
            }
            // +ve outstanding means user needs to give to others. -ve means user will get from others.
            outstanding = toGive - toReceive;
        } catch (Exception e) {
            LOGGER.error("Error in ISettleUpService -> getUserTotalOutstanding() : " + e.getMessage());
            throw e;
        }
        return outstanding;
    }
}
