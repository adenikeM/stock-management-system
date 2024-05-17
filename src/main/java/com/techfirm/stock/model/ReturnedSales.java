package com.techfirm.stock.model;

import com.techfirm.stock.model.enumeration.ReturnReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReturnReason reasonForReturn;

    @Column(length = 200)
    private String additionalComments;

    private LocalDate returnDate = LocalDate.now();


}
