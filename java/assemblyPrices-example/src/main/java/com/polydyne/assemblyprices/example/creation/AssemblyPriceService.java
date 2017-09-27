package com.polydyne.assemblyprices.example.creation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.assemblyprices.example.Cache;
import com.polydyne.assemblyprices.example.HttpHelper;
import com.polydyne.assemblyprices.example.Main;
import com.polydyne.assemblyprices.example.model.Assembly;
import com.polydyne.assemblyprices.example.model.AssemblyVolume;
import com.polydyne.assemblyprices.example.model.BomItem;
import com.polydyne.assemblyprices.example.model.CostRollupAttribute;
import com.polydyne.assemblyprices.example.model.CustomManufacturer;
import com.polydyne.assemblyprices.example.model.OutsourceAssemblyPrice;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AssemblyPriceService {
    
    public static Map<String, Map> getAssemblyPrices(String projectId) throws UnirestException {

        String url = Main.API_BASE_URL + "/projects/" + projectId + "/outsource_assembly_prices/";
                
        Map<String, Map> lvl1Map = new HashMap<>();  // key = lvl1Key  
        
        while (url != null) {
            HttpResponse<OutsourceAssemblyPrice[]> rsp = HttpHelper.get(url).asObject(OutsourceAssemblyPrice[].class);
            
            for(OutsourceAssemblyPrice assmPrice : rsp.getBody()) {
                String lvl1Key = generateAssemblyPriceLvl1Key(assmPrice);

                // Try to find lvl2Map or create a new Map
                Map<String, Map> lvl2Map = lvl1Map.getOrDefault(lvl1Key, new HashMap<>());
                if (lvl2Map.isEmpty()) {
                    lvl1Map.put(lvl1Key, lvl2Map);
                }

                String lvl2Key = generateAssemblyPriceLvl2Key(assmPrice);
                Map<Integer, OutsourceAssemblyPrice> lvl3Map = lvl2Map.getOrDefault(lvl2Key, new HashMap<>());
                if (lvl3Map.isEmpty()) {
                    lvl2Map.put(lvl2Key, lvl3Map);
                }

                // Lvl 3 - key is Volume ID
                lvl3Map.put(assmPrice.getVolumeId(), assmPrice);                
            }
            
            // Go to next page
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            HttpHelper.pause();            
        }

        return lvl1Map;
    }
    
    /**
     * The key will be a combination of projectId, assemblyId, customerManufacturerId
     * @param assmPrice
     * @return level 1 key for the hash
     */
    private static String generateAssemblyPriceLvl1Key(OutsourceAssemblyPrice assmPrice) {
        return assmPrice.getProjectId() + "-" +  assmPrice.getAssemblyId() + "-" + assmPrice.getCustomManufacturerId();
    }
    
    
    private static String generateAssemblyPriceLvl2Key(OutsourceAssemblyPrice assmPrice) {
        String lvl2Key = generateAssemblyPriceLvl1Key(assmPrice) 
                + "-" + assmPrice.getPriceType();
        if (assmPrice.getCostRollupAttributeType() != null) {
                lvl2Key += "-" + assmPrice.getCostRollupAttributeType()
                        + "-" + assmPrice.getCostRollupAttributeId();
        }
        return lvl2Key;
    }

    private enum AssemblyPriceTypes {
        RAW_MATERIAL("Raw Material", ""),
        ATTRIBUTES_MATERIAL("Attributes", "MATERIAL"),
        RAW_LABOR("Raw Labor", ""),
        ATTRIBUTES_LABOR("Attributes", "LABOR"),
        ATTRIBUTES_SGA("Attributes", "SG&A"),
        ATTRIBUTES_OTHER("Attributes", "OTHER"),
        PROFIT_MATERIAL("Material Profit", ""),
        PROFIT_LABOR("Labor Profit", ""),
        PROFIT_SGA("Overhead Profit", ""),
        PROFIT_OTHER("Other Profit", ""),
        ATTRIBUTES_NRE("Attributes", "NRE"),
        PROFIT_NRE("NRE Profit", "")
        ;
        
        private final String priceType;
        private final String attributeType;
        
        AssemblyPriceTypes(String priceType, String attributeType) {
            this.priceType = priceType;
            this.attributeType = attributeType;
        }
        
        String getPriceType() {
            return priceType;
        }
        
        String getAttributeType() {
            return attributeType;
        }
    }
    
    
    public static Double getAssemblyVolume(Long projectId, Long assemblyId, int volumeId) throws UnirestException {
        String url = Main.API_BASE_URL + "/projects/" + projectId.toString() + "/assemblies/" + assemblyId.toString() + "/volumes/" + volumeId;
        HttpResponse<AssemblyVolume> rsp = HttpHelper.get(url).asObject(AssemblyVolume.class);
        
        AssemblyVolume av = (AssemblyVolume)rsp.getBody();
        Double volume = av.getVolume();
        
        return volume;
    }
        
    /**
     * 
     * @param topAssm
     * @param level1Map
     * @param volumeId
     * @throws com.mashape.unirest.http.exceptions.UnirestException
     */
    public static void print(HashMap<Long, BomItem> topAssm, Map<String, Map> level1Map, int volumeId) throws UnirestException {
        
        for (String lvl1Key : level1Map.keySet()) {
        
            String field[] = lvl1Key.split("-", 3);
            String projectId = field[0];
            
            Assembly assembly = AssemblyService.getAssembly(projectId, field[1]);
            CustomManufacturer cm = CustomManufacturerService.getCustomManufacturer(field[2]);
            Double volume = AssemblyPriceService.getAssemblyVolume(assembly.getProjectId(), assembly.getAssemblyId(), volumeId);
            
            // Column sequence: Category, CM Target, volume price, notes
            System.out.println();
            System.out.println("\"Assembly: " + assembly.getAssemblyPartNumber()
                    + "\", \"Custom Manufacturer:" + cm.getName()
                    + "\", \"Volume ID: " + volumeId 
                    + "\", \"volume:" + volume.toString() + "\"");
            System.out.println("----------------------------------------------");
            System.out.println("\"Category\", \"CM Target\", \"Vol#1 Price\", \"Notes\"");

            // Print Raw Material
            HashMap<String, Map> level2Map = (HashMap<String, Map>)level1Map.get(lvl1Key);
            
            BigDecimal rawMaterialCost = printRawOrProfit(AssemblyPriceTypes.RAW_MATERIAL.getPriceType(), lvl1Key, level2Map, volumeId);
            
            // Print all Material Attributes rows
            BigDecimal totalMaterial = printAttributes(AssemblyPriceTypes.ATTRIBUTES_MATERIAL.getAttributeType(), lvl1Key, level2Map, volumeId);
            totalMaterial = AssemblyPriceService.addPrices(totalMaterial, rawMaterialCost);
            
            // Print Total Material
            System.out.println("\"Total Material\""
                    + ","
                    + "," + (totalMaterial == null ? "" : totalMaterial.toString())
                    + ",\"\"" 
            );
            
            // Print Raw Labor
            BigDecimal rawLaborPrice = printRawOrProfit(AssemblyPriceTypes.RAW_LABOR.getPriceType(), lvl1Key, level2Map, volumeId);
            
            // Print all Labor Attributes rows
            BigDecimal totalLabor = printAttributes(AssemblyPriceTypes.ATTRIBUTES_LABOR.getAttributeType(), lvl1Key, level2Map, volumeId);
            totalLabor = AssemblyPriceService.addPrices(totalLabor, rawLaborPrice);
            
            // Print Total Labor
            System.out.println("\"Total Labor\""
                    + ","
                    + "," + (totalLabor == null ? "" : totalLabor.toString())
                    + ",\"\"" 
            );
            
            // Print SG&A (or Overhead)
            BigDecimal totalSGA = printAttributes(AssemblyPriceTypes.ATTRIBUTES_LABOR.getAttributeType(), lvl1Key, level2Map, volumeId);
            
            // Print Total SG&A (or Overhead)
            System.out.println("\"Total SG&A\""
                    + ","
                    + "," + (totalSGA == null ? "" : totalSGA.toString())
                    + ",\"\"" 
            );
            
            // Print Other 
            BigDecimal totalOther = printAttributes(AssemblyPriceTypes.ATTRIBUTES_LABOR.getAttributeType(), lvl1Key, level2Map, volumeId);
            
            // Print Total Other
            System.out.println("\"Total Other\""
                    + ","
                    + "," + (totalOther == null ? "" : totalOther.toString())
                    + ",\"\"" 
            );
            
            // Calculate Assembly Cost 
            BigDecimal assemblyCost = null;
            assemblyCost = AssemblyPriceService.addPrices(assemblyCost, totalMaterial);
            assemblyCost = AssemblyPriceService.addPrices(assemblyCost, totalLabor);
            assemblyCost = AssemblyPriceService.addPrices(assemblyCost, totalSGA);
            assemblyCost = AssemblyPriceService.addPrices(assemblyCost, totalOther);
            
            // Print Assembly Cost
            System.out.println("\"Assembly Cost\""
                    + ","
                    + "," + (assemblyCost == null ? "" : assemblyCost.toString())
                    + ",\"\"" 
            );            
            
            
            // Material Profit
            BigDecimal materialProfit = AssemblyPriceService.printRawOrProfit(AssemblyPriceTypes.PROFIT_MATERIAL.getPriceType(), lvl1Key, level2Map, volumeId);
            
            // Labor Profit
            BigDecimal laborProfit = AssemblyPriceService.printRawOrProfit(AssemblyPriceTypes.PROFIT_LABOR.getPriceType(), lvl1Key, level2Map, volumeId);
                        
            // SG&A (or Overhead) Profit
            BigDecimal sgalProfit = AssemblyPriceService.printRawOrProfit(AssemblyPriceTypes.PROFIT_SGA.getPriceType(), lvl1Key, level2Map, volumeId);
            
            // Other Profit
            BigDecimal otherProfit = AssemblyPriceService.printRawOrProfit(AssemblyPriceTypes.PROFIT_OTHER.getPriceType(), lvl1Key, level2Map, volumeId);
            
            
            // Calculate Assembly Price (Assembly Price = Assembly Cost + all the profits)
            BigDecimal assemblyPrice = null;
            assemblyPrice = AssemblyPriceService.addPrices(assemblyPrice, assemblyCost);
            assemblyPrice = AssemblyPriceService.addPrices(assemblyPrice, materialProfit);
            assemblyPrice = AssemblyPriceService.addPrices(assemblyPrice, laborProfit);
            assemblyPrice = AssemblyPriceService.addPrices(assemblyPrice, sgalProfit);
            assemblyPrice = AssemblyPriceService.addPrices(assemblyPrice, otherProfit);
            
            // Print Assembly Price 
            System.out.println("\"Assembly Price\""
                    + ","
                    + "," + (assemblyPrice == null ? "" : assemblyPrice.toString())
                    + ",\"\"" 
            );
            
            // Calcuate Volume Price (Volume price = assembly price * volume)
            BigDecimal volumePrice = AssemblyPriceService.multiply(assemblyPrice, BigDecimal.valueOf(volume));
            
            // Print volume 
            System.out.println("\"Volume Price\""
                    + ","
                    + "," + (volumePrice == null ? "" : volumePrice.toString())
                    + ",\"\"" 
            );
            
            // Print all NRE attributes
            BigDecimal totalNRE = printAttributes(AssemblyPriceTypes.ATTRIBUTES_NRE.getAttributeType(), lvl1Key, level2Map, volumeId);
            
            // Print Total NRE
            System.out.println("\"Total NRE\""
                    + ","
                    + "," + (totalNRE == null ? "" : totalNRE.toString())
                    + ",\"\"" 
            );
            
            // Print NRE Profit
            BigDecimal nreProfit = AssemblyPriceService.printRawOrProfit(AssemblyPriceTypes.PROFIT_NRE.getPriceType(), lvl1Key, level2Map, volumeId);
            
            // Calculate NRE Price 
            BigDecimal nrePrice = AssemblyPriceService.addPrices(totalNRE, nreProfit);
            
            // Print NRE Price (NRE Prie = total NRE + NRE Profit)
            System.out.println("\"NRE Price\""
                    + ","
                    + "," + (nrePrice == null ? "" : nrePrice.toString())
                    + ",\"\"" 
            );
            
            // Calculate Volume Price w/ NRE = volume price + nre price
            BigDecimal volumePriceWithNre = AssemblyPriceService.addPrices(volumePrice, nrePrice);
            
            // Print NRE Price (NRE Prie = total NRE + NRE Profit)
            System.out.println("\"Volume Price with NRE\""
                    + ","
                    + "," + (volumePriceWithNre == null ? "" : volumePriceWithNre.toString())
                    + ",\"\"" 
            );            
        }
        
    }
    
    private static BigDecimal printRawOrProfit(final String priceType, final String lvl1Key,
            HashMap<String, Map> level2Map, int volumeId) {
        
        String lvl2Key = lvl1Key + "-" + priceType;

        HashMap<Integer, OutsourceAssemblyPrice> level3Map = (HashMap<Integer, OutsourceAssemblyPrice>) level2Map.get(lvl2Key);
        if (level3Map == null) {
            return null;
        }
        
        OutsourceAssemblyPrice outAssmPrice = level3Map.get(volumeId);
        
        BigDecimal price = outAssmPrice.getPrice();
        System.out.println("\"" + priceType + "\""
                + "," + (outAssmPrice.getTargetPrice() == null ? "" : outAssmPrice.getTargetPrice().toString())
                + "," + (price == null ? "" : price.toString())
                + ",\"" + (outAssmPrice.getNote() == null ? "" : outAssmPrice.getNote()) + "\""
        );

        return price;
    }
    
    /**
     * Print each attribute under this category
     * @param costRollupAttributeType
     * @return the total cost for attributes listed under this category
     */
    private static BigDecimal printAttributes(final String costRollupAttributeType, final String lvl1Key, 
            HashMap<String, Map> level2Map, int volumeId) {
        
        BigDecimal totalAttributesPrice = null;
        
        // Process only lvl2 that start with this keyword
        final String lookupPrefix_lvl2Key = lvl1Key + "-Attributes-" + costRollupAttributeType + "-";
        
        for (String lvl2Key : level2Map.keySet()) {
            if (lvl2Key.startsWith(lookupPrefix_lvl2Key)) {
                // Get costRollupAttributeId from the key
                String costRollupAttributeId = lvl2Key.substring(lvl2Key.lastIndexOf("-")+1);
                
                // Get CostRollupAttribute object from the cache
                CostRollupAttribute costRollupAttribute = (CostRollupAttribute)Cache.costRollupAttributeCache.get(Long.valueOf(costRollupAttributeId));

                HashMap<Integer, OutsourceAssemblyPrice> level3Map = (HashMap<Integer, OutsourceAssemblyPrice>)level2Map.get(lvl2Key);
                if (level3Map == null) {
                    continue;
                }
                
                OutsourceAssemblyPrice outAssmPrice = level3Map.get(volumeId);                

                // Sum up price for each attribute
                totalAttributesPrice = AssemblyPriceService.addPrices(outAssmPrice.getPrice(), totalAttributesPrice);
                
                System.out.println("\"" + costRollupAttribute.getName() + "\""
                        + "," + (outAssmPrice.getTargetPrice() == null ? "" : outAssmPrice.getTargetPrice().toString())
                        + "," + (outAssmPrice.getPrice() == null ? "" : outAssmPrice.getPrice().toString())
                        + ",\"" + (outAssmPrice.getNote() == null ? "" : outAssmPrice.getNote()) + "\""
                );
            }
        }
        
        return totalAttributesPrice;
    }  
    

    private static BigDecimal addPrices(BigDecimal lbd, BigDecimal rbd) {
        if (lbd != null) {
            if (rbd == null) {
                return BigDecimal.ZERO.add(lbd);
            } else {
                return lbd.add(rbd);
            }                   
        } 
        // Since this is an "else-if" block, it implies in the following lbd is null
        else if (rbd != null) {
            return BigDecimal.ZERO.add(rbd); 
        }
        
        // When both lbd and rbd are null, return null
        return null;
    }
    
    /**
     * 
     * @param lbd
     * @param rbd
     * @return null if either lbd or rbd is null.  Otherwise, return the multiplication of those 2 object
     */
    private static BigDecimal multiply(BigDecimal lbd, BigDecimal rbd) {
        if (lbd != null && rbd != null) {
            return lbd.multiply(rbd); 
        } else {
            return null;
        }
    }
}
