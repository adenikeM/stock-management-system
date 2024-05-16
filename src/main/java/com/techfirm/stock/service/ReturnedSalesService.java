package com.techfirm.stock.service;

import com.techfirm.stock.model.*;
import com.techfirm.stock.repository.ReturnedSalesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReturnedSalesService {
    private final ReturnedSalesRepository returnedSalesRepository;
    private final SalesService salesService;
    private EmailService emailService;

    public ReturnedSalesService(ReturnedSalesRepository returnedSalesRepository, SalesService salesService, EmailService emailService) {
        this.returnedSalesRepository = returnedSalesRepository;
        this.salesService = salesService;
        this.emailService = emailService;
    }

    @Transactional
    public ReturnedSales createReturnedSales(ReturnedSales returnedSales){
        // perform validation
//        if (returnedSales == null) {
//            throw new IllegalArgumentException("Returned sales object cannot be null.");
//        }
//        Sales sales = returnedSales.getSales();
//        if (sales == null) {
//            throw new IllegalArgumentException("Sales object cannot be null.");
//        }
//        Product product = returnedSales.getProduct();
//        if (product == null) {
//            throw new IllegalArgumentException("Product object cannot be null.");
//        }
        if (returnedSales == null || returnedSales.getSales() == null || returnedSales.getProduct() == null) {
            throw new IllegalArgumentException("Returned sales object, sales, and product cannot be null.");
        }

        // Check if the returned product belongs to the sales
//        Sales sales = returnedSales.getSales();
//        if (sales.getProducts() == null) {
//            sales.setProducts(new ArrayList<>());
//        }
//        Product soldProduct = sales.getProducts().stream()
//                                   .filter(product -> product.getId().equals(returnedSales.getProduct().getId()))
//                                   .findFirst()
//                                   .orElse(null);
//
//        // Ensure that the product being returned corresponds to a product sold in the sales
//        if (soldProduct == null) {
//            throw new IllegalArgumentException("The product to be returned was not sold in the associated sales.");
//        }
        Sales sales = returnedSales.getSales();
        List<Product> productsSold = sales.getProducts();

        boolean productIdMatch = productsSold.stream()
                                             .anyMatch(product -> product.getId().equals(returnedSales.getProduct().getId()));

        if (!productIdMatch) {
            throw new IllegalArgumentException("Returned product ID does not correspond to any product in the original sale.");
        }



        int quantityReturned = returnedSales.getQuantityReturned();
        if (quantityReturned <= 0) {
            throw new IllegalArgumentException("Quantity returned must be greater than zero.");
        }

        int quantitySold = sales.getTotalQuantitySold();
        if (quantityReturned > quantitySold) {
            throw new IllegalArgumentException("Quantity returned cannot be greater than quantity sold.");
        }
        // Calculate refund amount
       // BigDecimal refundAmount = product.getPrice().multiply(BigDecimal.valueOf(quantityReturned));

        // Update product's available quantity
        Product product = returnedSales.getProduct();
        int updatedQuantity = product.getAvailableQuantity() + quantityReturned;
        product.setAvailableQuantity(updatedQuantity);

        // Save returned sales entity
        ReturnedSales savedReturnedSales = returnedSalesRepository.save(returnedSales);

        // Send email to the customer
        //sendRefundEmail(returnedSales );

        return savedReturnedSales;
    }

    public List <ReturnedSales> getReturnedSales(){
        return returnedSalesRepository.findAll();}

    public ReturnedSales getReturnedSalesById(Long id){
        return returnedSalesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Return sales id not found"));
    }

//    private void sendRefundEmail(ReturnedSales returnedSales) {
//        String customerEmail = returnedSales.getSales().getCustomerInfo().getEmail();
//        Product returnedProduct = returnedSales.getProduct();
//        String productName = returnedProduct.getName();
//        BigDecimal refundAmount = calculateRefundAmount(returnedSales);
//        emailService.sendRefundEmail(customerEmail, productName, refundAmount);
//    }
    private BigDecimal calculateRefundAmount(ReturnedSales returnedSales) {
        BigDecimal refundAmount;
        BigDecimal price = returnedSales.getProduct().getPrice();
        int quantityReturned = returnedSales.getQuantityReturned();
        refundAmount = price.multiply(BigDecimal.valueOf(quantityReturned));
        return refundAmount;

    }
    //.status(HttpStatus.CREATED).body("Returned sales with ID " + returnedSales.getId() + " created successfully");

}

