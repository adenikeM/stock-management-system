package com.techfirm.stock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sales implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_info_id")
    private CustomerInfo customerInfo;

    @Column(name= "quantity_sold", length = 20, nullable = false)
    private Integer totalQuantitySold;

    @Column(length = 20, nullable = false)
    private BigDecimal price;

    @Past
    @Column(name = "sale_date")
    private LocalDateTime salesDate = LocalDateTime.now();

    @ManyToMany
    private List <Product> products;
}
