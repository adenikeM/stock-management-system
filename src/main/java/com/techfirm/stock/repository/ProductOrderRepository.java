package com.techfirm.stock.repository;

import com.techfirm.stock.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
}
