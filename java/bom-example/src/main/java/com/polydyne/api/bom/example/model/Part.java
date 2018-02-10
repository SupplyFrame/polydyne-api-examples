package com.polydyne.api.bom.example.model;

import java.util.Date;

public class Part {

	private Long partId;

    private Double referencePrice;

    private Long combo1;

    private Long combo2;

    private Long commodityId;

    private String description;

    private String referencePartNumber;

    private Long projectId;

    private Long packageTypeId;

    private String partNumber;

    private Long partStatusId;

    private String revision;

    private Double standardCost;

    private Double targetPrice;

    private Date timeStamp;

    private Long unitId;
    
    private String assemblyName; //added to have associated Id
    
    private Double customManufacturerTargetPrice;
    
    

    public Long getPartId() {
        return this.partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Double getReferencePrice() {
        return this.referencePrice;
    }

    public void setReferencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
    }

    public Long getCombo1() {
        return this.combo1;
    }

    public void setCombo1(Long combo1) {
        this.combo1 = combo1;
    }

    public Long getCombo2() {
        return this.combo2;
    }

    public void setCombo2(Long combo2) {
        this.combo2 = combo2;
    }

    public Long getCommodityId() {
        return this.commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferencePartNumber() {
        return this.referencePartNumber;
    }

    public void setReferencePartNumber(String referencePartNumber) {
        this.referencePartNumber = referencePartNumber;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getPackageTypeId() {
        return this.packageTypeId;
    }

    public void setPackageTypeId(Long packageTypeId) {
        this.packageTypeId = packageTypeId;
    }

    public String getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Long getPartStatusId() {
        return this.partStatusId;
    }

    public void setPartStatusId(Long partStatusId) {
        this.partStatusId = partStatusId;
    }

    public String getRevision() {
        return this.revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Double getStandardCost() {
        return this.standardCost;
    }

    public void setStandardCost(Double standardCost) {
        this.standardCost = standardCost;
    }

    public Double getTargetPrice() {
        return this.targetPrice;
    }

    public void setTargetPrice(Double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }



    public String getAssemblyName() {
        return assemblyName;
    }

    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }

    public Double getCustomManufacturerTargetPrice() {
        return customManufacturerTargetPrice;
    }

    public void setCustomManufacturerTargetPrice(Double customManufacturerTargetPrice) {
        this.customManufacturerTargetPrice = customManufacturerTargetPrice;
    }
}
