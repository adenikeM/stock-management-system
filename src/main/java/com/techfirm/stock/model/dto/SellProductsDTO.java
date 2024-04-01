package com.techfirm.stock.model.dto;

import com.techfirm.stock.model.CustomerInfo;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SellProductsDTO {
    @NotNull
    private List<ProductsToBePriced> productsToBeSold;
    private CustomerInfo customerInfo;
}
