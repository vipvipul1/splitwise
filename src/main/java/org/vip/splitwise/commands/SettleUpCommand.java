package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.vip.splitwise.controllers.SettleUpController;
import org.vip.splitwise.dtos.SettleUpResponseDto;
import org.vip.splitwise.dtos.SettleUpTrxn;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class SettleUpCommand implements Command {
    /*
        Expected Input:     u1 SettleUp (User u1 is asking to see the list of transactions she should perform to settle up)
        Expected Input:     u1 SettleUp g1 (User u1 is asking to see the list of transactions she should perform to settle up in Group - g1)
        Expected Output:    SUCCESS. List of transactions to be performed to settle up are:-
                                From    To      Amount
                            1.  u1      u2      200
                            2.  u3      u1      100
    */
    private SettleUpController settleUpController;
    private int FROM_SPACE = 9;
    private int TO_SPACE = 7;

    @Autowired
    public SettleUpCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length >= 2 && CommandType.SETTLE_UP.getLabel().equals(params[1]);
    }

    @Override
    public void execute(String input) {
        String[] params = input.split(" ");
        String userId = params[0];
        String groupId = null;
        if (params.length == 3) {
            groupId = params[2];
        }
        SettleUpResponseDto responseDto = settleUpController.getAllSettleUpTransactions(userId, groupId);
        String displayMember;
        if (groupId == null) {
            displayMember = "User: '" + userId + "'";
        } else {
            displayMember = "Group: '" + responseDto.getGroup().getName() + "'";
        }
        if (responseDto.getResponseCode() == HttpStatus.OK) {
            if (responseDto.getSettleUpTrxns().isEmpty()) {
                System.out.println(displayMember + " already settled up.");
            } else {
                System.out.println("Below are the transactions to be performed to settle up " + displayMember);
                System.out.println("|From|   |To|   |Amount|");
                for (SettleUpTrxn settleUpTrxn : responseDto.getSettleUpTrxns()) {
                    System.out.print(" " + settleUpTrxn.getFromUserId());
                    System.out.print(getSpaces(FROM_SPACE - settleUpTrxn.getFromUserId().length() - 1));
                    System.out.print(" " + settleUpTrxn.getToUserId());
                    System.out.print(getSpaces(TO_SPACE - settleUpTrxn.getToUserId().length() - 1));
                    System.out.println(" " + settleUpTrxn.getAmount());
                }
            }
        } else {
            System.out.println(responseDto.getResponseMsg());
        }
    }

    private String getSpaces(int n) {
        StringBuilder spaces = new StringBuilder("");
        while (n-- > 0) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
}
