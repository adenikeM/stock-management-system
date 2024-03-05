package com.techfirm.stock.controller;

import com.techfirm.stock.model.User;
import com.techfirm.stock.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static com.techfirm.stock.utils.Validation.validateCreateUserRequest;
import static com.techfirm.stock.utils.Validation.validateUpdateUser;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Integer id){
        log.info("Get user id by " + id);
        if(id < 1){
            throw new IllegalArgumentException("User ID cannot be less than 1, please input correct ID");
        }
        return userService.getUser(id)
                          .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        log.info("Request to create user => {}", user);
        if(user.getId() != null){
            log.info("user => {}", user);
            return validateCreateUserRequest(user);
        }
        return ResponseEntity.ok().body(userService.createUser(user));
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        if(user.getId() == null){
            return validateUpdateUser(user);
        }
        Optional<User> updatedUser = userService.UpdateUser(user);
        if(updatedUser.isPresent()){
            return ResponseEntity.ok(updatedUser);
        }
        else{
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "User with id " + user.getId() + "doesn't exist, Input correct User ID ", BAD_REQUEST));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
