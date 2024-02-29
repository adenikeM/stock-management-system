package com.techfirm.stock.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Sale implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer quantitySold;
    private BigDecimal price;
    private LocalDate saleDate;
    @ManyToMany
    private List <Product> products;
}
