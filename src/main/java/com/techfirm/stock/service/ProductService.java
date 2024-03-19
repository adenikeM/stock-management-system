package com.techfirm.stock.service;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.repository.ProductCategoryRepository;
import com.techfirm.stock.repository.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@NoArgsConstructor(force = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Integer id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String name) {
        return Optional.of((Product) productRepository.findAll());
    }

    public Product createProduct(Product product) {
        ProductCategory category = productCategoryService.createProductCategory(product.getProductCategory());
        product.setProductCategory(category);

        return productRepository.save(product);
    }

   // private ProductCategory savedProductWithRepo(ProductCategory productCategory) {
    //    return productCategoryRepository.save(productCategory);
    //}

    public Optional<Product> updateProduct(Product product) {
        productRepository.findById(product.getId());
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }
        ProductCategory category = productCategoryService.createProductCategory(product.getProductCategory());
        product.setProductCategory(category);

        return Optional.of(productRepository.save(product));
    }

    public void deleteProduct(Integer id) {
        productRepository.findById(id);
    }
}
