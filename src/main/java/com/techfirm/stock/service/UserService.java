package com.techfirm.stock.service;

import com.techfirm.stock.model.Address;
import com.techfirm.stock.model.Role;
import com.techfirm.stock.model.User;
import com.techfirm.stock.repository.AddressRepository;
import com.techfirm.stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AddressRepository addressRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Page<User> getAllUser2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return userRepository.findAll(pageable);
    }

    public Page<User> getAllUsers3(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Page<User> searchUserByFilter(String firstName, String userName, String password, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByFirstNameContainingOrUserNameContainingOrPasswordContaining(firstName,userName,password,pageable);
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
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

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
            }
        };
    }
    public User save (User newUser){
        if(newUser.getId() == null){
            newUser.setCreatedAt(LocalDateTime.now());
        }
        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }
}
