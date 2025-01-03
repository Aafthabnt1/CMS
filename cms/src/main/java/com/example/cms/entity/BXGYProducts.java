package com.example.cms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class BXGYProducts implements Serializable {
    List<Long> productID;
    List<Long> giveFreeProductId;

    public List<Long> getProductID() {
        return productID;
    }

    public void setProductID(List<Long> productID) {
        this.productID = productID;
    }

    public List<Long> getGiveFreeProductId() {
        return giveFreeProductId;
    }

    public void setGiveFreeProductId(List<Long> giveFreeProductId) {
        this.giveFreeProductId = giveFreeProductId;
    }
}
