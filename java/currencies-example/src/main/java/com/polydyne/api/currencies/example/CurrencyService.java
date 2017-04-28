package com.polydyne.api.currencies.example;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.currencies.example.model.BatchRequest;
import com.polydyne.api.currencies.example.model.BatchResponse;
import com.polydyne.api.currencies.example.model.Currency;

public class CurrencyService {
    public static ObjectMapper mapper = new ObjectMapper();

    public static List<Currency> updateCurrencies(List<Currency> currencyList) throws UnirestException {
        List<Currency> updatedCurrencyList = new ArrayList<Currency>();
        List<List<Currency>> currencyBatches = HttpHelper.splitIntoBatches(currencyList, 100);

        for(List<Currency> batch : currencyBatches) {
            BatchRequest[] batchRequest = new BatchRequest[batch.size()];
            for(int i=0; i< batch.size();i++){
                batchRequest[i] = new BatchRequest();
                batchRequest[i].setMethod("PATCH");
                batchRequest[i].setResource("/currencies/"+ batch.get(i).getCurrencyId());
                batchRequest[i].setBody(batch.get(i));
            }

            HttpResponse<BatchResponse[]> rsp = HttpHelper.post("/batch")
                    .body(batchRequest)
                   .asObject(BatchResponse[].class);
            
            if(rsp.getStatus()!=200){
                System.out.println("Error in updating currencies");
            }
            else{
                System.out.println("Updated currencies");
            }

            // Extract response
            BatchResponse[] batchResponse = rsp.getBody();

            for(int i=0; i< batchResponse.length;i++){
                Currency c = mapper.convertValue(batchResponse[i].getBody(), Currency.class);
                updatedCurrencyList.add(c);
            }
        }
        return updatedCurrencyList;
        
    }

}
