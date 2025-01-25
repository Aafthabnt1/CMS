package com.example.cms.dto;

import com.example.cms.entity.Products;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto implements Serializable {
    private Long cartId;
    private BigDecimal totalPrice;//if ui is giving the total price in backend we can validate it for that purpose i have used it
    private BigDecimal totalDiscountPrice;
    private BigDecimal finalPrice;
    private List<ProductDto> products=new ArrayList<>();
    private List<ProductDto> freeProducts=new ArrayList<>();

    public List<ProductDto> getFreeProducts() {
        return freeProducts;
    }

    public void setFreeProducts(List<ProductDto> freeProducts) {
        this.freeProducts = freeProducts;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public BigDecimal getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(BigDecimal totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
