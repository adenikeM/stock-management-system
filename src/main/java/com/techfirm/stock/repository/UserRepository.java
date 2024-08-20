package com.techfirm.stock.repository;

import com.techfirm.stock.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("select u from User u where u.firstName LIKE %:firstName%")
//    Page<User> findByFirstNameContaining(String firstName, Pageable pageable);
//    @Query("select u from User u where  u.userName LIKE %:userName%")
//    Page<User> findByUserNameContaining(String userName, Pageable pageable);
//    @Query("select u from User u where  u.password LIKE %:password%")
//    Page<User> findByPasswordContaining(String password, Pageable pageable);
Optional<User> findByEmail(String email);
@Query("select u from User u where UPPER(u.firstName) LIKE CONCAT('%',UPPER(?1),'%') and UPPER( u.userName) LIKE CONCAT( '%',UPPER(?2),'%') and UPPER(u.password) LIKE  CONCAT('%',UPPER(?3),'%')")
Page<User> findByFirstNameContainingOrUserNameContainingOrPasswordContaining(String firstName, String userName, String password, Pageable pageable);
}

