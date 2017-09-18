package com.polydyne.assemblyprices.example.creation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.assemblyprices.example.HttpHelper;
import com.polydyne.assemblyprices.example.Main;
import com.polydyne.assemblyprices.example.model.Assembly;

public class AssemblyService {
    
    public static Assembly getAssembly(String projectId, String assemblyId) throws UnirestException {
        String url = Main.API_BASE_URL + "/projects/" + projectId+ "/assemblies/" + assemblyId ;
        HttpResponse<Assembly> rsp = HttpHelper.get(url).asObject(Assembly.class);
        
        return (Assembly)rsp.getBody();
    }
    
}
