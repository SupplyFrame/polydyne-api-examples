package com.supplier.api.autoquoter.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RfqPriceVolume {

    private Long volumeId;
    private Double price;
    
    public Long getVolumeId() {
        return volumeId;
    }
    public void setVolumeId(Long volumeNum) {
        this.volumeId = volumeNum;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}
