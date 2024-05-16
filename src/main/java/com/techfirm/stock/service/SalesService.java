package com.techfirm.stock.service;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ReturnedSales;
import com.techfirm.stock.model.Sales;
import com.techfirm.stock.repository.ReturnedSalesRepository;
import com.techfirm.stock.repository.SalesRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final ReturnedSalesRepository returnedSalesRepository;

    public List<Sales> getAllSale() {
        return salesRepository.findAll();
    }

    public Page<Sales> getAllSale2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return salesRepository.findAll(pageable);
    }
    public Page<Sales> getAllSales3(Pageable pageable){
        return salesRepository.findAll(pageable);
    }
    public Optional<Sales> getSaleById(Long id) {
        return salesRepository.findById(id);
    }

    public void deleteSale(Long id) {
        salesRepository.findById(id);
    }

    public Sales createSale(Sales sales) {
        log.info("request to create new sales {} ", sales);
        if (!Objects.isNull(sales.getId())) {
            throw new IllegalArgumentException("New sales cannot have id");
        }
        return salesRepository.save(sales);
    }

}








