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
import com.polydyne.api.bom.example.model.ManufacturerPart;
import com.polydyne.api.bom.example.model.ParsedBomLine;

public class ManufacturerPartService {
    public static ObjectMapper mapper = new ObjectMapper();

    public static ManufacturerPart parseManufacturerPart(ParsedBomLine parsedBomLine) {
        ManufacturerPart manufacturerPart = new ManufacturerPart();

        Long mpnStatusId =null;
        if(!parsedBomLine.getMpnStatusCode().isEmpty()){
            mpnStatusId = Cache.mpnStatusCache.get(parsedBomLine.getMpnStatusCode());
        }

        Long manufacturerId =0L;//unknown mfg
        if(!parsedBomLine.getManufacturerName().isEmpty()){

            manufacturerId = Cache.manufacturerCache.get(parsedBomLine.getManufacturerId());
        }
        if(manufacturerId ==null){
            manufacturerId = 0L; //unknown
        }

        Long partId = Main.partMap.get(parsedBomLine.getPartNumber());

        manufacturerPart.setPartId(partId);
        manufacturerPart.setProjectId(parsedBomLine.getProjectId());
        manufacturerPart.setManufacturerId(manufacturerId);
        manufacturerPart.setManufacturerPartNumber(parsedBomLine.getManufacturerPartNumber());
        manufacturerPart.setAml(parsedBomLine.isAmlFlag());//optional
        manufacturerPart.setInternalManufacturerPartNumber(parsedBomLine.getInternalManufacturerPartNumber());
        manufacturerPart.setMatrixCategory(parsedBomLine.getMatrixCategory());
        manufacturerPart.setMpnStatus(mpnStatusId);
        manufacturerPart.setObsolete(parsedBomLine.getObsolete());
        manufacturerPart.setOemPercentage(parsedBomLine.getOemPercentage());
        manufacturerPart.setOemPrice(parsedBomLine.getOemPrice());
        manufacturerPart.setLongComment(parsedBomLine.getLongComment()); 
        manufacturerPart.setShortComment(parsedBomLine.getShortComment());

        return manufacturerPart;
    }

    public static List<ManufacturerPart> create(List<ManufacturerPart> manufacturerParts) throws UnirestException  {

        List<ManufacturerPart> manufacturerPartList = new ArrayList<ManufacturerPart>();
        List<List<ManufacturerPart>> manufacturerPartBatches = HttpHelper.splitIntoBatches(manufacturerParts, 100);

        for(List<ManufacturerPart> batch : manufacturerPartBatches) {
            BatchRequest[] batchRequest = new BatchRequest[batch.size()];
            for(int i=0; i< batch.size();i++){
                batchRequest[i] = new BatchRequest();
                batchRequest[i].setMethod("POST");
                batchRequest[i].setResource("/projects/"+ Main.projectId +"/manufacturer_parts/");
                batchRequest[i].setBody(batch.get(i));
            }

            HttpResponse<BatchResponse[]> rsp = HttpHelper.post("/batch")
                    .body(batchRequest)
                    .asObject(BatchResponse[].class);

            //Extract response
            BatchResponse[] batchResponse = rsp.getBody();

            for(int i=0; i< batchResponse.length;i++){
                ManufacturerPart manufacturerPart = mapper.convertValue(batchResponse[i].getBody(), ManufacturerPart.class);
                manufacturerPartList.add(manufacturerPart);
            }
        }

        return manufacturerPartList;
    }
}
