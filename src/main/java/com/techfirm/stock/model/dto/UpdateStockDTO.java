package com.techfirm.stock.model.dto;

import lombok.Data;

@Data
public class UpdateStockDTO {
    private long id;
    private int quantityToBeAdded;
}
