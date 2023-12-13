package com.geobruit.campus.market.project.repository;

import com.geobruit.campus.market.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
