package com.techfirm.stock.service;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.repository.LocationRepository;
import com.techfirm.stock.repository.ProductCategoryRepository;
import com.techfirm.stock.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final LocationRepository locationRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository, LocationRepository locationRepository) {

        this.productCategoryRepository = productCategoryRepository;
        this.locationRepository = locationRepository;
    }
    public List<ProductCategory> getAllProductCategory(){
        return productCategoryRepository.findAll();
    }
    public Optional<ProductCategory> getProductCategory(Integer id){
        return productCategoryRepository.findById(id);
    }
    public Optional<ProductCategory> getProductCategoryByName(String name){
        return Optional.of((ProductCategory) productCategoryRepository.findAll());
    }
    public ProductCategory createProductCategory(ProductCategory productCategory){
        Location productLocation = savedLocationWithRepo(productCategory.getLocation());
        productCategory.setLocation(productLocation);

        return productCategoryRepository.save(productCategory);
    }
    private Location savedLocationWithRepo(Location location) {
        return locationRepository.save(location);
    }
    public Optional<ProductCategory> updateProductCategory(ProductCategory productCategory){
        productCategoryRepository.findById(productCategory.getId());
        if(productCategory.getId() == null){
            throw  new IllegalArgumentException("ProductCategory id must not be null");
        }
        Location productLocation = savedLocationWithRepo(productCategory.getLocation());
        productCategory.setLocation(productLocation);


        return Optional.of(productCategoryRepository.save(productCategory));
    }
    public void deleteProductCategory(Integer id){productCategoryRepository.findById(id);
    }
}


