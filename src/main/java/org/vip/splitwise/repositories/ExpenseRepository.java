package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vip.splitwise.models.Expense;
import org.vip.splitwise.models.UserExpense;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
}
