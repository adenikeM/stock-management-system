package com.techfirm.stock.repository;

import com.techfirm.stock.model.ReturnedSales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnedSalesRepository extends JpaRepository<ReturnedSales, Long> {
}
