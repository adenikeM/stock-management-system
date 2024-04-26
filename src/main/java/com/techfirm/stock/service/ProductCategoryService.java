package com.techfirm.stock.service;

import com.techfirm.stock.model.Location;
import com.techfirm.stock.model.Product;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.model.dto.ProductCategoryDTO;
import com.techfirm.stock.repository.LocationRepository;
import com.techfirm.stock.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.techfirm.stock.utils.ObjectMapper.*;

@Service
@Slf4j
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final LocationService locationService;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository, LocationRepository locationRepository, LocationService locationService) {

        this.productCategoryRepository = productCategoryRepository;
        this.locationService = locationService;
    }

    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    public Page<ProductCategory> getAllProductCategory2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productCategoryRepository.findAll(pageable);
    }
    public Page<ProductCategory> getAllProductCategory3(Pageable pageable){
        return productCategoryRepository.findAll(pageable);
    }

    public Optional<ProductCategory> getProductCategoryById(Long id) {
        return productCategoryRepository.findById(id);
    }

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        Location productLocation = locationService.createLocation (productCategory.getLocation());
        productCategory.setLocation(productLocation);

        return productCategoryRepository.save(productCategory);
    }


    public Optional<ProductCategory> updateProductCategory(ProductCategory productCategory) {
        Long productCategoryId = productCategory.getId();
        if (productCategoryId == null) {
            throw new IllegalArgumentException("ProductCategory id must not be null");
        }

        productCategoryRepository.findById(productCategoryId)
                                 .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product with id " + productCategoryId));

        Location productLocation = locationService.createLocation(productCategory.getLocation());
        productCategory.setLocation(productLocation);


        return Optional.of(productCategoryRepository.save(productCategory));
    }

    public void deleteProductCategory(Long id) {productCategoryRepository.findById(id);}

    public ProductCategory createProductCategoryV2(ProductCategoryDTO createProductCategoryDTO){
        Location location = locationService.getLocationById(createProductCategoryDTO.getLocationId())
                                                  .orElseThrow(() -> new IllegalArgumentException("Invalid location id" + createProductCategoryDTO.getLocationId()));
    ProductCategory productCategory = mapCreateProductCategoryDTOToProductCategory(createProductCategoryDTO, location);
    return productCategoryRepository.save(productCategory);
    }

    public ProductCategory updateProductCategoryV2(Long id, ProductCategoryDTO updateProductCategoryDTO){
        ProductCategory retrievedProductCategory = productCategoryRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException("Could not retrieve Product category with id " + id));
        log.info("Retrieved product category {}", retrievedProductCategory);

        Long productLocationId = updateProductCategoryDTO.getLocationId();
        Location location = locationService.getLocationById(productLocationId)
                                                                .orElseThrow(() -> new IllegalArgumentException("Invalid product category id " + productLocationId));

        mapUpdateProductCategoryDTOToProductCategory(updateProductCategoryDTO, location, retrievedProductCategory);
        log.info("Retrieved product  category after mapping {}", retrievedProductCategory);
        return productCategoryRepository.save(retrievedProductCategory);
    }
}



