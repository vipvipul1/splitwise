package org.vip.splitwise.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vip.splitwise.contants.ShareType;
import org.vip.splitwise.dtos.ExpenseRequestDto;
import org.vip.splitwise.models.*;
import org.vip.splitwise.repositories.ExpenseRepository;
import org.vip.splitwise.repositories.GroupExpenseRepository;
import org.vip.splitwise.repositories.GroupUserRepository;
import org.vip.splitwise.repositories.UserExpenseRepository;
import org.vip.splitwise.strategies.ShareStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IExpenseService implements ExpenseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IExpenseService.class);

    private BeanFactory beanFactory;
    private GroupUserRepository groupUserRepo;
    private ExpenseRepository expenseRepo;
    private UserExpenseRepository userExpenseRepo;
    private GroupExpenseRepository groupExpenseRepo;

    @Autowired
    public IExpenseService(BeanFactory beanFactory, GroupUserRepository groupUserRepo, ExpenseRepository expenseRepo,
                           UserExpenseRepository userExpenseRepo, GroupExpenseRepository groupExpenseRepo) {
        this.beanFactory = beanFactory;
        this.groupUserRepo = groupUserRepo;
        this.expenseRepo = expenseRepo;
        this.userExpenseRepo = userExpenseRepo;
        this.groupExpenseRepo = groupExpenseRepo;
    }

    @Override
    @Transactional
    public List<UserExpense> addExpense(ExpenseRequestDto requestDto) {
        List<UserExpense> savedUserExpenses = null;
        try {
            List<String> userIds = requestDto.getUserIds();

            // Saving Expense info
            Expense expense = new Expense();
            expense.setDescription(requestDto.getDescription());
            Double totalAmount = requestDto.getPaidAmounts().stream()
                    .mapToDouble(Double::valueOf)
                    .reduce(0, Double::sum);
            expense.setAmount(totalAmount);
            expense.setCreatedOn(LocalDateTime.now());
            expense.setCreatedBy(User.builder().setId(userIds.get(0)).build());
            expense.setExpenseType(ExpenseType.REAL);
            Expense savedExpense = expenseRepo.save(expense);

            // Saving Group_Expense info if it's a group expense
            String lastId = userIds.get(userIds.size() - 1);
            if (lastId.startsWith("g")) {
                GroupExpense groupExpense = new GroupExpense();
                groupExpense.setExpense(savedExpense);
                groupExpense.setGroup(Group.builder().setId(lastId).build());
                groupExpenseRepo.save(groupExpense);
            }

            // Saving each User_Expense info
            if (lastId.startsWith("g")) {
                userIds.remove(userIds.size() - 1);
                Group group = Group.builder().setId(lastId).build();
                List<User> paidUsers = userIds.stream()
                        .map(id -> User.builder().setId(id).build())
                        .toList();
                List<GroupUser> notPaidUsers = groupUserRepo.findAllByGroupAndUserIsNotIn(group, paidUsers);
                userIds.addAll(notPaidUsers.stream().map(u -> u.getUser().getId()).toList());
            }
            List<UserExpense>  userExpenses = getAllUserExpenses(requestDto, userIds, savedExpense);

            savedUserExpenses = userExpenseRepo.saveAll(userExpenses);
        } catch (Exception e) {
            LOGGER.error("Error in IExpenseService -> addExpense() : " + e.getMessage());
            throw e;
        }
        return savedUserExpenses;
    }

    private List<UserExpense> getAllUserExpenses(ExpenseRequestDto requestDto, List<String> userIds, Expense savedExpense) {
        ShareStrategy shareStrategy = getShareStrategy(requestDto.getShareType());
        List<Double> paidAmounts = requestDto.getPaidAmounts();
        List<Integer> shareUnits = requestDto.getShareUnits();
        Map<String, Double> usersShare = shareStrategy.getAllUsersShare(userIds, paidAmounts, shareUnits);

        List<UserExpense> userExpenses = new ArrayList<>();
        for (int i = 0; i < userIds.size(); i++) {
            UserExpense userExpense = new UserExpense();
            userExpense.setUser(User.builder().setId(userIds.get(i)).build());
            userExpense.setHadToPay(usersShare.get(userIds.get(i)));
            userExpense.setPaid(0D);
            if (i < paidAmounts.size()) {
                userExpense.setPaid(paidAmounts.get(i));
            }
            userExpense.setExpense(savedExpense);
            userExpenses.add(userExpense);
        }
        return userExpenses;
    }

    private ShareStrategy getShareStrategy(ShareType shareType) {
        return beanFactory.getBean(shareType.getLabel() + ShareStrategy.class.getSimpleName(),
                ShareStrategy.class);
    }
}
