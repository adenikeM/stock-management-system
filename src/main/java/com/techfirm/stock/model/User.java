package com.techfirm.stock.model;

import com.techfirm.stock.constraint.ValidPassword;
import com.techfirm.stock.model.enumeration.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile")
@ToString
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne
    private Role userRole;

    public User(String lastName,String firstName,  String userName, String email,  String password){
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String lastName, String firstName, String userName, String email, String password, Address address, Role userRole) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.getRoleTitle()));

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
