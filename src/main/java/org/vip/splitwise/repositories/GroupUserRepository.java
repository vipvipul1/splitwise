package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.splitwise.models.GroupUser;
import org.vip.splitwise.models.GroupUserId;

public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserId> {
}
