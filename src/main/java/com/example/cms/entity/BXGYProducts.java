package com.example.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BXGYProducts implements Serializable {
   private List<String> productCode;
   private List<String> giveFreeProductCode;

    public List<String> getProductCode() {
        return productCode;
    }

    public void setProductCode(List<String> productCode) {
        this.productCode = productCode;
    }

    public List<String> getGiveFreeProductCode() {
        return giveFreeProductCode;
    }

    public void setGiveFreeProductCode(List<String> giveFreeProductCode) {
        this.giveFreeProductCode = giveFreeProductCode;
    }
}
