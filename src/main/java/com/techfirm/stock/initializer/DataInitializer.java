package com.techfirm.stock.initializer;

import com.techfirm.stock.model.Role;
import com.techfirm.stock.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleService roleService;

    @PostConstruct
    public void init() {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");
        createRoleIfNotExists("RECEPTION");
    }

    private void createRoleIfNotExists(String roleTitle) {
        try {
            roleService.getRoleByTitle(roleTitle);
        } catch (IllegalArgumentException e) {
            Role newRole = new Role();
            newRole.setRoleTitle(roleTitle);
            roleService.createRole(newRole);
        }
    }
}
