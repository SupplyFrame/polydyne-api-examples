package com.supplier.api.autoquoter.example.model;

public class ManufacturerInfoModel {
    
    private String manufacturerName;
    
    private String manufacturerPartNumber;

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerPartNumber() {
        return manufacturerPartNumber;
    }

    public void setManufacturerPartNumber(String manufacturerPartNumber) {
        this.manufacturerPartNumber = manufacturerPartNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((manufacturerName == null) ? 0 : manufacturerName.hashCode());
        result = prime * result + ((manufacturerPartNumber == null) ? 0 : manufacturerPartNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ManufacturerInfoModel other = (ManufacturerInfoModel) obj;
        if (manufacturerName == null) {
            if (other.manufacturerName != null)
                return false;
        } else if (!manufacturerName.equals(other.manufacturerName))
            return false;
        if (manufacturerPartNumber == null) {
            if (other.manufacturerPartNumber != null)
                return false;
        } else if (!manufacturerPartNumber.equals(other.manufacturerPartNumber))
            return false;
        return true;
    }



    
    
    
    

}
