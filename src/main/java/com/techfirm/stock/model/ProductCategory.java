package com.techfirm.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class ProductCategory implements Serializable {
    @Id
    private Integer id;
    private String categoryName;
    private Date createdDate;
    @OneToOne
    private Location location;
}
