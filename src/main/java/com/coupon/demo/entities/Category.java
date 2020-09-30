package com.coupon.demo.entities;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, unique = true)
    private CategoryType category;

    public Category(CategoryType category) {
        this.category = category;
    }
}
