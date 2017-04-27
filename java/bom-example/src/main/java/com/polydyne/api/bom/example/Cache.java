package com.polydyne.api.bom.example;

import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.bom.example.model.ComboValue;
import com.polydyne.api.bom.example.model.Commodity;
import com.polydyne.api.bom.example.model.CustomField;
import com.polydyne.api.bom.example.model.Manufacturer;
import com.polydyne.api.bom.example.model.MpnStatus;
import com.polydyne.api.bom.example.model.PackageType;
import com.polydyne.api.bom.example.model.Unit;

public class Cache {
    
    public static Map<String, Long> commodityCache = new HashMap<String, Long>();
    public static Map<String, Long> unitCache = new HashMap<String, Long>();
    public static Map<String, Long> packageTypeCache = new HashMap<String, Long>();
    public static Map<String, Long> mpnStatusCache = new HashMap<String, Long>();
    public static Map<String, Long> manufacturerCache = new HashMap<String, Long>();
    public static Map<String, String> customFieldCache = new HashMap<String, String>();
    public static Map<String, String> customFieldDisplayNameCache = new HashMap<String, String>();
    public static Map<String, Long> comboValuesCache1 = new HashMap<String, Long>();
    public static Map<String, Long> comboValuesCache2 = new HashMap<String, Long>();

    public static String customField1 = null;
    public static String customField2 = null;

    
    public static void initializeCache() throws UnirestException{
        
        cacheCommodities();
        cacheUnits();
        cacheManufacturers();
        cacheMpnStatuses();
        cachePackageTypes();
        cacheCustomFields();
        cacheCustomFieldDisplayName();
    }

    
    private static void cacheCustomFieldDisplayName() throws UnirestException {
        String url = Main.API_BASE_URL + "/custom_fields";
        if(url!=null){
            // Request page of custom fields
            HttpResponse<CustomField[]> rsp = HttpHelper.get(url).asObject(CustomField[].class);

            // Map each package type code to packageTypeId
            for(CustomField customField: rsp.getBody()){
                if(customField.getDataType().equals("combo")){
                    customFieldDisplayNameCache.put(customField.getDisplayName(), customField.getName()); //changed
                }
            }
        }
    }

    public static void cacheComboValues2() throws UnirestException {
        // Get your custom field name
        String url = customFieldCache.get(customField2);
        if(url!=null){
            // Request page of combo values
            HttpResponse<ComboValue[]> rsp =  HttpHelper.get(url).asObject(ComboValue[].class);

            // Map each value to comboId
            for(ComboValue comboValue: rsp.getBody()){
                comboValuesCache2.put(comboValue.getValue(), comboValue.getComboId());
            }
        }
    }

    public static void cacheComboValues1() throws UnirestException {
        // Get your custom field name
        String url = customFieldCache.get(customField1);
        if(url!=null){
            //Request page of combo values
            HttpResponse<ComboValue[]> rsp =  HttpHelper.get(url).asObject(ComboValue[].class);

            // Map each value to comboId
            for(ComboValue comboValue: rsp.getBody()){
                comboValuesCache1.put(comboValue.getValue(), comboValue.getComboId());
            }
        }
    }

    private static void cacheCustomFields() throws UnirestException {
        String url = Main.API_BASE_URL + "/custom_fields";
        if(url!=null){
            //Request page of custom fields
            HttpResponse<CustomField[]> rsp = HttpHelper.get(url).asObject(CustomField[].class);

            //Map each package type code to packageTypeId
            for(CustomField customField: rsp.getBody()){
                if(customField.getDataType().equals("combo")){
                    customFieldCache.put(customField.getDisplayName(), customField.getComboValuesUrl());
                }
            }
        }
    }

    private static void cachePackageTypes() throws UnirestException {
        String url = Main.API_BASE_URL + "/package_types";
        while(url!=null){
            // Request page of package types
            HttpResponse<PackageType[]> rsp = HttpHelper.get(url).asObject(PackageType[].class);

            // Map each package type code to packageTypeId
            for(PackageType packageType: rsp.getBody()){
                packageTypeCache.put(packageType.getPackageTypeCode(), packageType.getPackageTypeId());
            }
            // Parse next URL from response headers (if available)
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            // Pause to avoid violating rate limit
            HttpHelper.pause();
        }

    }

    private static void cacheMpnStatuses() throws UnirestException {
        String url = Main.API_BASE_URL + "/mpn_statuses";
        while(url!=null){
            // Request page of mpn statuses
            HttpResponse<MpnStatus[]> rsp = HttpHelper.get(url).asObject(MpnStatus[].class);

            // Map each mpn status code to mpnStatusId
            for(MpnStatus mpnStatus: rsp.getBody()){
                mpnStatusCache.put(mpnStatus.getMpnStatusCode(), mpnStatus.getMpnStatusId());
            }

            url = HttpHelper.getNextUrl(rsp.getHeaders());

            HttpHelper.pause();
        }  
    }

    private static void cacheManufacturers() throws UnirestException {
        String url = Main.API_BASE_URL + "/manufacturers";
        while(url!=null){
            // Request page of manufacturers
            HttpResponse<Manufacturer[]> rsp = HttpHelper.get(url).asObject(Manufacturer[].class);

            // Map each manufacturer short name to manufacturerId
            for(Manufacturer manufacturer: rsp.getBody()){
                manufacturerCache.put(manufacturer.getShortName(), manufacturer.getManufacturerId());
            }
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            HttpHelper.pause();
        } 

    }

    private static void cacheUnits() throws UnirestException {
        String url = Main.API_BASE_URL + "/units";
        while(url!=null){
            // Request page of units
            HttpResponse<Unit[]> rsp = HttpHelper.get(url).asObject(Unit[].class);

            // Map each unit code to unitId
            for(Unit unit: rsp.getBody()){
                unitCache.put(unit.getUnitCode(), unit.getUnitId());
            }
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            HttpHelper.pause();
        }     
    }

    private static void cacheCommodities() throws UnirestException {
        String url = Main.API_BASE_URL + "/commodities";
        while(url != null){
            // Request page of commodities
            HttpResponse<Commodity[]> rsp = HttpHelper.get(url).asObject(Commodity[].class);

            // Map each commodity code to commodityId
            for (Commodity commodity : rsp.getBody()) {
                commodityCache.put(commodity.getCommodityCode(), commodity.getCommodityId());
            }

            url = HttpHelper.getNextUrl(rsp.getHeaders());

            HttpHelper.pause();
        }
    }

}
