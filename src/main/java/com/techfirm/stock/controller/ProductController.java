package com.techfirm.stock.controller;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;
import static com.techfirm.stock.utils.Validation.validateCreateProductRequest;
import static com.techfirm.stock.utils.Validation.validateUpdateProduct;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("stockMgt")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity <List<Product>> getAllProduct(){
        return ResponseEntity.ok().body(productService.getAllProduct());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>getProductByID(@PathVariable Integer id){
        log.info("Get product id by " + id);
        if(id < 1){
            ResponseEntity.badRequest().body(
                    buildErrorResponse("Product id cannot be less than 1", BAD_REQUEST));
        }
        return productService.getProduct(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
    @GetMapping("/{name}")
    public ResponseEntity<?>getProductByName(@PathVariable String name){
        log.info("Request to get a product with name : " + name);
        if (name == null){
            return ResponseEntity.badRequest().build();
        }
        return productService.getProductByName(name)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(()-> ResponseEntity.notFound().build());
        }
    @PostMapping
    public ResponseEntity<?>createProduct(@RequestBody Product product){
        log.info("Request to create product => {}", product);
        if(product.getId() != null){
            log.info("product => {}", product);
            return validateCreateProductRequest(product);
    }
        return ResponseEntity.ok().body(productService.createProduct(product));
    }
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        if(product.getId()==null){
            return validateUpdateProduct(product);
        }
        Optional<Product> updatedProduct = productService.updateProduct(product);
        if(updatedProduct.isPresent()){
            return ResponseEntity.ok(updatedProduct);
        }else{
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Product with id " + product.getId() + "doesn't exist, Enter correct product id", BAD_REQUEST));
        }
        }
        @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
    }

