package com.polydyne.api.currencies.example;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Main {
    
    public static String API_BASE_URL = "https://api.polydyne.com/v1";
    public static String OPEN_EXCHANGE_RATES_URL = "https://openexchangerates.org/api";
    public static String USERNAME;
    public static String PASSWORD;
    public static String APP_ID;
    
    public static void main(String[] args) throws InterruptedException, IOException, UnirestException {
        
        USERNAME = System.getProperty("username");
        PASSWORD = System.getProperty("password");
        APP_ID = System.getProperty("app_id");

        // Initialize unirest
        initializeUnirest();
        
        UpdateCurrency updateCurrency = new UpdateCurrency();
        // Get latest exchange rates
        updateCurrency.getExchangeRates();
        // Get currencies
        updateCurrency.getCurrency();
        // Update currency exchange rates
        updateCurrency.updateCurrencyRates();
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
