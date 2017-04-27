package com.polydyne.api.bom.example.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ManufacturerPart {

    @NotNull
    private Long manufacturerId;

    @NotNull
    private Boolean aml;

    private Long manufacturerPartId;

    @Size(max=100)
    private String internalManufacturerPartNumber;

    @Size(max=2000)
    private String longComment;

    @Size(max=25)
    private String matrixCategory;

    @Size(max=100)
    private String manufacturerPartNumber;

    private Long mpnStatus;

    private Long projectId;

    @NotNull
    private Boolean obsolete;

    private BigDecimal oemPercentage;

    private BigDecimal oemPrice;

    private Long partId;

    @Size(max=500)
    private String shortComment;

    private Date timeStamp;


    public Long getManufacturerId() {
        return this.manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Boolean getAml() {
        return this.aml;
    }

    public void setAml(Boolean aml) {
        this.aml = aml;
    }

    public Long getManufacturerPartId() {
        return this.manufacturerPartId;
    }

    public void setManufacturerPartId(Long manufacturerPartId) {
        this.manufacturerPartId = manufacturerPartId;
    }

    public String getInternalManufacturerPartNumber() {
        return this.internalManufacturerPartNumber;
    }

    public void setInternalManufacturerPartNumber(String internalManufacturerPartNumber) {
        this.internalManufacturerPartNumber = internalManufacturerPartNumber;
    }

    public String getLongComment() {
        return this.longComment;
    }

    public void setLongComment(String longComment) {
        this.longComment = longComment;
    }

    public String getMatrixCategory() {
        return this.matrixCategory;
    }

    public void setMatrixCategory(String matrixCategory) {
        this.matrixCategory = matrixCategory;
    }

    public String getManufacturerPartNumber() {
        return this.manufacturerPartNumber;
    }

    public void setManufacturerPartNumber(String manufacturerPartNumber) {
        this.manufacturerPartNumber = manufacturerPartNumber;
    }

    public Long getMpnStatus() {
        return this.mpnStatus;
    }

    public void setMpnStatus(Long mpnStatus) {
        this.mpnStatus = mpnStatus;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Boolean getObsolete() {
        return this.obsolete;
    }

    public void setObsolete(Boolean obsolete) {
        this.obsolete = obsolete;
    }

    public BigDecimal getOemPercentage() {
        return this.oemPercentage;
    }

    public void setOemPercentage(BigDecimal oemPercentage) {
        this.oemPercentage = oemPercentage;
    }

    public BigDecimal getOemPrice() {
        return this.oemPrice;
    }

    public void setOemPrice(BigDecimal oemPrice) {
        this.oemPrice = oemPrice;
    }

    public Long getPartId() {
        return this.partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public String getShortComment() {
        return this.shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }


}
