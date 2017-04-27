package com.polydyne.api.bom.example;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.bom.example.model.ParsedBomLine;

public class CsvBomParser{
    
    /*
     * This class parses the CSV file and creates assemblies, parts, manufacturer parts and BOM items
     */
    public static List<ParsedBomLine> parseFile(String csvFilePath, Long projectId) throws IOException, UnirestException, InterruptedException{
        Iterable<CSVRecord> csvRecords  = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new FileReader(csvFilePath));
        Map<String, Integer> headerMap = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new FileReader(csvFilePath)).getHeaderMap();
        ArrayList<String> headerList = new ArrayList<String>();
        for(Map.Entry<String, Integer> map : headerMap.entrySet()){
            headerList.add(map.getKey());

        }
        List<ParsedBomLine> result = new ArrayList<ParsedBomLine>();
        //find custom fields
        List<String> customFieldsList = findCustomFields(headerList);
        
        String[] customFields = customFieldsList.toArray(new String[2]);
        
        Cache.customField1 = customFields[0];
        Cache.customField2 =  customFields[1];
        
        for(CSVRecord record: csvRecords){
            ParsedBomLine parsedBomLine= new ParsedBomLine();
            parsedBomLine.setProjectId(projectId);
            parsedBomLine.setAssemblyName(record.get("Assembly Name"));
            parsedBomLine.setAssemblyRevision(record.get("Assembly Revision"));
            parsedBomLine.setPartNumber(record.get("Part Number"));
            parsedBomLine.setPartRevision(record.get("Part Revision"));
            parsedBomLine.setSubassemblyFlag("1".equals(record.get("Subassembly Flag")));
            parsedBomLine.setQuantity(Integer.parseInt(record.get("Quantity")));
            parsedBomLine.setLocation(record.get("Location"));
            parsedBomLine.setDescription(record.get("Description"));
            parsedBomLine.setPartStatus(record.get("Part Status"));
            parsedBomLine.setCommodity(record.get("Commodity"));
            parsedBomLine.setPackageType(record.get("Package Type"));
            parsedBomLine.setUnits(record.get("Units"));
            parsedBomLine.setReferencePartNumber(record.get("Reference PN"));
            parsedBomLine.setStandardCost(parseDoubleIfExists(record, "Standard Cost"));
            parsedBomLine.setTargetPrice(parseDoubleIfExists(record, "Target Price"));
            parsedBomLine.setReferencePrice(parseDoubleIfExists(record, "Reference Price"));
            parsedBomLine.setManufacturerId(record.get("Manufacturer ID"));
            parsedBomLine.setManufacturerName(record.get("Mfg Name"));
            parsedBomLine.setAmlFlag("1".equals(record.get("AML Flag")));
            parsedBomLine.setManufacturerPartNumber(record.get("Manufacturer Part Number"));
            parsedBomLine.setInternalManufacturerPartNumber(record.get("Internal Manufacturer Part Number"));
            parsedBomLine.setMatrixCategory(record.get("Matrix Category"));
            parsedBomLine.setOemPrice(parseBigDecimalIfExists(record, "OEM Price"));
            parsedBomLine.setOemPercentage(parseBigDecimalIfExists(record, "OEM Percentage"));
            parsedBomLine.setShortComment(record.get("Short Comment"));
            parsedBomLine.setLongComment(record.get("Long Comment"));
            parsedBomLine.setMpnStatusCode(record.get("MPN Status Code"));
            parsedBomLine.setObsolete(Boolean.parseBoolean(record.get("Obsolete")));

            if(Cache.customField1!=null && (!record.get(Cache.customField1).isEmpty()) ){
                parsedBomLine.setCustomField1(record.get(Cache.customField1)); 
            }
            if(Cache.customField2 !=null && (!record.get(Cache.customField2).isEmpty())){
                parsedBomLine.setCustomField2(record.get(Cache.customField2));
            }

            result.add(parsedBomLine);
        }
        return result;
    }

    private static ArrayList<String> findCustomFields(ArrayList<String> headerList) {
        //find custom fields
        Map<String, String> map =  Cache.customFieldDisplayNameCache;
        ArrayList<String> displayNamesCustomFields = new ArrayList<>() ;
        
        for(Map.Entry<String, String> entry: map.entrySet()){
            displayNamesCustomFields.add(entry.getKey());
        }

        headerList.retainAll(displayNamesCustomFields); //intersection
        
        return headerList;
    }

    private static Double parseDoubleIfExists(CSVRecord record, String key) {
        if(!record.get(key).isEmpty()){
            return Double.parseDouble(record.get(key));
        } else {
            return null;
        }
    }

    private static BigDecimal parseBigDecimalIfExists(CSVRecord record, String key) {
        if(!record.get(key).isEmpty()){
            return new BigDecimal(record.get(key));
        } else {
            return null;
        }
    }
}
