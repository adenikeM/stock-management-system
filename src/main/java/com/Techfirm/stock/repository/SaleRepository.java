package com.Techfirm.stock.repository;

import com.Techfirm.stock.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
