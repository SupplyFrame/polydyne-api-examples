package com.polydyne.api.bom.example.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Manufacturer {
	
    private Long manufacturerId;

    @Size(max=25)
    private String cageCode;

    @NotNull
    @Size(max=100)
    private String name;

    @NotNull
    @Size(max=10)
    private String shortName;

    @Size(max=5)
    private String nedaCode;

    private Date timeStamp;

    public Manufacturer() {}

    public Long getManufacturerId() {
        return this.manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getCageCode() {
        return this.cageCode;
    }

    public void setCageCode(String cageCode) {
        this.cageCode = cageCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNedaCode() {
        return this.nedaCode;
    }

    public void setNedaCode(String nedaCode) {
        this.nedaCode = nedaCode;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}
