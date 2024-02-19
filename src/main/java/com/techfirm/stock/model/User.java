package com.techfirm.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String lastName;
    private String firstName;
    private String otherName;
    private Enum gender;
    private Date birthDate;
    private String userName;
    private String address;
    private String password;


}
