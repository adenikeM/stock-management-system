package com.Techfirm.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class ProductCategory implements Serializable {
    @Id
    private Integer id;
    private String name;
    private Integer quantity;


}
