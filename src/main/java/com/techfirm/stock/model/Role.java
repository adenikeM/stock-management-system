package com.techfirm.stock.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Role Title cannot be null")
    @Column(length = 20, nullable = false)
    private String roleTitle;

    @OneToOne
    private User user;


}
