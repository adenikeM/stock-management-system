package com.techfirm.stock.controller;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.model.Role;
import com.techfirm.stock.service.LocationService;
import com.techfirm.stock.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static com.techfirm.stock.utils.Validation.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("api/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRole() {
        return ResponseEntity.ok().body(roleService.getAllRole());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleByID(@PathVariable Integer id) {
        log.info("Get Role id by " + id);
        if (id < 1) {
            ResponseEntity.badRequest().body(
                    buildErrorResponse("Role id cannot be less than 1", BAD_REQUEST));
        }
        return roleService.getRole(id)
                          .map(role -> ResponseEntity.ok().body(role))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        log.info("Request to create role => {}", role);
        if (role.getId() != null) {
            log.info("user role => {}", role);
            return validateCreateRoleRequest(role);
        }
        return ResponseEntity.ok().body(roleService.createRole(role));
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        if (role.getId() == null) {
            return validateUpdateRole(role);
        }
        Optional<Role> updatedRole = roleService.updateRole(role);
        if (updatedRole.isPresent()) {
            return ResponseEntity.ok(updatedRole);
        } else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Role with id " + role.getId() + "doesn't exist, Enter correct role id", BAD_REQUEST));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
