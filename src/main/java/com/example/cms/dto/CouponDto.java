package com.example.cms.dto;

import com.example.cms.entity.BXGYProducts;
import com.example.cms.entity.CouponType;
import com.example.cms.entity.Details;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
@Data
public class CouponDto implements Serializable {

    private Long couponId;
    private String couponCode;
    private BigDecimal discountPercentage;
    private BigDecimal thresholdAmount;
    private String couponType;

    private LocalDateTime expiryDate;
    private int repetitionLimit;
    private BXGYProducts bxgyProducts;
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

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
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
