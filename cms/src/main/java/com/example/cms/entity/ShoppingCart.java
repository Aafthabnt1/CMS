package com.example.cms.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shopping_cart")
public class ShoppingCart extends BaseClass implements Serializable {
    @Column(name = "cart_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long cartId;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "shoppingCart",orphanRemoval = true,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    List<Products> products=new ArrayList<>();
}
