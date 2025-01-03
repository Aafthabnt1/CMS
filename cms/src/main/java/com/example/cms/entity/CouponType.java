package com.example.cms.entity;

public enum CouponType {
    CART_WISE("Cart-wise"),
    PRODUCT_WISE("Product-wise"),
    BUY_X_GET_Y("BxGy");

    private String couponType;
    CouponType(String s) {
        this.couponType=s;
    }

    public String getCouponType() {
        return couponType;
    }
}
