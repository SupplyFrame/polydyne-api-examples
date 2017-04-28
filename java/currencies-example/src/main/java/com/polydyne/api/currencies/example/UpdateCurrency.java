package com.polydyne.api.currencies.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.currencies.example.model.Currency;

public class UpdateCurrency {

    // Maps 
    private static Map<String, Double> currentExchangeRateMap = new HashMap<String, Double>();
    private static Map<String, Double> currencyCodeAndRateMap = new HashMap<String, Double>();
    private static Map<String, Long> currencyCodeAndIdMap = new HashMap<String, Long>();
    private static List<Currency> currencyList = new ArrayList<Currency>();


    public void getExchangeRates() throws UnirestException {
        // Fetch latest exchange rates from the open exchange rate API
        String url = Main.OPEN_EXCHANGE_RATES_URL + "/latest.json?app_id="+Main.APP_ID;
        if(url!=null){
      
            HttpResponse<JsonNode> rsp = HttpHelper.get(url).asJson();
            String json = rsp.getBody().getObject().get("rates").toString();
            JSONObject jsonObject = new JSONObject(json.trim());

            Iterator<?> keys = jsonObject.keys();

            // Map currency code to exchange rate fetched from open exchange rates API
            while( keys.hasNext() ) {
                String key = (String) keys.next();
                Double value = (Double) jsonObject.getDouble(key);
                currentExchangeRateMap.put(key, value);
            }           
        }
    }

    public void getCurrency() throws UnirestException {

        String url = Main.API_BASE_URL + "/currencies";
        if(url!=null){
            // Request page of currencies
            HttpResponse<Currency[]> rsp = HttpHelper.get(url).asObject(Currency[].class);
            for(Currency currency: rsp.getBody()){
                // Map each currency code to currency rate
                currencyCodeAndRateMap.put(currency.getCurrencyCode(), currency.getExchangeRate());
                // Map each currency code to currencyId
                currencyCodeAndIdMap.put(currency.getCurrencyCode(), currency.getCurrencyId());
            }
        }       
    }

    public void updateCurrencyRates() throws UnirestException {
        
        // Use the maps to find a list of currencies to be updated
        for(Map.Entry<String, Double> entry:currencyCodeAndRateMap.entrySet()){
            
            String key = entry.getKey();
            if(currentExchangeRateMap.containsKey(key)){
                
                if(currentExchangeRateMap.get(key).compareTo(currencyCodeAndRateMap.get(key))!=0){
                    // Add currencies with new exchange rates to the list
                    Currency currency = new Currency();
                    currency.setCurrencyId(currencyCodeAndIdMap.get(key));
                    currency.setCurrencyCode(key);
                    currency.setExchangeRate(currentExchangeRateMap.get(key));
                    
                    currencyList.add(currency);
                }
                else{
                    System.out.println("Skipping "+ entry.getKey());
                }           
            }
            else{
                System.out.println("Ignoring "+ entry.getKey());
            }
        }
        
        // Update currencies using batch API 
       List<Currency> l = CurrencyService.updateCurrencies(currencyList);
       for(int i =0; i<l.size();i++){
           System.out.println(l.get(i).getCurrencyCode() + " "+ l.get(i).getExchangeRate());
       }
    }
}
