package com.example.cms.entity;

public enum CouponType {
    Cart_wise("Cart_wise"),
    Product_wise("Product_wise"),
    BxGy("BxGy");

    private String couponType;
    CouponType(String s) {
        this.couponType=s;
    }

    public String getCouponType() {
        return couponType;
    }


}
