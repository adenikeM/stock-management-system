package com.techfirm.stock.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private String colour;
    private Date manufactureDate;
    private Date expiryDate;
    private String size;
    @ManyToOne
    private ProductCategory productCategory;




}
