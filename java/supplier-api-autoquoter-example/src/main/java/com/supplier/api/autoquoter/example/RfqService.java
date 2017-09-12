package com.supplier.api.autoquoter.example;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.supplier.api.autoquoter.example.model.BatchRequest;
import com.supplier.api.autoquoter.example.model.BatchResponse;
import com.supplier.api.autoquoter.example.model.ManufacturerInfoModel;
import com.supplier.api.autoquoter.example.model.Rfq;
import com.supplier.api.autoquoter.example.model.RfqPriceVolume;

public class RfqService {

    public static ObjectMapper mapper = new ObjectMapper();

    public void autoQuoteRfqs() throws UnirestException {

        int priceQtedCount = 0;
        int priceNotThereAndNotQtedCount =0;
        int priceExistedCount = 0;
        int leadtimeQtedCount = 0;
        int leadtimeNotThereAndNotQtedCount =0;
        int leadtimeExistedCount = 0;
        int moqQtedCount = 0;
        int moqNotThereAndNotQtedCount =0;
        int moqExistedCount = 0;

        ArrayList<Rfq> rfqList = new ArrayList<Rfq>();


        // For each RFQ, check if price per volume, leadtime and moq have values. If they are empty, autoquote them 
        for (Rfq rfq : Cache.rfqSet) {

            Rfq newRfq = new Rfq();   
            List<RfqPriceVolume> newRfqPriceVolumeList = new ArrayList<>();
            List<RfqPriceVolume> volumeList = rfq.getVolumes();

            // Check if each price in the volumes list is quoted
            for(RfqPriceVolume priceVolume : volumeList){
                if(priceVolume.getPrice()==null){
                    // Autoquote prices 
                    RfqPriceVolume newRfqPriceVolume = new RfqPriceVolume();
                    Double autoQuotedPrice = autoQuotePrice(rfq);
                    if(autoQuotedPrice!=null){
                        // If the autoquote gives a price, create an object with the volume number and autoquoted price
                        newRfqPriceVolume.setVolumeId(priceVolume.getVolumeId());
                        newRfqPriceVolume.setPrice(autoQuotedPrice);
                        newRfqPriceVolumeList.add(newRfqPriceVolume);
                        priceQtedCount++;

                    }
                    else{
                        priceNotThereAndNotQtedCount++;
                    }
                }
                else{
                    priceExistedCount++;
                }
            }  

            if(newRfqPriceVolumeList.size()!=0){
                newRfq.setVolumes(newRfqPriceVolumeList);
            }


            if(rfq.getLeadTime()==null){
                Double autoQuotedLeadtime = autoQuoteLeadtime(rfq);
                if(autoQuotedLeadtime!=null){
                    newRfq.setLeadTime(autoQuotedLeadtime);
                    leadtimeQtedCount++;
                }
                else{
                    leadtimeNotThereAndNotQtedCount++;
                }
            }
            else{
                leadtimeExistedCount++;
            }
            if(rfq.getMoqQuantity()==null){
                Double autoQuotedMoq = autoQuoteMoq(rfq);
                if(autoQuotedMoq!=null){
                    newRfq.setMoqQuantity(autoQuotedMoq);
                    moqQtedCount++;
                }
                else{
                    moqNotThereAndNotQtedCount++;
                }
            }
            else{
                moqExistedCount++;
            }

            // If rfq has atleast one of volume or leadtime or moq autoquoted, add to list to be updated
            if((newRfq.getVolumes()!=null && !newRfq.getVolumes().isEmpty() && newRfq.getVolumes().size()!=0) || 
                    (newRfq.getLeadTime()!=null) || (newRfq.getMoqQuantity()!=null)){

                newRfq.setCustomerId(rfq.getCustomerId());
                newRfq.setProjectId(rfq.getProjectId());
                newRfq.setRfqId(rfq.getRfqId());
                newRfq.setSiteId(rfq.getSiteId());
                rfqList.add(newRfq);
            }

        }

        double totalRfqs = Cache.rfqSet.size();
        if(totalRfqs>0){
            System.out.println("Price autoquoted: "+Math.round(priceQtedCount*100/totalRfqs)+"% \nPrice not autoquoted:  "
                    +Math.round(priceNotThereAndNotQtedCount*100/totalRfqs)+"%\nPrice already quoted:  "+Math.round(priceExistedCount*100/totalRfqs)+"%");
            System.out.println("Leadtime autoquoted: "+Math.round(leadtimeQtedCount*100/totalRfqs)+"% \nLeadtime not autoquoted:  "
                    +Math.round(leadtimeNotThereAndNotQtedCount*100/totalRfqs)+"%\nLeadtime already quoted:  "+Math.round(leadtimeExistedCount*100/totalRfqs)+"%");
            System.out.println("Moq autoquoted: "+Math.round(moqQtedCount*100/totalRfqs)+"% \nMoq not autoquoted:  "
                    + Math.round(moqNotThereAndNotQtedCount*100/totalRfqs)+"%\nMoq already quoted:  "+Math.round(moqExistedCount*100/totalRfqs)+"%");

            // Send list of rfqs to be updated
            List<Rfq> patchedRfqs = patchRfqs(rfqList);
            for(Rfq r : patchedRfqs){
                System.out.println("Rfq updated with rfqId: "+ r.getRfqId() + " and projectId/siteId: " + r.getProjectId());
            }
        }
        else{
            System.out.println("There are no rfqs to be updated");
        }
    }

    private List<Rfq> patchRfqs(ArrayList<Rfq> rfqList) throws UnirestException {
        List<Rfq> patchedRfqList = new ArrayList<Rfq>();
        List<List<Rfq>> rfqBatches = HttpHelper.splitIntoBatches(rfqList, 100);

        for(List<Rfq> batchRfq : rfqBatches) {
            BatchRequest[] batchRequest = new BatchRequest[batchRfq.size()];
            for(int i=0; i< batchRfq.size();i++){
                batchRequest[i] = new BatchRequest();
                batchRequest[i].setMethod("PATCH");
                if(batchRfq.get(i).getSiteId()!=null){
                    // for site view projects
                    batchRequest[i].setResource("/customers/"+ batchRfq.get(i).getCustomerId() +"/projects/"+batchRfq.get(i).getProjectId()+
                            "/sites/"+batchRfq.get(i).getSiteId()+"/rfqs/"+batchRfq.get(i).getRfqId());
                }
                else{
                    batchRequest[i].setResource("/customers/"+ batchRfq.get(i).getCustomerId() +"/projects/"+batchRfq.get(i).getProjectId()+"/rfqs/"+batchRfq.get(i).getRfqId());                     
                }
                batchRequest[i].setBody(batchRfq.get(i));
            }

            HttpResponse<BatchResponse[]> rsp = HttpHelper.post("/batch")
                    .body(batchRequest)
                    .asObject(BatchResponse[].class);

            //Extract response
            BatchResponse[] batchResponse = rsp.getBody();

            for(int i=0; i< batchResponse.length;i++){
                Rfq patchedRfq = mapper.convertValue(batchResponse[i].getBody(), Rfq.class);
                patchedRfqList.add(patchedRfq);
            }
        }

        return patchedRfqList;

    }


    private Double autoQuotePrice(Rfq rfq) {

        // Autoquote the price of the rfq to the average price from the hashmap for the given mpn and manufacturer name if it exists
        ManufacturerInfoModel manufacturerInfoModel = new ManufacturerInfoModel();
        manufacturerInfoModel.setManufacturerName(rfq.getManufacturerName());
        manufacturerInfoModel.setManufacturerPartNumber(rfq.getManufacturerPartNumber());

        Double autoQuotedPrice = Cache.avgPriceMap.get(manufacturerInfoModel);

        return autoQuotedPrice;
    }

    private Double autoQuoteMoq(Rfq rfq) {

        ManufacturerInfoModel manufacturerInfoModel = new ManufacturerInfoModel();
        manufacturerInfoModel.setManufacturerName(rfq.getManufacturerName());
        manufacturerInfoModel.setManufacturerPartNumber(rfq.getManufacturerPartNumber());

        Double autoQuotedMoq = Cache.avgMoqMap.get(manufacturerInfoModel);

        return autoQuotedMoq;
    }

    private Double autoQuoteLeadtime(Rfq rfq) {

        ManufacturerInfoModel manufacturerInfoModel = new ManufacturerInfoModel();
        manufacturerInfoModel.setManufacturerName(rfq.getManufacturerName());
        manufacturerInfoModel.setManufacturerPartNumber(rfq.getManufacturerPartNumber());

        Double autoQuotedLeadtime = Cache.avgLeadtimeMap.get(manufacturerInfoModel);

        return autoQuotedLeadtime;
    }



}
