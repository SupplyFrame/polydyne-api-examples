package com.polydyne.api.bom.example.creation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.bom.example.HttpHelper;
import com.polydyne.api.bom.example.Main;
import com.polydyne.api.bom.example.model.AssemblyVolume;
import com.polydyne.api.bom.example.model.BatchRequest;
import com.polydyne.api.bom.example.model.BatchResponse;
import com.polydyne.api.bom.example.model.BomItem;
import com.polydyne.api.bom.example.model.ParsedBomLine;

public class BomItemService {

    public static BomItem parseBomItem(ParsedBomLine parseBomLine) {
        /*
         * For each assemblyId and partId pair, a unique bom item is created. 
         * On creating a bom item, identify if it is a top level assembly. Update volumes for them.
         */

        BomItem bomItem = new BomItem();
        Long assemblyId = null;
        bomItem.setAssembly(parseBomLine.isSubassemblyFlag());
        if(parseBomLine.isSubassemblyFlag()==true){
            // If assembly is top level, the parent Assembly Id is 0
            assemblyId = Main.assemblyMap.get(parseBomLine.getPartNumber());

            if(parseBomLine.getAssemblyName().isEmpty()){
                // Top level assembly
                bomItem.setParentAssemblyId(0L); //should be 0
                bomItem.setComponentId(assemblyId);
            }
            else{
                // Sub assembly
                Long parentAssemblyId = Main.assemblyMap.get(parseBomLine.getAssemblyName());// will get its Id
                bomItem.setParentAssemblyId(parentAssemblyId);
                bomItem.setComponentId(assemblyId);
            }
        }
        else{
            assemblyId =  Main.assemblyMap.get(parseBomLine.getAssemblyName());
            Long partId = Main.partMap.get(parseBomLine.getPartNumber());
            bomItem.setParentAssemblyId(assemblyId);
            bomItem.setComponentId(partId);
        }
        bomItem.setProjectId(parseBomLine.getProjectId());
        bomItem.setItemNumber((long)1);

        bomItem.setLocation(parseBomLine.getLocation());
        bomItem.setQuantity(parseBomLine.getQuantity());
        bomItem.setBuy(false);  //buy is optional  
        
        return bomItem;
    }

    public static void updateAssemblyVolume(Long assemblyId, Long projectId) throws InterruptedException, UnirestException {
        
        for(int volumeId=1; volumeId<= Main.NUMBER_OF_VOLUME_BREAKS; volumeId++){

            // Assign arbitrary volume count
            Double volume =  Math.pow(10.0, volumeId);

            AssemblyVolume assemblyVolume = new AssemblyVolume();
            assemblyVolume.setVolume(volume);

            HttpResponse<AssemblyVolume> rsp = HttpHelper.patch("/projects/"+projectId + "/assemblies/"+assemblyId+"/volumes/"+volumeId)
                    .body(assemblyVolume)
                    .asObject(AssemblyVolume.class);

            assemblyVolume = rsp.getBody();
            HttpHelper.pause();
        }
    }

    public static List<BomItem> createBomItems(List<BomItem> bomItems) throws UnirestException {
        ObjectMapper mapper = new ObjectMapper();
        List<BomItem> bomItemList = new ArrayList<BomItem>();
        List<List<BomItem>> bomItemBatches = HttpHelper.splitIntoBatches(bomItems, 100);

        for(List<BomItem> batch : bomItemBatches) {

            BatchRequest[] batchRequest = new BatchRequest[batch.size()];
            for(int i=0; i< batch.size();i++){
                batchRequest[i] = new BatchRequest();
                batchRequest[i].setMethod("POST");
                batchRequest[i].setResource("/projects/"+ Main.projectId +"/bom_items/");
                batchRequest[i].setBody(batch.get(i)); 
            }

            HttpResponse<BatchResponse[]> rsp = HttpHelper.post("/batch")
                    .body(batchRequest)
                    .asObject(BatchResponse[].class);

            BatchResponse[] batchResponse = rsp.getBody();

            for(int i=0; i< batchResponse.length;i++){
                //create bom items
                BomItem bomItem = mapper.convertValue(batchResponse[i].getBody(), BomItem.class);
                bomItemList.add(bomItem);
            }
        }
        return bomItemList;
    }
}
