package com.polydyne.example;

import java.util.ArrayList;
import java.util.List;

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

    public static <T> List<List<T>> splitIntoBatches(List<T> list, int batchSize) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (int i=0; i<list.size(); i+=batchSize) {
            result.add(list.subList(i, Math.min(i + batchSize, list.size())));
        }
        return result;
    }
}
