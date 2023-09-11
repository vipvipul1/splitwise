package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vip.splitwise.contants.PayType;
import org.vip.splitwise.contants.ShareType;
import org.vip.splitwise.controllers.ExpenseController;
import org.vip.splitwise.dtos.ExpenseRequestDto;
import org.vip.splitwise.dtos.ExpenseResponseDto;
import org.vip.splitwise.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseCommand implements Command {
    /*
        (refer "Design Splitwise.pdf" for explanation)
        Expected Input:     u1 Expense g1 iPay 1000 Equal Desc Wifi Bill (g1 - u1, u2, u3)
        Expected Input:     u1 Expense u3 g1 multiPay 1000 2000 Exact 900 800 600 700 Desc Movie (g1 - u1, u2, u3, u4)
        Expected Input:     u1 Expense u2 u3 u4 iPay 900 Equal Desc Last night dinner
        Expected Input:     u1 Expense u2 u3 iPay 1000 Percent 20 30 50 Desc House rent
        Expected Input:     u1 Expense u2 u3 u4 iPay 1000 Ratio 1 2 3 4 Desc Goa trip
        Expected Input:     u1 Expense u2 u3 iPay 1000 Exact 100 300 600 Desc Groceries
        Expected Input:     u1 Expense u2 u3 multiPay 100 300 200 Equal Desc Lunch at office
        Expected Input:     u1 Expense u2 u3 multiPay 500 300 200 Percent 20 30 50 Desc Netflix subscription

        Expected Output:    SUCCESS.
    */
    private ExpenseController expenseController;

    @Autowired
    public ExpenseCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length >= 8 && CommandType.EXPENSE.getLabel().equals(params[1]);
    }

    @Override
    public void execute(String input) {
        String[] params = input.split(" ");
        ExpenseRequestDto requestDto = new ExpenseRequestDto();

        List<String> userIds = new ArrayList<>();
        userIds.add(params[0]);
        int curIndex = 2;
        while (params[curIndex].startsWith("g") || params[curIndex].startsWith("u")) {
            userIds.add(params[curIndex++]);
        }
        requestDto.setUserIds(userIds);
        requestDto.setPayType(PayType.getByLabel(params[curIndex++]));

        List<Double> paidAmounts = new ArrayList<>();
        while (CommonUtil.isNumber(params[curIndex])) {
            paidAmounts.add(Double.parseDouble(params[curIndex++]));
        }
        requestDto.setPaidAmounts(paidAmounts);

        requestDto.setShareType(ShareType.getByLabel(params[curIndex++]));

        List<Integer> shareUnits = new ArrayList<>();
        while (CommonUtil.isNumber(params[curIndex])) {
            shareUnits.add(Integer.parseInt(params[curIndex++]));
        }
        requestDto.setShareUnits(shareUnits);

        curIndex++;
        StringBuilder description = new StringBuilder(params[curIndex++]);
        while (curIndex < params.length) {
            description.append(" ").append(params[curIndex++]);
        }
        requestDto.setDescription(description.toString());

        ExpenseResponseDto responseDto = expenseController.addExpense(requestDto);
        System.out.println(responseDto.getResponseMsg());
    }
}
