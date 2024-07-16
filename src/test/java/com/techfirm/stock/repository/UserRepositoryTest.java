package com.techfirm.stock.repository;

import com.techfirm.stock.model.Address;
import com.techfirm.stock.model.Role;
import com.techfirm.stock.model.User;
import com.techfirm.stock.model.enumeration.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldGetUsersIfFirstNameOrUserNameOrPasswordExists() {
        //given

        User user1 = new User("Owolabi","Ade","Toks","Ade@gmail.com","Tokss@19");
        User user2 = new User("Adigun","Ola","Toks Toks","Ola@gmail.com","ToksOla@19");
        User user3 = new User("Taiwo","Taye","TayeTaiwo","Taye@gmail.com","ToksToks@19");
        User user4 = new User();
        List<User> users = List.of(user1,user2,user3,user4);
       userRepository.saveAll(users);
        //when
        Page<User> exists = userRepository.findByFirstNameContainingOrUserNameContainingOrPasswordContaining(
                "Ade", "Toks Toks", "ToksToks@19", Pageable.unpaged());
        //then
        assertThat(exists).isNotEmpty();
        assertThat(exists).hasSize(3);
        assertThat(exists.getSize()).isEqualTo(3);
        assertThat(exists.getContent().contains(users)).isTrue();
        assertThat(exists.getContent().contains(new User())).isFalse();
        assertThat(exists.getContent()).containsOnly(user1, new User());

        List<User> content = exists.getContent();
        boolean hasToksToksUserName = content.stream()
                                  .anyMatch(user -> user.getUserName().equalsIgnoreCase("Toks Toks"));

        assertThat(hasToksToksUserName).isTrue();
    }

}