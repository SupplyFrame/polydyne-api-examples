package com.supplier.api.autoquoter.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.supplier.api.autoquoter.example.model.ManufacturerInfoModel;

public class TsvParser {


    public static void createMaps(String inputFilePath) throws FileNotFoundException, IOException {

        Iterable<CSVRecord> records = CSVFormat.TDF.withFirstRecordAsHeader().parse(new FileReader(inputFilePath));

        for(CSVRecord record: records){

            // For each record create maps for manufacturer information and price list, leadtime list and moq list from the given input file. 
            //These map will be used by the auto quoter
            ManufacturerInfoModel manufacturerInfo = new ManufacturerInfoModel();
            // Populate the manufacturer info model using the TSV file. The column names should be changed to match the column names of the input TSV
            manufacturerInfo.setManufacturerName(record.get("MFG_NAME").trim());
            manufacturerInfo.setManufacturerPartNumber(record.get("MFG_PART_NUMBER").trim());
            Double price = parseDoubleIfExists(record, "PRICE_T");
            Double leadtime = parseDoubleIfExists(record, "LEADTIME");
            Double moq = parseDoubleIfExists(record, "MOQ_QTY");
            //get existing price list for this manufacturer
            List<Double> priceList = Cache.priceMap.getOrDefault(manufacturerInfo, new ArrayList<Double>());
            if(price!=null){
                priceList.add(price);
            }
            List<Double> leadtimeList = Cache.leadtimeMap.getOrDefault(manufacturerInfo, new ArrayList<Double>());
            if(leadtime!=null){
                leadtimeList.add(leadtime); 
            }

            List<Double> moqList = Cache.moqMap.getOrDefault(manufacturerInfo, new ArrayList<Double>());
            if(moq!=null){
                moqList.add(moq); 
            }

            Cache.priceMap.put(manufacturerInfo, priceList);
            Cache.leadtimeMap.put(manufacturerInfo, leadtimeList);
            Cache.moqMap.put(manufacturerInfo, moqList);

        }
        

        // For each stored map, get the average value so that we don't have to recalculate while auto quoting
        for(ManufacturerInfoModel mi : Cache.priceMap.keySet()){
            if(Cache.priceMap.get(mi).size()!=0){
                Double averagePrice =  Cache.priceMap.get(mi)
                        .stream()
                        .mapToDouble(a -> a)
                        .average().getAsDouble();

                Cache.avgPriceMap.put(mi, averagePrice);
            }

        }
        
        for(ManufacturerInfoModel mi : Cache.leadtimeMap.keySet()){

            if(Cache.leadtimeMap.get(mi).size()!=0){
                Double averageLeadtime =  Cache.leadtimeMap.get(mi)
                        .stream()
                        .mapToDouble(a -> a)
                        .average().getAsDouble();

                Cache.avgLeadtimeMap.put(mi, averageLeadtime);
            }
        }
        
        for(ManufacturerInfoModel mi : Cache.moqMap.keySet()){

            if(Cache.moqMap.get(mi).size()!=0){
                Double averageMoq =  Cache.moqMap.get(mi)
                        .stream()
                        .mapToDouble(a -> a)
                        .average().getAsDouble();

                Cache.avgMoqMap.put(mi, averageMoq);
            }

        }        
    }

    private static Double parseDoubleIfExists(CSVRecord record, String key) {
        if(!record.get(key).isEmpty()){
            return Double.parseDouble(record.get(key));
        } else {
            return null;
        }
    }

}
