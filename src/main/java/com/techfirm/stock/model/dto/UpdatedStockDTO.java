package com.techfirm.stock.model.dto;

import lombok.Data;

@Data
public class UpdatedStockDTO {
    private long id;
    private int incrementQuantity;
}
