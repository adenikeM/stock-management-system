package com.techfirm.stock.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPriceDTO {
    List<ProductPrice> productPriceList;
    BigDecimal totalPrice;
}
