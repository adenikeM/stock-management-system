package com.techfirm.stock.controller;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.model.Role;
import com.techfirm.stock.service.LocationService;
import com.techfirm.stock.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("api")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRole() {
        return ResponseEntity.ok().body(roleService.getAllRole());
    }

    @GetMapping("v2/roles")
    public ResponseEntity<List<Role>> getAllRole2(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "0") int pageSize){

        Page<Role> roles = roleService.getAllRole2(pageNo,pageSize);
        return ResponseEntity.ok(roles.getContent());
    }

    @GetMapping("/v3/roles")
    public ResponseEntity<List<Role>> getAllRole3(Pageable pageable ){
        Page<Role> roles = roleService.getAllRole3(pageable);
        return ResponseEntity.ok(roles.getContent());
    }
    @GetMapping("/roles/{id}")
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

    @PostMapping("/roles")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        log.info("Request to create role => {}", role);
        if (role.getId() != null) {
            log.info("user role => {}", role);
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID should be null, Id = "
                                         + role.getId(), HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok().body(roleService.createRole(role));
    }

    @PutMapping("/roles")
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        if (role.getId() == null) {
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID cannot be null, Id = "
                                         + role.getId(), HttpStatus.BAD_REQUEST));
        }
        Optional<Role> updatedRole = roleService.updateRole(role);
        if (updatedRole.isPresent()) {
            return ResponseEntity.ok(updatedRole);
        } else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Role with id " + role.getId() + "doesn't exist, Enter correct role id", BAD_REQUEST));
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
