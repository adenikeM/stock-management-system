package com.techfirm.stock.service;

import com.techfirm.stock.model.Address;
import com.techfirm.stock.model.Role;
import com.techfirm.stock.model.User;
import com.techfirm.stock.repository.AddressRepository;
import com.techfirm.stock.repository.RoleRepository;
import com.techfirm.stock.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.addressRepository = addressRepository;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Page<User> getAllUser2(int page, int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return userRepository.findAll(pageable);
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
       Address address = addressRepository.save(user.getAddress());
        user.setAddress(address);
        Role role = roleService.createRole(user.getUserRole());
        user.setUserRole(role);
        return userRepository.save(user);
    }

    public Optional<User> UpdateUser(User user) {
        userRepository.findById(Math.toIntExact(user.getId()));
        if (user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        Role role = roleService.createRole(user.getUserRole());
        user.setUserRole(role);
        return Optional.of(userRepository.save(user));
    }

    public void deleteUser(Integer id) {
        userRepository.findById(id);
    }
}
