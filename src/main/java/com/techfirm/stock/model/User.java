package com.techfirm.stock.model;

import com.techfirm.stock.constraint.ValidPassword;
import com.techfirm.stock.model.enumeration.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "user_profile")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Last name cannot be null")
    @Column(length = 20, nullable = false)
    private String lastName;

    @NotNull
    @Column(length = 20, nullable = false)
    private String firstName;

    private String otherNames;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Past
    private LocalDate birthDate;

    @NotEmpty
    @Column(name="user_name", nullable = false, unique = true, updatable = false)
    private String userName;

    @NotEmpty
    @Email
    private String email;

    @OneToOne
    private Address address;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 character long with " +
            "1 uppercase, 1 lowercase, 2 digit and 1 symbol")
    @ValidPassword
    private String password;

    @OneToOne
    private Role userRole;
}
