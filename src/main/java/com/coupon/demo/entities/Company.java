package com.coupon.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @JsonIgnore
    private Long id;

    @Column(name = "name", length = 30, nullable = false, updatable = false, unique = true)
    private String name;

    @Column(name = "email", length = 100, nullable = false,  unique = true)
    private String email;

    @Column(name = "password", length = 200, nullable = false)
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Coupon> coupons;

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Company(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
