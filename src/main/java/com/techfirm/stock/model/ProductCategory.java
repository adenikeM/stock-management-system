package com.techfirm.stock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class ProductCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Category name cannot be null")
    @Column(length = 20, nullable = false)
    private String categoryName;

    @Past
    private LocalDate createdDate;

    @OneToOne
    private Location location;
}
