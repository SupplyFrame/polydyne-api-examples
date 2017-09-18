package com.polydyne.assemblyprices.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.assemblyprices.example.creation.AssemblyPriceService;
import com.polydyne.assemblyprices.example.creation.BomItemService;
import com.polydyne.assemblyprices.example.model.BomItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This sample code is provided "as-is" without any warranties of any kind.
 */
public class Main {

    // TODO: update to production URL later
    public static String API_BASE_URL = "http://localhost:9099";
//    public static String API_BASE_URL = "https://api.polydyne.com/v1";
    
    public static String USERNAME;
    public static String PASSWORD;
    public static String PROJECT_ID;    

    public static String PROJECT_DESCRIPTION = "Test project created by API";
    public static int VOLUME_NUMBER = 1; // This example only print volume 1

    //global maps and lists
    public static Map<String, Long> partMap = new HashMap<String, Long>();
    public static Map<String, Long> assemblyMap = new HashMap<String, Long>(); 
    public static ArrayList<String> manufacturerPartList = new ArrayList<String>();
    public static ArrayList<String> partList = new ArrayList<String>();

    /**
     * 
     * @param args
     * @throws InterruptedException
     * @throws IOException
     * @throws UnirestException 
     */
    public static void main(String[] args) throws InterruptedException, IOException, UnirestException {
        USERNAME = System.getProperty("username");
        PASSWORD = System.getProperty("password");
        PROJECT_ID = System.getProperty("projectId");

        // Initialize unirest
        initializeUnirest();

        // Populate caches
        System.out.println("Populating caches");
        Cache.initializeCache();
        
        HashMap<Long, BomItem> topAssemblies = BomItemService.getTopAssemblies(PROJECT_ID);
        
        Map<String, Map> assmPricing = AssemblyPriceService.getAssemblyPrices(PROJECT_ID);
         
        // Print Outsource Pricing table for volume ID 1 only
        AssemblyPriceService.print(topAssemblies, assmPricing, VOLUME_NUMBER);
        
    }


    private static void initializeUnirest() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
            = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
