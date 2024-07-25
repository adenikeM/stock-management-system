package com.techfirm.stock.service;

import com.techfirm.stock.model.AnalyticsOverview;
import com.techfirm.stock.model.ProductCategory;
import com.techfirm.stock.model.dto.ProductCategoryCount;
import com.techfirm.stock.repository.ProductCategoryRepository;
import com.techfirm.stock.repository.ProductRepository;
import com.techfirm.stock.repository.ReturnedSalesRepository;
import com.techfirm.stock.repository.SalesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AnalyticsOverviewService {
    private  final ProductRepository productRepository;
    private final SalesRepository salesRepository;
    private final ReturnedSalesRepository returnedSalesRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public AnalyticsOverviewService(ProductRepository productRepository, SalesRepository salesRepository, ReturnedSalesRepository returnedSalesRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.salesRepository = salesRepository;
        this.returnedSalesRepository = returnedSalesRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public AnalyticsOverview getAnalyticsOverview(){
        AnalyticsOverview overview = new AnalyticsOverview();

        Long productCount = productRepository.count();
        overview.setProductCount(productCount);

        Long salesCount = salesRepository.count();
        overview.setSalesCount(salesCount);

        Long returnedSalesCount = returnedSalesRepository.count();
        overview.setReturnedSalesCount(returnedSalesCount);

        Long productCategoryCount = productCategoryRepository.count();
        overview.setProductCategoryCount(productCategoryCount);

//        List<ProductCategory> categories = productCategoryRepository.findAll();
//        List<ProductCategoryCount> categoryCounts = new ArrayList<>();
//        for(ProductCategory category : categories) {
//            Long count = productRepository.countByProductCategory(category);
//            ProductCategoryC;ount categoryCount = new ProductCategoryCount(category.getCategoryName(), count);
//            categoryCounts.add(categoryCount);
//        }
//        overview.setCategoryCounts(categoryCounts);

        return overview;
    }

    }




