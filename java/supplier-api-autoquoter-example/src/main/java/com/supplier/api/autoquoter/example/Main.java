package com.supplier.api.autoquoter.example;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class Main {

    public static String API_BASE_URL = "https://api.polydyne.com/v1";
    public static String USERNAME;
    public static String PASSWORD;
    public static String INPUT_FILE;            

    public static void main(String[] args) throws InterruptedException, IOException, UnirestException {
        USERNAME = System.getProperty("username");
        PASSWORD = System.getProperty("password");
        INPUT_FILE = System.getProperty("input_file");

        //read from tsv and populate maps
        System.out.println("Reading file");
        TsvParser.createMaps(INPUT_FILE);
        // Initialize unirest
        initializeUnirest();
        System.out.println("Populating caches");
        // Get all customers for the supplier
        Cache.cacheCustomer();
        // For each customer, get the manufacturers 
        Cache.cacheManufacturersForCustomer();
        // Get projects
        Cache.getProjectsForCustomers();
        //Get siteview projects
        Cache.getSiteViews();
        //For each project get rfq list 
        Cache.getRfqsForProjectPerCustomer();
        
        System.out.println("Autoquoting");
        // Autoquote RFQs : leadtime, moq, price
        RfqService rfqService = new RfqService();
        rfqService.autoQuoteRfqs();
        System.out.println("Autoquoting complete");

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
