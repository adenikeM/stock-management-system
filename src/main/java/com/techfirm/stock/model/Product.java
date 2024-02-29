package com.techfirm.stock.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private String size;
    @ManyToOne
    private ProductCategory productCategory;




}
