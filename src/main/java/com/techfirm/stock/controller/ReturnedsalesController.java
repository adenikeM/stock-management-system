package com.techfirm.stock.controller;

import com.techfirm.stock.model.ReturnedSales;
import com.techfirm.stock.model.dto.ReturnedSalesRequest;
import com.techfirm.stock.service.ReturnSalesService;
import com.techfirm.stock.service.ReturnedSalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("api")
public class ReturnedsalesController {
    private  final ReturnSalesService returnedSalesService;

    public ReturnedsalesController(ReturnSalesService returnedSalesService) {
        this.returnedSalesService = returnedSalesService;
    }
    @GetMapping("/returnedSales")
    public ResponseEntity<List<ReturnedSales>> getReturnedSales(){
        return ResponseEntity.ok().body(returnedSalesService.getReturnedSales());
    }
    @PostMapping("/returnSales")
    public ResponseEntity<ReturnedSales> createReturnedSales(@RequestBody ReturnedSalesRequest request
                                                             ) {
        log.info("Incoming request {}", request);
        ReturnedSales returnedSales = returnedSalesService.createReturnedSales(
                request.getSalesId(),
                request.getProductReturn(),
                request.getReasonForReturn(),
                request.getAdditionalComments()
                );
        return ResponseEntity.ok(returnedSales);
    }

    @GetMapping("/returnedSales/{id}")
    public ResponseEntity<ReturnedSales> getReturnedSalesById(@PathVariable Long id){
        ReturnedSales returnedSales = returnedSalesService.getReturnedSalesById(id);
        if (returnedSales == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(returnedSales);
    }
}
//