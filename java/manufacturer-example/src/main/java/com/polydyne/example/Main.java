package com.polydyne.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Main {

    // Replace the values below with your username and password
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static void main(String[] args) throws InterruptedException, UnirestException {

        initializeUnirest();
        String url = "https://api.polydyne.com/v1/manufacturers";
        while(url!=null){
            // Request page of manufacturers
            HttpResponse<Manufacturer[]> rsp = HttpHelper.get(url).asObject(Manufacturer[].class);
            List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
            for(Manufacturer manufacturer : rsp.getBody()){
                manufacturers.add(manufacturer);
            }
            manufacturers.forEach(m -> System.out.println(m.getName()));
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            Thread.sleep(200);
        }   
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
