package com.techfirm.stock.utils;

import com.techfirm.stock.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.techfirm.stock.exception.ErrorResponse.buildErrorResponse;

public class Validation {

    public static ResponseEntity<?> validateCreateProductRequest(Product product) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + product.getId(), HttpStatus.BAD_REQUEST));

    }
    public static ResponseEntity<?> validateUpdateProduct(Product product) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + product.getId(), HttpStatus.BAD_REQUEST));
    }
}