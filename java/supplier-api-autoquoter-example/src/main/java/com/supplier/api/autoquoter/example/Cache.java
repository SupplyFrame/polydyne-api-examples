package com.supplier.api.autoquoter.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.supplier.api.autoquoter.example.model.Customer;
import com.supplier.api.autoquoter.example.model.Manufacturer;
import com.supplier.api.autoquoter.example.model.ManufacturerInfoModel;
import com.supplier.api.autoquoter.example.model.Project;
import com.supplier.api.autoquoter.example.model.ProjectCustomer;
import com.supplier.api.autoquoter.example.model.Rfq;


public class Cache {

    public static Map<Long, String> customerCache = new HashMap<Long, String>();
    public static Map<Long, HashMap<Long,String>> manufacturerCustomerCache = new HashMap<Long, HashMap<Long,String>>(); //customer-> map of mfgId to mfgName
    public static Set<ProjectCustomer> projectCustomerSet = new HashSet<ProjectCustomer>(); 
    public static Set<ProjectCustomer> projectAndCustomerSet = new HashSet<ProjectCustomer>(); 
    public static Set<Rfq> rfqSet = new HashSet<Rfq>();
    public static Map<ManufacturerInfoModel, List<Double>> priceMap = new HashMap<ManufacturerInfoModel, List<Double>>();
    public static Map<ManufacturerInfoModel, List<Double>> leadtimeMap = new HashMap<ManufacturerInfoModel, List<Double>>();
    public static Map<ManufacturerInfoModel, List<Double>> moqMap = new HashMap<ManufacturerInfoModel, List<Double>>();
    public static Map<ManufacturerInfoModel, Double> avgPriceMap = new HashMap<ManufacturerInfoModel, Double>();
    public static Map<ManufacturerInfoModel, Double> avgLeadtimeMap = new HashMap<ManufacturerInfoModel, Double>();
    public static Map<ManufacturerInfoModel, Double> avgMoqMap = new HashMap<ManufacturerInfoModel, Double>();



    // Get all customers for the supplier
    public static void cacheCustomer() throws UnirestException {
        String url = Main.API_BASE_URL + "/customers";
        while(url!=null){
            HttpResponse<Customer[]> rsp = HttpHelper.get(url).asObject(Customer[].class);

            for(Customer customer: rsp.getBody()){
                customerCache.put(customer.getCustomerId(), customer.getName());
            }
            url = HttpHelper.getNextUrl(rsp.getHeaders());
            HttpHelper.pause();
        }
        System.out.println("Cached customer size is: "+ customerCache.size());
    }


    public static void cacheManufacturersForCustomer() throws UnirestException {
        //for each customer in the cache, get the corresponding manufacturers
        for (Long customerId : customerCache.keySet()) {
            String url = Main.API_BASE_URL + "/customers/"+customerId+"/manufacturers";
            while(url!=null){
                // Request page of custom fields
                HttpResponse<Manufacturer[]> rsp = HttpHelper.get(url).asObject(Manufacturer[].class);

                HashMap<Long, String> manufacturerCache = manufacturerCustomerCache.getOrDefault(customerId, new HashMap<Long, String>());
                for(Manufacturer manufacturer: rsp.getBody()){
                    manufacturerCache.put(manufacturer.getManufacturerId(), manufacturer.getName());
                    manufacturerCustomerCache.put(customerId,manufacturerCache);

                }
                url = HttpHelper.getNextUrl(rsp.getHeaders());
                HttpHelper.pause();
            }

            System.out.println("Manufacturers cached for customerId: "+ customerId);
        }  
    }


    public static void getProjectsForCustomers() throws UnirestException {
        for (Long customerId : customerCache.keySet()) {
            String url = Main.API_BASE_URL + "/customers/"+customerId+"/projects?active=true";
            while(url!=null){
                
                HttpResponse<Project[]> rsp = HttpHelper.get(url).asObject(Project[].class);
                for(Project project: rsp.getBody()){
                    //only active projects are considered as we need to patch rfqs

                    ProjectCustomer projectCustomer = new ProjectCustomer();
                    projectCustomer.setCustomerId(customerId);
                    projectCustomer.setProjectId(project.getProjectId());
                    //for non site view it will remain null
                    projectCustomerSet.add(projectCustomer);
                }
                url = HttpHelper.getNextUrl(rsp.getHeaders());
                HttpHelper.pause();
            }
        }  
    }



    public static void getSiteViews() throws UnirestException {

        List<ProjectCustomer> duplicateSiteViewProjectsList = new ArrayList<ProjectCustomer>();
        for (ProjectCustomer projectCustomer : projectCustomerSet) {
            String url = Main.API_BASE_URL + "/customers/"+projectCustomer.getCustomerId()+"/projects/"+projectCustomer.getProjectId()+"/sites";
            while(url!=null){
         
                HttpResponse<ProjectCustomer[]> rsp = HttpHelper.get(url).asObject(ProjectCustomer[].class);

                if(rsp.getBody().length!=0){

                    for(ProjectCustomer site : rsp.getBody()){
                        //add projectId, siteId, customerId
                        site.setCustomerId(projectCustomer.getCustomerId());
                        site.setProjectId(projectCustomer.getProjectId());

                        site.setSiteId(site.getSiteId());
                        projectAndCustomerSet.add(site); //add it again
                    }

                    ProjectCustomer pc = new ProjectCustomer();
                    pc.setCustomerId(projectCustomer.getCustomerId());
                    pc.setProjectId(projectCustomer.getProjectId());

                    if(projectCustomerSet.contains(pc)){
                        // Remove site view projects that were added before with null siteId
                        duplicateSiteViewProjectsList.add(pc);
                    }

                }
                url = HttpHelper.getNextUrl(rsp.getHeaders());
                HttpHelper.pause();
            }
        }  

        projectCustomerSet.removeAll(duplicateSiteViewProjectsList);
        projectCustomerSet.addAll(projectAndCustomerSet);

        System.out.println("Total projects and site views: "+ projectCustomerSet.size());
    }


    public static void getRfqsForProjectPerCustomer() throws UnirestException, JsonParseException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        for (ProjectCustomer projectCustomer : projectCustomerSet) {
            //different url for site view projects
            String url;
            if(projectCustomer.getSiteId()!=null){
                url = Main.API_BASE_URL + "/customers/"+projectCustomer.getCustomerId()+"/projects/"+projectCustomer.getProjectId()+"/sites/"
                        +projectCustomer.getSiteId()+"/rfqs";
            }
            else{
                url = Main.API_BASE_URL + "/customers/"+projectCustomer.getCustomerId()+"/projects/"+projectCustomer.getProjectId()+"/rfqs";
            }

            while(url!=null){
                HttpResponse<JsonNode> rsp = HttpHelper.get(url).asJson();

                // Get rfqs if they exist. Some projects may be under construction and rfqs cannot be viewed
                if(rsp.getStatus()==200){
                    for(int i=0;i<rsp.getBody().getArray().length();i++){

                        JSONObject jsonObj = rsp.getBody().getArray().getJSONObject(i);                       
                        Rfq rfqProjectCustomer = mapper.readValue(jsonObj.toString(), Rfq.class);             
                        rfqProjectCustomer.setCustomerId(projectCustomer.getCustomerId());
                        rfqProjectCustomer.setProjectId(projectCustomer.getProjectId());
                        HashMap<Long, String> manufacturerCache = manufacturerCustomerCache.get(projectCustomer.getCustomerId());
                        rfqProjectCustomer.setManufacturerName(manufacturerCache.get(rfqProjectCustomer.getManufacturerId()));
                        if(projectCustomer.getSiteId()!=null){
                            rfqProjectCustomer.setSiteId(projectCustomer.getSiteId());
                        }

                        rfqSet.add(rfqProjectCustomer);

                    }

                    url = HttpHelper.getNextUrl(rsp.getHeaders());
                    HttpHelper.pause();
                }
                else{

                    break;
                }

            }
        }  
        System.out.println("Cached rfq size: "+rfqSet.size());
    }

}
