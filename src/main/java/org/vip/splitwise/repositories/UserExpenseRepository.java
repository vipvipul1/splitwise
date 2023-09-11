package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vip.splitwise.models.UserExpense;

import java.util.List;

public interface UserExpenseRepository extends JpaRepository<UserExpense, String> {

    @Query("select ge from UserExpense ue inner join GroupExpense ge on ue.expense=ge.expense where ge.group=?1")
    List<UserExpense> findAllByGroupId(String groupId);
}
