package com.geobruit.campus.market.project.init;

import com.geobruit.campus.market.project.model.Role;
import com.geobruit.campus.market.project.repository.RoleRepository;
import com.geobruit.campus.market.project.security.UserRole;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        initializeRoles();
    }

    private void initializeRoles() {
        for (UserRole userRole : UserRole.values()) {
            if (roleRepository.findByName(userRole.name()) == null) {
                roleRepository.save(new Role(userRole.name()));
            }
        }
    }
}

