package com.polydyne.api.bom.example.creation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.polydyne.api.bom.example.HttpHelper;
import com.polydyne.api.bom.example.model.Project;

public class ProjectService {

    public static long createProject(String projectName, String projectDescription, int volumeBreaks,
            long regionId, long currencyId, long customerId) throws UnirestException {
        
        String projectType = "RFQ"; 
        String status = "IN PROCESS";

        // Project object: set any other optional fields that you need
        Project project = new Project();
        project.setName(projectName);
        project.setProjectType(projectType);
        project.setRegionId(regionId);
        project.setCurrencyId(currencyId);
        project.setStatus(status);
        project.setCustomerId(customerId); 
        project.setDescription(projectDescription);
        project.setVolumes(volumeBreaks); //set the number of volume breaks you want

        // POST to create Project
        HttpResponse<Project> rsp = HttpHelper.post("/projects/")
                .body(project)
                .asObject(Project.class);

        // Extract response
        project = rsp.getBody();

        System.out.println("Created project with ID "+ project.getProjectId());

        return project.getProjectId();
    }
}
