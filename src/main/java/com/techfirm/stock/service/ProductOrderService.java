package com.techfirm.stock.service;

import com.techfirm.stock.model.ProductOrder;
import com.techfirm.stock.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    public List<ProductOrder> getAllProductOrder() {
        return productOrderRepository.findAll();
    }

    public Optional<ProductOrder> getAllProductOrderByID(Integer productOrderId) {
        return productOrderRepository.findById(productOrderId);
    }

    public ProductOrder createProductOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    public Optional<ProductOrder> updateProductOrder(ProductOrder productOrder) {
        productOrderRepository.findById(productOrder.getProductOrderId());
        if (productOrder.getProductOrderId() == null) {
            throw new IllegalArgumentException("product id must not be null");
        }
        return Optional.of(productOrderRepository.save(productOrder));
    }

    public void deleteProductOrder(Integer productOrderID) {
        productOrderRepository.findById(productOrderID);
    }
}
