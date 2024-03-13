package com.techfirm.stock.model;

import com.techfirm.stock.model.enumeration.OrderStatus;
import com.techfirm.stock.model.enumeration.QualityCheck;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class ProductOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productOrderId;

    @Column(length = 20, nullable = false)
    private Double quantityOrder;

    @Column(length = 20, nullable = false)
    private Double pricePerUnit;

    @Enumerated(EnumType.STRING)
    private QualityCheck qualityCheck;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Past
    private LocalDate orderedDate;

    @Past
    private LocalDate deliveryDate;

}
