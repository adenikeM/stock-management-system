package com.techfirm.stock.controller;

import com.techfirm.stock.model.Sale;
import com.techfirm.stock.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("api")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Sale>> getAllSale() {
        return ResponseEntity.ok().body(saleService.getAllSale());
    }

    @GetMapping("/v2/sales")
    public ResponseEntity<List<Sale>> getAllSale2(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "0") Integer pageSize){

        Page<Sale> sales = saleService.getAllSale2(page, pageSize);
        return ResponseEntity.ok(sales.getContent());
    }


    @GetMapping("/sales/{id}")
    public ResponseEntity<?> getSaleByID(@PathVariable Long id) {
        log.info("Get sale id by " + id);
        if (id < 1) {
            throw new IllegalArgumentException("Sale ID cannot be less than 1, please input correct ID");
        }
        return saleService.getSaleById(id)
                          .map(sale -> ResponseEntity.ok().body(sale))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PostMapping("/sales")
//    public ResponseEntity<?> createSale(@RequestBody Sale sale) {
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
//    public ResponseEntity<?> updateSale(@RequestBody Sale sale) {
//        if (sale.getId() == null) {
//            return ResponseEntity.badRequest()
//                                 .body(buildErrorResponse("ID cannot be null, Id = "
//                                         + sale.getId(), HttpStatus.BAD_REQUEST));
//        }
//        Sale updatedSale = saleService.UpdateSale(new ProductSaleDTO());
//        if (updatedSale != null) {
//            return ResponseEntity.ok(updatedSale);
//        } else {
//            return ResponseEntity.badRequest().body(buildErrorResponse(
//                    "Sale with id " + sale.getId() + "doesn't exist, Input correct Sale ID ", BAD_REQUEST));
//        }
//    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Sale> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
