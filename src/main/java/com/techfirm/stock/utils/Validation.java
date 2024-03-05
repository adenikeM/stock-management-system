package com.techfirm.stock.utils;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.Sale;
import com.techfirm.stock.model.User;
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
    public static ResponseEntity<?> validateCreateUserRequest(User user) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + user.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateUpdateUser(User user) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + user.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateCreateSaleRequest(Sale sale) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + sale.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateUpdateSale(Sale sale) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + sale.getId(), HttpStatus.BAD_REQUEST));
    }
}