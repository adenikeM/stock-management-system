package com.techfirm.stock.service;

import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.model.dto.ProductDTO;
import com.techfirm.stock.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.utils.ObjectMapper.mapCreateProductDTOToProduct;
import static com.techfirm.stock.utils.ObjectMapper.mapUpdateProductDTOToProduct;

@Service
@Slf4j
//@NoArgsConstructor(force = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;

    public ProductService(ProductRepository productRepository, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
    }

//    public ProductService(ProductRepository productRepository, ProductCategoryService productCategoryService) {
//        this.productRepository = productRepository;
//        this.productCategoryService = productCategoryService;
//    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProduct2(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAll(pageable);
    }
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

//    public Optional<Product> getProductByName(String name) {
//        return Optional.of((Product) productRepository.findAll());
//    }

    public Product createProduct(Product product) {
        ProductCategory category = productCategoryService.createProductCategory(product.getProductCategory());
        product.setProductCategory(category);

        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Product product) {
        Long productId = product.getId();
        if (productId == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }
//        Optional<Product> retrievedProductOptional = productRepository.findById(productId);
//
//        Product retrievedProduct = retrievedProductOptional
//                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + productId));

        Product retrievedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + productId));


        ProductCategory category = productCategoryService.createProductCategory(retrievedProduct.getProductCategory());
        product.setProductCategory(category);

        return Optional.of(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id);
    }

    public Product createProductV2(ProductDTO createProductDTO) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById (createProductDTO.getProductCategoryId())
                                                                .orElseThrow(() -> new IllegalArgumentException("Invalid product category id " + createProductDTO.getProductCategoryId()));
        Product product = mapCreateProductDTOToProduct(createProductDTO, productCategory);
        return productRepository.save(product);
    }

    public Product updateProductV2(Long id, ProductDTO updateProductDTO) {
        Product retrievedProduct = productRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + id));
        log.info("Retrieved product {}", retrievedProduct);

        Long productCategoryId = updateProductDTO.getProductCategoryId();
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product category id " + productCategoryId));

        mapUpdateProductDTOToProduct(updateProductDTO, productCategory, retrievedProduct);
        log.info("Retrieved product after mapping {}", retrievedProduct);
        return productRepository.save(retrievedProduct);
    }
    }

