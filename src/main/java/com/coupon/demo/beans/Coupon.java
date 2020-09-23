package com.coupon.demo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "coupons", uniqueConstraints = {@UniqueConstraint(columnNames = {"company_id" ,
        "title"})})
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")   
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private Company company;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Customer> customer;


    public Coupon(Category category, Company company, String title, String description, LocalDate startDate, LocalDate endDate, Integer amount, Double price, String image) {
        this.category = category;
        this.company = company;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id.equals(coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
