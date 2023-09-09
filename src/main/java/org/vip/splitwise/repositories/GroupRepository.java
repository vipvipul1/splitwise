package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.splitwise.models.Group;

public interface GroupRepository extends JpaRepository<Group, String> {
}
