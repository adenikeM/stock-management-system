package com.techfirm.stock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Product name cannot be null")
    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private Integer availableQuantity;

    @Column(length = 20, nullable = false)
    private BigDecimal price;

    @Column(length = 20, nullable = false)
    private String colour;

    @Past
    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    @Column(length = 20, nullable = false)
    private String size;

    @ManyToOne
    private ProductCategory productCategory;


    public Product(Long id, String name, int availableQuantity, java.math.BigDecimal price, java.time.LocalDate manufactureDate, java.time.LocalDate expiryDate, String size, ProductCategory productCategory) {
    }
}
