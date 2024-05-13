package com.techfirm.stock.controller;

import com.techfirm.stock.model.Sales;
import com.techfirm.stock.service.SalesService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("api")
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Sales>> getAllSale() {
        return ResponseEntity.ok().body(salesService.getAllSale());
    }

    @GetMapping("/v2/sales")
    public ResponseEntity<List<Sales>> getAllSale2(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "0") int pageSize){

        Page<Sales> sales = salesService.getAllSale2(pageNo, pageSize);
        return ResponseEntity.ok(sales.getContent());
    }
    @GetMapping("/v3/sales")
    public ResponseEntity<List<Sales>> getAllSales3(Pageable pageable){
        Page<Sales> sales = salesService.getAllSales3(pageable);
        return ResponseEntity.ok(sales.getContent());
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<?> getSaleByID(@PathVariable Long id) {
        log.info("Get sale id by " + id);
        if (id < 1) {
            throw new IllegalArgumentException("Sales ID cannot be less than 1, please input correct ID");
        }
        return salesService.getSaleById(id)
                          .map(sale -> ResponseEntity.ok().body(sale))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Sales> deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
