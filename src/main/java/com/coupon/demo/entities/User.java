package com.coupon.demo.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class Users {

    @Id
    private Long id;

    private String username;

    private String password;

    private String authority;
}
