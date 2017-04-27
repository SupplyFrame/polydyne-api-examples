package com.polydyne.api.bom.example.creation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.bom.example.HttpHelper;
import com.polydyne.api.bom.example.Main;
import com.polydyne.api.bom.example.model.Assembly;
import com.polydyne.api.bom.example.model.AssemblyVolume;
import com.polydyne.api.bom.example.model.BatchRequest;
import com.polydyne.api.bom.example.model.BatchResponse;
import com.polydyne.api.bom.example.model.ParsedBomLine;

public class AssemblyService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Assembly parse(ParsedBomLine parsedBomLine) {
        Assembly assembly = new Assembly();
        assembly.setAssemblyPartNumber(parsedBomLine.getPartNumber()); //in csv, assembly name for subassembly flag = 1 is in Part number
        assembly.setProjectId(parsedBomLine.getProjectId());
        assembly.setDescription(parsedBomLine.getDescription());//optional
        assembly.setRevision(parsedBomLine.getAssemblyRevision()); //optional
        assembly.setRefStandard(parsedBomLine.getStandardCost()); 

        return assembly;

    }

    public static List<Assembly> create(List<Assembly> assemblies) throws UnirestException  {
        
        List<Assembly> assemblyList = new ArrayList<Assembly>();
        List<List<Assembly>> assemblyBatches = HttpHelper.splitIntoBatches(assemblies, 100);

        for(List<Assembly> batch : assemblyBatches) {
            BatchRequest[] batchRequest = new BatchRequest[batch.size()];
            for(int i=0; i< batch.size();i++){
                batchRequest[i] = new BatchRequest();
                batchRequest[i].setMethod("POST");
                batchRequest[i].setResource("/projects/"+ Main.projectId +"/assemblies/");
                batchRequest[i].setBody(batch.get(i)); //added assembly body
            }

            HttpResponse<BatchResponse[]> rsp = HttpHelper.post("/batch")
                    .body(batchRequest)
                    .asObject(BatchResponse[].class);

            //extract response
            BatchResponse[] batchResponse = rsp.getBody();

            //now from here we get list of assemblies

            for(int i=0; i< batchResponse.length;i++){

                Assembly assembly = mapper.convertValue(batchResponse[i].getBody(), Assembly.class);
                assemblyList.add(assembly);
            }
        }
        return assemblyList;
    }

    public static AssemblyVolume parseAssemblyVolume(ParsedBomLine line) {
        return null;

    }
}
