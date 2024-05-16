package com.techfirm.stock.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnedSales implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @Getter
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 20, nullable = false)
    private Integer quantityReturned;

    @Column(nullable = false)
    private String reasonForReturn;

    @Column(name = "return_date")
    private LocalDate returnDate = LocalDate.now();


}
