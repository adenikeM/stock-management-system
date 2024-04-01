package com.techfirm.stock.utils;

import com.techfirm.stock.model.*;
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
    public static ResponseEntity<?> validateCreateSaleRequest(Sales sales) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + sales.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateUpdateSale(Sales sales) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + sales.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateCreateProductCategoryRequest(ProductCategory productCategory) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + productCategory.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateUpdateProductCategory(ProductCategory productCategory) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + productCategory.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateCreateLocationRequest(Location location) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + location.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateUpdateLocation(Location location) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + location.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateCreateRoleRequest(Role role) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + role.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateUpdateRole(Role role) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + role.getId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateCreateProductOrderRequest(ProductOrder order) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID should be null, Id = "
                                     + order.getProductOrderId(), HttpStatus.BAD_REQUEST));
    }
    public static ResponseEntity<?> validateUpdateProductOrder(ProductOrder order) {
        return ResponseEntity.badRequest()
                             .body(buildErrorResponse("ID cannot be null, Id = "
                                     + order.getProductOrderId(), HttpStatus.BAD_REQUEST));
    }
}