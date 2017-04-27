package com.polydyne.api.bom.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Unit {
    
    private long unitId;
    private String unitCode;
    
    public Unit() {}
    
    public long getUnitId() {
        return unitId;
    }
    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }
    public String getUnitCode() {
        return unitCode;
    }
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    
    

}
