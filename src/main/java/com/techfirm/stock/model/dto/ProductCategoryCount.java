package com.techfirm.stock.model.dto;

public class ProductCategoryCount {
    private String categoryName;

    private Long count;


    public ProductCategoryCount(String categoryName, Long count) {

        this.categoryName = categoryName;

        this.count = count;

    }
}
