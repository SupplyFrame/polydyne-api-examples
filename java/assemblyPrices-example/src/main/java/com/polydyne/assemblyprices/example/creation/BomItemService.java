package com.polydyne.assemblyprices.example.creation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.assemblyprices.example.HttpHelper;
import com.polydyne.assemblyprices.example.Main;
import com.polydyne.assemblyprices.example.model.BomItem;
import java.util.HashMap;

public class BomItemService {

    /**
     * Get a list of top assemblies 
     * @param projectId project Id of the target project
     * @return a HashMap<Long, BomItem> where the key is a componentId
     * @throws UnirestException 
     */
    public static HashMap<Long, BomItem> getTopAssemblies(String projectId) throws UnirestException {
        // setting filter to parentAssemblyId=0 returns only top level assemblies
        String url = Main.API_BASE_URL + "/projects/" + projectId + "/bom_items?parentAssemblyId=0";
        
        HashMap<Long, BomItem> bomItemMap = new HashMap<>();
        while (url != null) {
            HttpResponse<BomItem[]> rsp = HttpHelper.get(url).asObject(BomItem[].class);
            
            for(BomItem bomItem : rsp.getBody()){
                bomItemMap.put(bomItem.getComponentId(), bomItem);
            }
            
            // Go to next page if necessary
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            HttpHelper.pause();
        }

        return bomItemMap;
    }
    
}
