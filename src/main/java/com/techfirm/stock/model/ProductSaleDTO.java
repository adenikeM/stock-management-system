package com.techfirm.stock.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductSaleDTO {
    @NotNull
    private Long productId;

    @NotNull
    @DecimalMax(value = "99999")
    private Integer quantity;
}
