package com.techfirm.stock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
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

    @NotEmpty(message = "Sale cannot be null")
    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private Integer quantitySold;

    @Column(length = 20, nullable = false)
    private BigDecimal price;

    @Past
    private LocalDate saleDate;

    @ManyToMany
    private List <Product> products;
}
