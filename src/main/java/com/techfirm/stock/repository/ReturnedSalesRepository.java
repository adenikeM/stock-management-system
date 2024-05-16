package com.techfirm.stock.repository;

import com.techfirm.stock.model.ReturnedSales;
import com.techfirm.stock.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnedSalesRepository extends JpaRepository<ReturnedSales, Long> {
    List<ReturnedSales> findBySales(Sales sales);
}
