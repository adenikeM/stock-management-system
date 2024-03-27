package com.techfirm.stock.controller;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.model.dto.ProductCategoryDTO;
import com.techfirm.stock.model.dto.ProductDTO;
import com.techfirm.stock.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductCategory>> getAllProductCategory() {
        return ResponseEntity.ok().body(productCategoryService.getAllProductCategory());
    }

    @GetMapping("/v2/category")
    public ResponseEntity<List<ProductCategory>> getAllProductCategory2(
            @RequestParam(name = "page", defaultValue = "0" ) Integer pageNo){
        int pageSize = 1;
        Page<ProductCategory> category = productCategoryService.getAllProductCategory2(pageNo, pageSize);
        return ResponseEntity.ok(category.getContent());

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductCategoryByID(@PathVariable Integer id) {
        log.info("Get product category id by " + id);
        if (id < 1) {
            ResponseEntity.badRequest().body(
                    buildErrorResponse("Product category id cannot be less than 1", BAD_REQUEST));
        }
        return productCategoryService.getProductCategoryById(Long.valueOf(id))
                                     .map(productCategory -> ResponseEntity.ok().body(productCategory))
                                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping("/{name}")
//    public ResponseEntity<?> getProductByName(@PathVariable String name) {
//        log.info("Request to get a product category with name : " + name);
//        if (name == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return productCategoryService.getProductCategoryByName(name)
//                                     .map(productCategory -> ResponseEntity.ok().body(productCategory))
//                                     .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PostMapping("/category")
    public ResponseEntity<?> createProductCategory(@RequestBody ProductCategory productCategory) {
        log.info("Request to create product category => {}", productCategory);
        if (productCategory.getId() != null) {
            log.info("product category => {}", productCategory);
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID should be null, Id = "
                                         + productCategory.getId(), HttpStatus.BAD_REQUEST));
        }
        return ResponseEntity.ok().body(productCategoryService.createProductCategory(productCategory));
    }
    @PostMapping("/v2/category")
    public ResponseEntity<?> createProductCategory(@Valid @RequestBody ProductCategoryDTO createProductCategoryDTO) {
        log.info("Request to create product category v2 => {}", createProductCategoryDTO);
        return ResponseEntity.ok().body(productCategoryService.createProductCategoryV2(createProductCategoryDTO));
    }

    @PutMapping("/category")
    public ResponseEntity<?> updateProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategory.getId() == null) {
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID cannot be null, Id = "
                                         + productCategory.getId(), HttpStatus.BAD_REQUEST));
        }
        Optional<ProductCategory> updatedProductCategory = productCategoryService.updateProductCategory(productCategory);
        if (updatedProductCategory.isPresent()) {
            return ResponseEntity.ok(updatedProductCategory);
        } else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Product category with id " + productCategory.getId() + "doesn't exist, Enter correct product category id", BAD_REQUEST));
        }
    }
    @PutMapping("/v2/category/{id}")
    public ResponseEntity<?> updateProductCategoryV2(@Valid @RequestBody ProductCategoryDTO updateProductCategoryDTO, @PathVariable Long id){
        log.info("Incoming request to update product category v2 with id {} and payload {}", id, updateProductCategoryDTO);
        ProductCategory updatedProductCategoryV2 = productCategoryService.updateProductCategoryV2(id, updateProductCategoryDTO);
        return ResponseEntity.ok(updatedProductCategoryV2);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ProductCategory> deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }
}
