package com.geobruit.campus.market.project.repository;

import com.geobruit.campus.market.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long userId);
    User findByEmail(String email);

    User findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

}
