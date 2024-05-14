package com.techfirm.stock.controller;

import com.techfirm.stock.model.ReturnedSales;
import com.techfirm.stock.service.ReturnedSalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("api")
public class ReturnedsalesController {
    private  final ReturnedSalesService returnedSalesService;

    public ReturnedsalesController(ReturnedSalesService returnedSalesService) {
        this.returnedSalesService = returnedSalesService;
    }
    @GetMapping("/returnedSales")
    public ResponseEntity<List<ReturnedSales>> getReturnedSales(){
        return ResponseEntity.ok().body(returnedSalesService.getReturnedSales());
    }
    @PostMapping("/returnedSales")
    public ResponseEntity<ReturnedSales> createReturnedSales(@RequestBody ReturnedSales returnedSales){
        ReturnedSales createdReturnedSales = returnedSalesService.createReturnedSales(returnedSales);
        return ResponseEntity.ok(createdReturnedSales);
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
