package com.polydyne.api.bom.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomField {
    
    private String name;
    private String displayName;
    private String dataType;
    private String comboValuesUrl;
    
    public CustomField() {}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComboValuesUrl() {
        return comboValuesUrl;
    }

    public void setComboValuesUrl(String comboValuesUrl) {
        this.comboValuesUrl = comboValuesUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

}
