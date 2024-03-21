package com.techfirm.stock.controller;

import com.techfirm.stock.model.ProductSaleRequest;
import com.techfirm.stock.model.Sale;
import com.techfirm.stock.model.User;
import com.techfirm.stock.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("api")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Sale>> getAllUser() {
        return ResponseEntity.ok().body(saleService.getAllSale());
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<?> getSaleByID(@PathVariable Integer id) {
        log.info("Get sale id by " + id);
        if (id < 1) {
            throw new IllegalArgumentException("Sale ID cannot be less than 1, please input correct ID");
        }
        return saleService.getSale(id)
                          .map(sale -> ResponseEntity.ok().body(sale))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/sales")
    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
        log.info("Request to create sale => {}", sale);
        if (sale.getId() != null) {
            log.info("user => {}", sale);
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID should be null, Id = "
                                         + sale.getId(), HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok().body(saleService.createSale(sale));
    }

    @PutMapping("/sales")
    public ResponseEntity<?> updateSale(@RequestBody Sale sale) {
        if (sale.getId() == null) {
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID cannot be null, Id = "
                                         + sale.getId(), HttpStatus.BAD_REQUEST));
        }
        Optional<Sale> updatedSale = saleService.UpdateSale(sale);
        if (updatedSale.isPresent()) {
            return ResponseEntity.ok(updatedSale);
        } else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Sale with id " + sale.getId() + "doesn't exist, Input correct Sale ID ", BAD_REQUEST));
        }
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Sale> deleteSale(@PathVariable Integer id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
