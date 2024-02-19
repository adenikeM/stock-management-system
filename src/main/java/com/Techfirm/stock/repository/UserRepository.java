package com.Techfirm.stock.repository;

import com.Techfirm.stock.model.Sale;
import com.Techfirm.stock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
