package com.techfirm.stock.service;

import com.techfirm.stock.model.*;
import com.techfirm.stock.repository.CustomerInfoRepository;
import com.techfirm.stock.repository.ReturnedSalesRepository;
import com.techfirm.stock.repository.SalesRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ReturnedSalesService {
    private final ReturnedSalesRepository returnedSalesRepository;
    private final SalesRepository salesRepository;
    private EmailService emailService;

    public ReturnedSalesService(ReturnedSalesRepository returnedSalesRepository, SalesRepository salesRepository, EmailService emailService) {
        this.returnedSalesRepository = returnedSalesRepository;
        this.salesRepository = salesRepository;
        this.emailService = emailService;
    }

    public ReturnedSales createReturnedSales(ReturnedSales returnedSales) {
        validateReturnedProductId(returnedSales);
        validateReturnedQuantity(returnedSales);

        Sales originalSales = returnedSales.getSales();
        originalSales.setTotalQuantitySold(originalSales.getTotalQuantitySold() - returnedSales.getQuantityReturned());
        salesRepository.save(originalSales);

        sendRefundEmailToCustomer(returnedSales);

        return returnedSalesRepository.save(returnedSales);

    }

    public List <ReturnedSales> getReturnedSales(){return returnedSalesRepository.findAll();}

    public ReturnedSales getReturnedSalesById(Long id){
        return returnedSalesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Return sales id not found"));
    }


    private void sendRefundEmailToCustomer(ReturnedSales returnedSales) {
            String customerEmail = returnedSales.getSales().getCustomerInfo().getEmail();
        Product returnedProduct = returnedSales.getProduct();
         String productName = returnedProduct.getName();
            BigDecimal refundAmount = calculateRefundAmount(returnedSales);
            emailService.sendRefundEmail(customerEmail, productName, refundAmount);
        }

    private BigDecimal calculateRefundAmount(ReturnedSales returnedSales) {
        BigDecimal refundAmount;
        BigDecimal price = returnedSales.getPrice();
        int quantityReturned = returnedSales.getQuantityReturned();
        refundAmount = price.multiply(BigDecimal.valueOf(quantityReturned));
        return refundAmount;

    }

    private void validateReturnedQuantity(ReturnedSales returnedSales) {
            if (returnedSales.getQuantityReturned() <= 0) {
                throw new IllegalArgumentException("Returned quantity must be greater than zero.");
            }

            if (returnedSales.getQuantityReturned() > returnedSales.getSales().getTotalQuantitySold()) {
                throw new IllegalArgumentException("Returned quantity cannot exceed the total quantity sold.");
            }
        }

    private void validateReturnedProductId(ReturnedSales returnedSales) {
            Sales originalSales = returnedSales.getSales();
            List<Product> productsSold = originalSales.getProducts();

            boolean productIdMatch = productsSold.stream()
                                                 .anyMatch(product -> product.getId().equals(returnedSales.getProduct().getId()));

            if (!productIdMatch) {
                throw new IllegalArgumentException("Returned product ID does not correspond to any product in the original sale.");
            }
        }
}

