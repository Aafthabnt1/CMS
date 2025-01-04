package com.example.cms.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="coupons")

public class Coupon extends BaseClass implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long couponId;

    @Column(name="coupon_code",unique = true)
    private String couponCode;

    @Column(name="cart_discount")
    private BigDecimal discountPercentage;

    @Column(name="threshold_amount")
    private BigDecimal thresholdAmount;


    @Enumerated(EnumType.STRING)
    @Column(name="coupon_type")
    private CouponType couponType;

    @Column(name="expired_date")
    private LocalDateTime expiryDate;

    @Column(name="Repetition_Limit")
    private int repetitionLimit;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "bxgy_products_offer",columnDefinition = "json")
    private BXGYProducts bxgyProducts;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="product_wise_product_id_details",columnDefinition = "json")
    private Set<Details> productCodes;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getRepetitionLimit() {
        return repetitionLimit;
    }

    public void setRepetitionLimit(int repetitionLimit) {
        this.repetitionLimit = repetitionLimit;
    }

    public BXGYProducts getBxgyProducts() {
        return bxgyProducts;
    }

    public void setBxgyProducts(BXGYProducts bxgyProducts) {
        this.bxgyProducts = bxgyProducts;
    }

    public Set<Details> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(Set<Details> productCodes) {
        this.productCodes = productCodes;
    }
}
