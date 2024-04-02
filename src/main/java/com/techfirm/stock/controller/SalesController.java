package com.techfirm.stock.controller;

import com.techfirm.stock.model.Sales;
import com.techfirm.stock.service.SalesService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

//    @PostMapping("/sales")
//    public ResponseEntity<?> createSale(@RequestBody Sales sale) {
//        log.info("Request to create sale => {}", sale);
//        if (sale.getId() != null) {
//            log.info("user => {}", sale);
//            return ResponseEntity.badRequest()
//                                 .body(buildErrorResponse("ID should be null, Id = "
//                                         + sale.getId(), HttpStatus.BAD_REQUEST));
//        }
//        return ResponseEntity.ok().body(saleService.createSale(sale));
//    }

//    @PutMapping("/sales")
//    public ResponseEntity<?> updateSale(@RequestBody Sales sale) {
//        if (sale.getId() == null) {
//            return ResponseEntity.badRequest()
//                                 .body(buildErrorResponse("ID cannot be null, Id = "
//                                         + sale.getId(), HttpStatus.BAD_REQUEST));
//        }
//        Sales updatedSale = saleService.UpdateSale(new ProductSaleDTO());
//        if (updatedSale != null) {
//            return ResponseEntity.ok(updatedSale);
//        } else {
//            return ResponseEntity.badRequest().body(buildErrorResponse(
//                    "Sales with id " + sale.getId() + "doesn't exist, Input correct Sales ID ", BAD_REQUEST));
//        }
//    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Sales> deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
