package com.polydyne.assemblyprices.example.creation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.assemblyprices.example.HttpHelper;
import com.polydyne.assemblyprices.example.Main;
import com.polydyne.assemblyprices.example.model.CustomManufacturer;

public class CustomManufacturerService {
    
    public static CustomManufacturer getCustomManufacturer(String customManufacturerId) throws UnirestException {
        String url = Main.API_BASE_URL + "/custom_manufacturers/" + customManufacturerId;
        HttpResponse<CustomManufacturer> rsp = HttpHelper.get(url).asObject(CustomManufacturer.class);
        
        return (CustomManufacturer)rsp.getBody();
    }
    
    
}
