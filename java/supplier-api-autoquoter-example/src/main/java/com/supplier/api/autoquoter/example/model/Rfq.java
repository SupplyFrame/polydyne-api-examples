package com.supplier.api.autoquoter.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rfq {
   
    private String rfqId;
    private List<RfqPriceVolume> volumes;
    private Double moqQuantity;   
    private Double leadtime;
    private Long manufacturerId;
    private String manufacturerName;
    private String manufacturerPartNumber;
    private Long customerId;
    private Long projectId;
    private Long siteId; //if its siteview else null
       
    public Long getSiteId() {
        return siteId;
    }
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
    public String getManufacturerName() {
        return manufacturerName;
    }
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getRfqId() {
        return rfqId;
    }
    public void setRfqId(String rfqId) {
        this.rfqId = rfqId;
    }

    public List<RfqPriceVolume> getVolumes() {
        return volumes;
    }
    public void setVolumes(List<RfqPriceVolume> volumes) {
        this.volumes = volumes;
    }
    public Double getMoqQuantity() {
        return moqQuantity;
    }
    public void setMoqQuantity(Double moqQuantity) {
        this.moqQuantity = moqQuantity;
    }
    public Double getLeadtime() {
        return leadtime;
    }
    public void setLeadtime(Double leadtime) {
        this.leadtime = leadtime;
    }
    public Long getManufacturerId() {
        return manufacturerId;
    }
    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    public String getManufacturerPartNumber() {
        return manufacturerPartNumber;
    }
    public void setManufacturerPartNumber(String manufacturerPartNumber) {
        this.manufacturerPartNumber = manufacturerPartNumber;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    
}
