package com.techfirm.stock.repository;

import com.techfirm.stock.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    String KEYWORD =  "select b from User b where UPPER(b.firstName) LIKE CONCAT('%',UPPER(?1),'%') and UPPER( b.userName) LIKE CONCAT( '%',UPPER(?2),'%') and UPPER( b.password) LIKE  CONCAT('%',UPPER(?3),'%')";
    @Query(KEYWORD)
    Page<User> findByFirstNameAndUserNameAndPassword(String firstName, String userName, String password, Pageable pageable);
}
