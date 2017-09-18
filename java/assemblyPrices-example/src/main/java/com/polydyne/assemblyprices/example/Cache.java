package com.polydyne.assemblyprices.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.assemblyprices.example.model.CostRollupAttribute;
import java.util.HashMap;
import java.util.Map;


public class Cache {
    public static Map<Long, CostRollupAttribute> costRollupAttributeCache = new HashMap<>();
    
    public static void initializeCache() throws UnirestException{
        cacheCostRollupAttributes();
    }
    
    private static void cacheCostRollupAttributes() throws UnirestException {
        String url = Main.API_BASE_URL + "/cost_rollup_attributes";
        while(url!=null){
            // Request page of units
            HttpResponse<CostRollupAttribute[]> rsp = HttpHelper.get(url).asObject(CostRollupAttribute[].class);

            // Map each unit code to unitId
            for(CostRollupAttribute costRollupAttribute: rsp.getBody()){
                costRollupAttributeCache.put(costRollupAttribute.getCostRollupAttributeId(), costRollupAttribute);
            }
            
            // Set the URL to pick the next page of data if necessary
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            HttpHelper.pause();
        }
    }
    
}
