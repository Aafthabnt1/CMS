package com.example.cms.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Products extends BaseClass implements Serializable {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private long productId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="cartId")
    private ShoppingCart shoppingCart;

    @Column(name = "product_code",unique = true)
    private String productCode;

    @Column(name = "product_name")
    private String productName;
    @Column(name="product_price")
    private BigDecimal productPrice;
    @Column(name="product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    @Column(name="product_quantity")
    private int productQuantity;
    @Column(name="coupon_discount")
    private String coupon_discount;

    @Column(name="coupon_used")
    private String coupon_used;


}
