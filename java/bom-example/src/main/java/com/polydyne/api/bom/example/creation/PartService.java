package com.polydyne.api.bom.example.creation;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.bom.example.Cache;
import com.polydyne.api.bom.example.HttpHelper;
import com.polydyne.api.bom.example.Main;
import com.polydyne.api.bom.example.model.BatchRequest;
import com.polydyne.api.bom.example.model.BatchResponse;
import com.polydyne.api.bom.example.model.ParsedBomLine;
import com.polydyne.api.bom.example.model.Part;

public class PartService {
    public static ObjectMapper mapper = new ObjectMapper();

    public static  Part parsePart(ParsedBomLine parsedBomLine) {
        // If the part has already been created, it is not created again. Each part can have multiple manufacturers
        Long commodityId =null;
        if(parsedBomLine.getCommodity()!=null && Cache.commodityCache.containsKey(parsedBomLine.getCommodity())){
            commodityId = Cache.commodityCache.get(parsedBomLine.getCommodity());
        }

        Long unitId = Cache.unitCache.get(parsedBomLine.getUnits());

        Long packageTypeId = null;
        if(parsedBomLine.getPackageType()!=null && Cache.packageTypeCache.containsKey(parsedBomLine.getPackageType())){
            packageTypeId = Cache.packageTypeCache.get(parsedBomLine.getPackageType());
        }
        //for custom fields
        Long combo1 = null;
        if(parsedBomLine.getCustomField1()==null){
            combo1 = null;       
        }
        if( !Cache.comboValuesCache1.isEmpty() &&  Cache.comboValuesCache1.containsKey(parsedBomLine.getCustomField1())){
            combo1 = Cache.comboValuesCache1.get(parsedBomLine.getCustomField1());
        }

        Long combo2 = null;
        if(parsedBomLine.getCustomField2()==null){
            combo2 = null;       
        }
        else if(!Cache.comboValuesCache2.isEmpty()  && Cache.comboValuesCache2.containsKey(parsedBomLine.getCustomField2())){

            combo2 = Cache.comboValuesCache2.get(parsedBomLine.getCustomField2());
        }

        Part part = new Part();
        part.setProjectId(parsedBomLine.getProjectId());
        part.setPartNumber(parsedBomLine.getPartNumber());
        part.setRevision(parsedBomLine.getPartRevision()); //optional
        part.setReferencePrice(parsedBomLine.getReferencePrice());
        part.setReferencePartNumber(parsedBomLine.getReferencePartNumber());
        part.setCommodityId(commodityId); 
        part.setUnitId(unitId);
        part.setPackageTypeId(packageTypeId);
        part.setStandardCost(parsedBomLine.getStandardCost());
        part.setTargetPrice(parsedBomLine.getTargetPrice());
        part.setDescription(parsedBomLine.getDescription());
        part.setCombo1(combo1);
        part.setCombo2(combo2);
        part.setAssemblyName(parsedBomLine.getAssemblyName());

        return part;
    }

    public static List<Part> createPart(List<Part> parts) throws UnirestException  {

        List<Part> partList = new ArrayList<Part>();
        List<List<Part>> partBatches = HttpHelper.splitIntoBatches(parts, 100);

        for(List<Part> batch : partBatches) {
            BatchRequest[] batchRequest = new BatchRequest[batch.size()];
            for(int i=0; i< batch.size();i++){
                batchRequest[i] = new BatchRequest();
                batchRequest[i].setMethod("POST");
                batchRequest[i].setResource("/projects/"+ Main.projectId +"/parts/");
                batchRequest[i].setBody(batch.get(i));
            }

            HttpResponse<BatchResponse[]> rsp = HttpHelper.post("/batch")
                    .body(batchRequest)
                   .asObject(BatchResponse[].class);

            // Extract response
            BatchResponse[] batchResponse = rsp.getBody();

            for(int i=0; i< batchResponse.length;i++){
                Part part = mapper.convertValue(batchResponse[i].getBody(), Part.class);
                partList.add(part);
            }
        }
        return partList;
    }
}
