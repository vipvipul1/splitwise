package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.GroupExpense;
import org.vip.splitwise.models.UserExpense;

import java.util.List;

public interface GroupExpenseRepository extends JpaRepository<GroupExpense, String> {
}
