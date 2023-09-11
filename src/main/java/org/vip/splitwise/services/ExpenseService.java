package org.vip.splitwise.services;

import org.vip.splitwise.dtos.ExpenseRequestDto;
import org.vip.splitwise.models.UserExpense;

import java.util.List;

public interface ExpenseService {
    List<UserExpense> addExpense(ExpenseRequestDto requestDto);
}
