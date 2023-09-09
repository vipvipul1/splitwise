package org.vip.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vip.splitwise.models.User;

public interface UserRepository extends JpaRepository<User, String> {

}
