package com.techfirm.stock.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPrice {
    private long productId;
    private int quantity;
    private BigDecimal price;
}
