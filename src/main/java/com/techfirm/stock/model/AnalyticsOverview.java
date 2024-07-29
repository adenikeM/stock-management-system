package com.techfirm.stock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsOverview implements Serializable {

    private Long productCount;
    private Long salesCount;
    private Long returnedSalesCount;
    private Long productCategoryCount;
}


