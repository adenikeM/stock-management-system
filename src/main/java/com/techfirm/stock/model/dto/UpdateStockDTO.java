package com.techfirm.stock.model.dto;

import lombok.Data;

@Data
public class UpdateStockDTO {
    private long productId;
    private int quantityToBeAdded;
}
