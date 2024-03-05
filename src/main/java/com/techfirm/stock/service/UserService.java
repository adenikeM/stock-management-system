package com.techfirm.stock.service;

import com.techfirm.stock.model.Role;
import com.techfirm.stock.model.User;
import com.techfirm.stock.repository.RoleRepository;
import com.techfirm.stock.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public Optional<User> getUser(Integer id){ return userRepository.findById(id);}
    public User createUser(User user){
        Role role = savedRoleWithRepo(user.getUserRole());
        user.setUserRole(role);
        return userRepository.save(user);
    }
    private Role savedRoleWithRepo(Role role) {
        return roleRepository.save(role);
    }
    public Optional<User> UpdateUser(User user){
        userRepository.findById(Math.toIntExact(user.getId()));
        if(user.getId() == null){
            throw  new IllegalArgumentException("User id cannot be null");
        }
        Role role = savedRoleWithRepo(user.getUserRole());
        user.setUserRole(role);
        return Optional.of(userRepository.save(user));
    }
    public void deleteUser(Integer id){userRepository.findById(id);}
}
