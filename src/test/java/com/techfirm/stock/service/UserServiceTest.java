package com.techfirm.stock.service;

import com.techfirm.stock.model.Address;
import com.techfirm.stock.model.Role;
import com.techfirm.stock.model.User;
import com.techfirm.stock.repository.AddressRepository;
import com.techfirm.stock.repository.RoleRepository;
import com.techfirm.stock.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private RoleService roleService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository,roleService,addressRepository );
    }

    @Test
    void canGetAllUser() {
        //when
        userService.getAllUser();
        //then
        verify(userRepository).findAll();
    }

    @Test
    void canCreateUser() {
        //given
        Address address = new Address();
        address.setAddress("Oloje Estate");
        address.setCity("Ilorin");

        Role role = new Role();
        role.setRoleTitle("admin");

        User user = new User("Owolabi","Ade","Toks","Ade@gmail.com","Tokss@19",address,role);

        // when
        userService.createUser(user);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);

    }

}