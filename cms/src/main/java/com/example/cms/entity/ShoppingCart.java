package com.example.cms.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private List<Products> products=new ArrayList<>();

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @Column(name="totalDiscount")
    private BigDecimal totalDiscount;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products.add(products) ;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
}
