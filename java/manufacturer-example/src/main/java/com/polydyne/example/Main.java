package com.polydyne.example;

import java.util.Base64;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {

    // Replace the values below with your username and password
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    /**
     * Builds and encodes the HTTP Basic Authentication Header in the correct format
     */
    public static HttpHeaders buildAuthHeader(String user, String password) {
        HttpHeaders headers = new HttpHeaders();
        byte[] bytes = (user + ":" + password).getBytes();
        headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(bytes)));
        return headers;
    }

    /**
     * Parses the link headers and returns the next URL if it exists
     */
    public static String getNextUrl(HttpHeaders headers) {
        for(String header : headers.get("Link")) {
            if (header.contains("rel=\"next\"")) {
                return header.substring(1, header.indexOf(">"));
            }
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<Manufacturer>> typeRef = new ParameterizedTypeReference<List<Manufacturer>>(){};
        HttpEntity<String> request = new HttpEntity<String>(buildAuthHeader(USERNAME, PASSWORD));

        String url = "https://api.polydyne.com/v1/manufacturers";
        while(url != null) {
            ResponseEntity<List<Manufacturer>> response = restTemplate.exchange(url, HttpMethod.GET, request, typeRef);
            List<Manufacturer> manufacturers = response.getBody();
            manufacturers.forEach(m -> System.out.println(m.getName()));
            url = getNextUrl(response.getHeaders());
            Thread.sleep(200);
        }
    }
}
