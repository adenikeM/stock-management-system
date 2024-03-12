package com.techfirm.stock.controller;

import com.techfirm.stock.model.ProductSaleRequest;
import com.techfirm.stock.model.Sale;
import com.techfirm.stock.model.User;
import com.techfirm.stock.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static com.techfirm.stock.utils.Validation.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }
    @GetMapping
    public ResponseEntity<List<Sale>> getAllUser(){
        return ResponseEntity.ok().body(saleService.getAllSale());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleByID(@PathVariable Integer id){
        log.info("Get sale id by " + id);
        if(id < 1){
            throw new IllegalArgumentException("Sale ID cannot be less than 1, please input correct ID");
        }
        return saleService.getSale(id)
                          .map(sale -> ResponseEntity.ok().body(sale))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Sale sale){
        log.info("Request to create sale => {}", sale);
        if(sale.getId() != null){
            log.info("user => {}", sale);
            return validateCreateSaleRequest(sale);
        }
        return ResponseEntity.ok().body(saleService.createSale(sale));
    }
    @PutMapping
    public ResponseEntity<?> updateSale(@RequestBody Sale sale){
        if(sale.getId() == null){
            return validateUpdateSale(sale);
        }
        Optional<Sale> updatedSale = saleService.UpdateSale(sale);
        if(updatedSale.isPresent()){
            return ResponseEntity.ok(updatedSale);
        }
        else{
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Sale with id " + sale.getId() + "doesn't exist, Input correct Sale ID ", BAD_REQUEST));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Sale> deleteSale(@PathVariable Integer id){
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
