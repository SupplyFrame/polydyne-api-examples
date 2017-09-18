package com.polydyne.assemblyprices.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomManufacturer {

    /**
     * @return the customManufacturerId
     */
    public Long getCustomManufacturerId() {
        return customManufacturerId;
    }

    /**
     * @param customManufacturerId the customManufacturerId to set
     */
    public void setCustomManufacturerId(Long customManufacturerId) {
        this.customManufacturerId = customManufacturerId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the currencyId
     */
    public Long getCurrencyId() {
        return currencyId;
    }

    /**
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    private Long customManufacturerId;
    private String name;
    
    // We do not list all of the properties in this example
    
    private Long currencyId;    
    
}
