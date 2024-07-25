package com.techfirm.stock.model.dto;

import lombok.Data;

@Data
public class ProductCategoryCount {
    private String categoryName;

    private Long count;


    public ProductCategoryCount(String categoryName, Long count) {

        this.categoryName = categoryName;

        this.count = count;

    }

}
