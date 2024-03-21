package com.techfirm.stock.controller;

import com.techfirm.stock.model.ProductOrder;
import com.techfirm.stock.service.ProductOrderService;
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
@RequestMapping("/api/order/")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }
    @PostMapping
    public ResponseEntity<?> createProductOrderService(@RequestBody ProductOrder order){
        log.info("Create product order => {}", order);
        if(order.getProductOrderId() != null){
            log.info("product order => {}", order);
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID should be null, Id = "
                                         + order.getProductOrderId(), HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok().body(productOrderService.createProductOrder(order));
    }
    @GetMapping
    public ResponseEntity<List<ProductOrder>> getAllProductOrder(){
        return ResponseEntity.ok().body(productOrderService.getAllProductOrder());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductOrderById(@PathVariable Integer id){
        log.info("Get productOrder id by " + id);
        if(id < 1){
            ResponseEntity.badRequest().body(
                    buildErrorResponse("ProductOrder id cannot be less than 1", BAD_REQUEST));
        }
        return productOrderService.getAllProductOrderByID(id)
                                  .map(productOrder -> ResponseEntity.ok().body(productOrder))
                                  .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<?> updateProductOrder(@RequestBody ProductOrder order){
        if(order.getProductOrderId() == null){
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID cannot be null, Id = "
                                         + order.getProductOrderId(), HttpStatus.BAD_REQUEST));
        }
        Optional<ProductOrder> updatedProductOrder = productOrderService.updateProductOrder(order);
        if(updatedProductOrder.isPresent()){
            return ResponseEntity.ok(updatedProductOrder);
        }
        else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "ProductOrder with id " + order.getProductOrderId() + "doesn't exist, Enter correct productOrder id", BAD_REQUEST));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductOrder> deleteProductOrder(@PathVariable Integer id){
        productOrderService.deleteProductOrder(id);
        return ResponseEntity.noContent().build();
    }
}

