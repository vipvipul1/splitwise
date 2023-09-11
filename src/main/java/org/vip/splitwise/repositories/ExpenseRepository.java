package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.splitwise.models.Expense;
import org.vip.splitwise.models.UserExpense;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
}
