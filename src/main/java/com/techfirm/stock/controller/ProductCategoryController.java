package com.techfirm.stock.controller;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.service.ProductCategoryService;
import com.techfirm.stock.service.ProductService;
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
@RequestMapping("api/category")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllProductCategory() {
        return ResponseEntity.ok().body(productCategoryService.getAllProductCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductCategoryByID(@PathVariable Integer id) {
        log.info("Get product category id by " + id);
        if (id < 1) {
            ResponseEntity.badRequest().body(
                    buildErrorResponse("Product category id cannot be less than 1", BAD_REQUEST));
        }
        return productCategoryService.getProductCategory(id)
                                     .map(productCategory -> ResponseEntity.ok().body(productCategory))
                                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        log.info("Request to get a product category with name : " + name);
        if (name == null) {
            return ResponseEntity.badRequest().build();
        }
        return productCategoryService.getProductCategoryByName(name)
                                     .map(productCategory -> ResponseEntity.ok().body(productCategory))
                                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProductCategory(@RequestBody ProductCategory productCategory) {
        log.info("Request to create product category => {}", productCategory);
        if (productCategory.getId() != null) {
            log.info("product category => {}", productCategory);
            return validateCreateProductCategoryRequest(productCategory);
        }
        return ResponseEntity.ok().body(productCategoryService.createProductCategory(productCategory));
    }

    @PutMapping
    public ResponseEntity<?> updateProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategory.getId() == null) {
            return validateUpdateProductCategory(productCategory);
        }
        Optional<ProductCategory> updatedProductCategory = productCategoryService.updateProductCategory(productCategory);
        if (updatedProductCategory.isPresent()) {
            return ResponseEntity.ok(updatedProductCategory);
        } else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Product category with id " + productCategory.getId() + "doesn't exist, Enter correct product category id", BAD_REQUEST));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductCategory> deleteProductCategory(@PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }
}
