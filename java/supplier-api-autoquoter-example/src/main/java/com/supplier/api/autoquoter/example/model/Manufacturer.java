package com.supplier.api.autoquoter.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Manufacturer {
    
    private Long manufacturerId;
    private String name;
    
    public Long getManufacturerId() {
        return manufacturerId;
    }
    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
