package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vip.splitwise.models.Expense;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.UserExpense;

import java.util.List;
import java.util.Set;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, String> {

    @Query("select ue from UserExpense ue inner join GroupExpense ge on ue.expense=ge.expense where ge.group.id=?1")
    List<UserExpense> findAllByGroupId(String groupId);

    @Query("select ue from UserExpense ue inner join GroupExpense ge on ue.expense=ge.expense where ge.group in (?1)")
    List<UserExpense> findAllUserExpensesByGroups(List<Group> groups);

    @Query("select ue from UserExpense ue " +
            "where ue.expense in (select e.expense from UserExpense e where e.user.id = ?1) " +
            "and ((ue.expense not in (?2)) or (?2 is null))")
    List<UserExpense> findAllByUserIdNotInGroupExpense(String userId, Set<Expense> groupUserExpenses);
}
