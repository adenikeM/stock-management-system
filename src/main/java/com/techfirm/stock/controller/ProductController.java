package com.techfirm.stock.controller;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.dto.ProductDTO;
import com.techfirm.stock.service.ProductService;
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
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok().body(productService.getAllProduct());
    }

    @GetMapping("/v2/products")
    public ResponseEntity<List<Product>> getAllProduct2(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "0") Integer pageSize) {
        Page<Product> products = productService.getAllProduct2(page, pageSize);
        return ResponseEntity.ok(products.getContent()) ;
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductByID(@PathVariable Long id) {
        log.info("Get product id by " + id);
        if (id < 1) {
            ResponseEntity.badRequest().body(
                    buildErrorResponse("Product id cannot be less than 1", BAD_REQUEST));
        }
        return productService.getProduct(id)
                             .map(product -> ResponseEntity.ok().body(product))
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping("/{name}")
//    public ResponseEntity<?> getProductByName(@PathVariable String name) {
//        log.info("Request to get a product with name : " + name);
//        if (name == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return productService.getProductByName(name)
//                             .map(product -> ResponseEntity.ok().body(product))
//                             .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        log.info("Request to create product => {}", product);
        if (product.getId() != null) {
            log.info("product => {}", product);
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID should be null, Id = "
                                         + product.getId(), HttpStatus.BAD_REQUEST));

        }
        return ResponseEntity.ok().body(productService.createProduct(product));
    }

    @PostMapping("/v2/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO createProductDTO) {
        log.info("Request to create product v2 => {}", createProductDTO);
        return ResponseEntity.ok().body(productService.createProductV2(createProductDTO));
    }

    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            return ResponseEntity.badRequest()
                                 .body(buildErrorResponse("ID cannot be null, Id = "
                                         + product.getId(), HttpStatus.BAD_REQUEST));
        }
        Optional<Product> updatedProduct = productService.updateProduct(product);
        if (updatedProduct.isPresent()) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.badRequest().body(buildErrorResponse(
                    "Product with id " + product.getId() + "doesn't exist, Enter correct product id", BAD_REQUEST));
        }
    }
    @PutMapping("/v2/products/{id}")
    public ResponseEntity<?> updateProductV2(@Valid @RequestBody ProductDTO updateProductDTO, @PathVariable Long id){
        log.info("Incoming request to update product v2 with id {} and payload {}", id, updateProductDTO);
        Product updatedProductV2 = productService.updateProductV2(id, updateProductDTO);
        return ResponseEntity.ok(updatedProductV2);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

