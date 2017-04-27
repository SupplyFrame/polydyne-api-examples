package com.polydyne.example;

import java.util.Date;


public class Manufacturer {

    private Integer manufacturerId;
    private String name;
    private String shortName;
    private String cageCode;
    private String nedaCode;
    private Date timeStamp;
    
    public Manufacturer() {}

    public Integer getManufacturerId() {
        return manufacturerId;
    }
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getCageCode() {
        return cageCode;
    }
    public void setCageCode(String cageCode) {
        this.cageCode = cageCode;
    }
    public String getNedaCode() {
        return nedaCode;
    }
    public void setNedaCode(String nedaCode) {
        this.nedaCode = nedaCode;
    }
    public Date getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
