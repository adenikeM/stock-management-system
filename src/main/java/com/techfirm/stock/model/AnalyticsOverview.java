package com.techfirm.stock.model;

import com.techfirm.stock.model.dto.ProductCategoryCount;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnalyticsOverview  implements Serializable {

private Long productCount;

private Long salesCount;

private Long returnedSalesCount;

private Long productCategoryCount;
}


