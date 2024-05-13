package com.techfirm.stock.service;

import com.techfirm.stock.model.Sales;
import com.techfirm.stock.repository.SalesRepository;

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
        return salesRepository.findById(Math.toIntExact(id));
    }

    public void deleteSale(Long id) {
        salesRepository.findById(Math.toIntExact(id));
    }

    public Sales createSale(Sales sales) {
        log.info("request to create new sales {} ", sales);
        if (!Objects.isNull(sales.getId())) {
            throw new IllegalArgumentException("New sales cannot have id");
        }

        return salesRepository.save(sales);
    }
}








