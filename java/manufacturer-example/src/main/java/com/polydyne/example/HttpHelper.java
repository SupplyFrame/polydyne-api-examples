package com.polydyne.example;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

public class HttpHelper {

    public static GetRequest get(String url) {
        return Unirest.get(url).basicAuth(Main.USERNAME, Main.PASSWORD);
    }

    public static String getNextUrl(Headers headers) {
        for(String header : headers.get("Link")) {
            if (header.contains("rel=\"next\"")) {
                return header.substring(1, header.indexOf(">"));
            }
        }
        return null;
    }

    public static void pause() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}
