package org.vip.splitwise.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.vip.splitwise.contants.ShareType;
import org.vip.splitwise.dtos.ExpenseRequestDto;
import org.vip.splitwise.dtos.ExpenseResponseDto;
import org.vip.splitwise.dtos.UserResponseDto;
import org.vip.splitwise.models.User;
import org.vip.splitwise.models.UserExpense;
import org.vip.splitwise.services.ExpenseService;
import org.vip.splitwise.services.UserService;
import org.vip.splitwise.strategies.EqualShareStrategy;
import org.vip.splitwise.strategies.ShareStrategy;

import java.util.List;

@Controller
public class ExpenseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public ExpenseResponseDto addExpense(ExpenseRequestDto requestDto) {
        ExpenseResponseDto responseDto = new ExpenseResponseDto();
        try {
            List<UserExpense> userExpenses = expenseService.addExpense(requestDto);
            responseDto.setUserExpenses(userExpenses);
            responseDto.setResponseCode(HttpStatus.OK);
            responseDto.setResponseMsg("SUCCESS");
        } catch (Exception e) {
            LOGGER.error("Error in ExpenseController -> addExpense() : " + e.getMessage());
            responseDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDto.setResponseMsg("FAILURE. Reason: " + e.getMessage());
        }
        return responseDto;
    }
}
