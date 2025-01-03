package com.example.cms.entity;

public enum ProductType {
    ELECTRONIC("ELECTRONIC"),
    FURNITURE("FURNITURE"),
    HOUSE_ITEM("HouseItem");
    //etc based on scenerios we can add the types
    private String type;
     ProductType(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }
}
