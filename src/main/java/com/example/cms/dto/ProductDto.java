package com.example.cms.dto;

import com.example.cms.entity.ProductType;
import com.example.cms.entity.ShoppingCart;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class ProductDto implements Serializable {

    private long productId;


    private ShoppingCart shoppingCart;


    private String productCode;


    private String productName;

    private BigDecimal productPrice;

    private String productType;

    private int productQuantity;

    private String couponDiscountPrice;

    private String couponUsed;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getCouponDiscountPrice() {
        return couponDiscountPrice;
    }

    public void setCouponDiscountPrice(String couponDiscountPrice) {
        this.couponDiscountPrice = couponDiscountPrice;
    }

    public String getCouponUsed() {
        return couponUsed;
    }

    public void setCouponUsed(String couponUsed) {
        this.couponUsed = couponUsed;
    }
}
