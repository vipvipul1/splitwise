package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.splitwise.models.GroupExpense;

public interface GroupExpenseRepository extends JpaRepository<GroupExpense, String> {
}
