package com.polydyne.api.bom.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.bom.example.creation.AssemblyService;
import com.polydyne.api.bom.example.creation.BomItemService;
import com.polydyne.api.bom.example.creation.ManufacturerPartService;
import com.polydyne.api.bom.example.creation.PartService;
import com.polydyne.api.bom.example.creation.ProjectService;
import com.polydyne.api.bom.example.model.Assembly;
import com.polydyne.api.bom.example.model.BomItem;
import com.polydyne.api.bom.example.model.ManufacturerPart;
import com.polydyne.api.bom.example.model.ParsedBomLine;
import com.polydyne.api.bom.example.model.Part;
import com.polydyne.api.bom.example.model.PartNumberAndRevision;

public class Main {

    public static String API_BASE_URL = "https://api.polydyne.com/v1";
    public static String USERNAME;
    public static String PASSWORD;
    public static String PROJECT_NAME;
    public static String INPUT_FILE;

    public static String PROJECT_DESCRIPTION = "Test project created by API";
    public static int NUMBER_OF_VOLUME_BREAKS = 3;                 

    //global maps and lists
    public static Map<String, Long> partMap = new HashMap<String, Long>();
    public static Map<String, Long> assemblyMap = new HashMap<String, Long>(); 
    public static ArrayList<String> manufacturerPartList = new ArrayList<String>();
    public static ArrayList<String> partList = new ArrayList<String>();
    public static Long projectId = null;

    public static void main(String[] args) throws InterruptedException, IOException, UnirestException {
        USERNAME = System.getProperty("username");
        PASSWORD = System.getProperty("password");
        INPUT_FILE = System.getProperty("input_file");
        PROJECT_NAME = System.getProperty("project_name");

        // Initialize unirest
        initializeUnirest();

        // Populate caches
        System.out.println("Populating caches");
        Cache.initializeCache();

        // Select a region, customer and currency to use for this project
        long regionId = selectRegion();
        long currencyId = selectCurrency();
        long customerId = selectCustomer();

        projectId = ProjectService.createProject(PROJECT_NAME, PROJECT_DESCRIPTION,NUMBER_OF_VOLUME_BREAKS, regionId, currencyId, customerId);
        List<ParsedBomLine> parsedBomLines = CsvBomParser.parseFile(INPUT_FILE, projectId);

        // Find the custom field values after parsing the Bom
        Cache.cacheComboValues1(); 
        Cache.cacheComboValues2();

        // Stores the parts mapped to assemblies to prevent attempting to create duplicates
        Map<String, Set<String>> assemblyPartMap = new HashMap<String, Set<String>>();
        Set<PartNumberAndRevision> partRevisionSet = new HashSet<>();

        // Separate the parsed lines into assemblies and parts
        List<ParsedBomLine> assemblyLines = new ArrayList<ParsedBomLine>();
        List<ParsedBomLine> partLines = new ArrayList<ParsedBomLine>();

        // Lists to create unique parts, manufacturer parts and unique bom items and to update top level assemblies
        List<ParsedBomLine> uniquePartLines = new ArrayList<ParsedBomLine>();
        List<ParsedBomLine> uniqueManufacturerPartLines= new ArrayList<ParsedBomLine>();
        List<ParsedBomLine> uniqueBomItemLines= new ArrayList<ParsedBomLine>();
        List<ParsedBomLine> assemblyVolumeList = new ArrayList<ParsedBomLine>(); //top level assemblies to be updated

        for (ParsedBomLine line : parsedBomLines) {
            if (line.isSubassemblyFlag()) {
                assemblyLines.add(line);
                //unique BOM
                uniqueBomItemLines.add(line);
                assemblyPartMap.put(line.getPartNumber(), new HashSet<String>());//assemblyName to new set

                //Get info of top level assemblies to update their volumes after corresponding bom item creation
                if(line.getAssemblyName().isEmpty()){
                    assemblyVolumeList.add(line);
                }

            } else {
                partLines.add(line);
                PartNumberAndRevision pnr = new PartNumberAndRevision(line.getPartNumber(),line.getPartRevision());

                if(!partRevisionSet.contains(pnr)){
                    partRevisionSet.add(pnr);
                    uniquePartLines.add(line); //Unique parts using part number and revision
                    partList.add(line.getPartNumber());
                }

                //manufacturerParts
                if(!manufacturerPartList.contains(line.getManufacturerPartNumber())){
                    uniqueManufacturerPartLines.add(line);
                    manufacturerPartList.add(line.getManufacturerPartNumber());
                }

                //if set contains the partNumber and map contains it, then no need to create
                Set<String> partNum = new HashSet<>();
                if(assemblyPartMap.containsKey(line.getAssemblyName())){
                    partNum = assemblyPartMap.get(line.getAssemblyName());
                    if(partNum == null || !partNum.contains(line.getPartNumber())){
                        //add it
                        uniqueBomItemLines.add(line);
                        partNum.add(line.getPartNumber());
                        assemblyPartMap.put(line.getAssemblyName(), partNum);
                    }
                }
                else{
                    uniqueBomItemLines.add(line);
                    partNum.add(line.getPartNumber());
                    assemblyPartMap.put(line.getAssemblyName(), partNum);
                }
            }
        }

        // Assemble batch of assemblies for creation
        List<Assembly> assemblies = assemblyLines.stream()
                .map(line -> AssemblyService.parse(line))
                .collect(Collectors.toList());

        // create assemblies
        List<Assembly> createdAssemblies = AssemblyService.create(assemblies);
        System.out.println(createdAssemblies.size() + " assemblies created");

        // Store reference values
        for (Assembly created : createdAssemblies) {
            assemblyMap.put(created.getAssemblyPartNumber(), created.getAssemblyId());
        }

        // Assemble batch of parts for creation
        List<Part> parts = uniquePartLines.stream()
                .map(line -> PartService.parsePart(line))
                .collect(Collectors.toList());

        // Create parts
        List<Part> createdParts= PartService.createPart(parts);
        System.out.println(createdParts.size() + " parts created");

        // Store reference values
        for (Part created : createdParts) {
            partMap.put(created.getPartNumber(), created.getPartId());
        }

        // Assemble batch of manufacturerParts for creation
        List<ManufacturerPart> manufacturerParts = uniqueManufacturerPartLines.stream()
                .map(line -> ManufacturerPartService.parseManufacturerPart(line))
                .collect(Collectors.toList());

        // Create manufacturer parts
        List<ManufacturerPart> createdManufacturerParts = ManufacturerPartService.create(manufacturerParts);
        System.out.println(createdManufacturerParts.size() + " manufacturer parts created");

        //Assemble batch of bom items for creation
        List<BomItem> bomItems = uniqueBomItemLines.stream()
                .map(line -> BomItemService.parseBomItem(line))
                .collect(Collectors.toList());

        // Create bom items
        List<BomItem> createdBomItems= BomItemService.createBomItems(bomItems);
        System.out.println(createdBomItems.size() + " bom items created");

        // Update assembly volumes of top level assemblies
        for(ParsedBomLine line : assemblyVolumeList){
            Long assemblyId = assemblyMap.get(line.getPartNumber()); //as  assembly names are in part number
            BomItemService.updateAssemblyVolume(assemblyId, projectId);
        }

        System.out.println("Updated "+ (assemblyVolumeList.size()*NUMBER_OF_VOLUME_BREAKS) + " assembly volumes for "+ assemblyVolumeList.size()+" top level assemblies");
    }

    private static long selectRegion() throws UnirestException {
        // TODO Get first page of regions and return the first one
        String url = API_BASE_URL + "/regions";
        long regionId = -1;
        if(url!=null){
            HttpResponse<JsonNode> rsp = HttpHelper.get(url).asJson();

            regionId= rsp.getBody().getArray().getJSONObject(0).getLong("regionId");

        }
        return regionId;

    }

    private static long selectCurrency() throws UnirestException {

        String url = API_BASE_URL + "/currencies";
        long currencyId = -1;
        if(url!=null){
            HttpResponse<JsonNode> rsp = HttpHelper.get(url).asJson();

            currencyId= rsp.getBody().getArray().getJSONObject(0).getLong("currencyId");

        }
        return currencyId;
    }

    private static long selectCustomer() throws UnirestException {
        String url = API_BASE_URL + "/customers";
        long customerId = -1;
        if(url!=null){
            HttpResponse<JsonNode> rsp = HttpHelper.get(url).asJson();

            customerId= rsp.getBody().getArray().getJSONObject(0).getLong("customerId");

        }
        return customerId;
    }

    private static void initializeUnirest() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
            = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
