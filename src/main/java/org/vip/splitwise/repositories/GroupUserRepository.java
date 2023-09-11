package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.GroupUser;
import org.vip.splitwise.models.GroupUserId;
import org.vip.splitwise.models.User;

import java.util.List;

public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserId> {

    List<GroupUser> findAllByGroupAndUserIsNotIn(Group group, List<User> users);
}
