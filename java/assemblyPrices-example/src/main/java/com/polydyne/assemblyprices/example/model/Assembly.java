package com.polydyne.assemblyprices.example.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Assembly {

	private Long assemblyId;

	@NotNull
	@Size(max=100)
	private String assemblyPartNumber;

	@Size(max=400)
	private String description;

	@NotNull
	private Long projectId;

	private Double refStandard;

	@Size(max=100)
	private String revision;

	private Date timeStamp;
	
	private List<AssemblyVolume> volumes;
	

	public Long getAssemblyId() {
		return this.assemblyId;
	}

	public void setAssemblyId(Long assemblyId) {
		this.assemblyId = assemblyId;
	}

	public String getAssemblyPartNumber() {
		return this.assemblyPartNumber;
	}

	public void setAssemblyPartNumber(String assemblyPartNumber) {
		this.assemblyPartNumber = assemblyPartNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Double getRefStandard() {
		return this.refStandard;
	}

	public void setRefStandard(Double refStandard) {
		this.refStandard = refStandard;
	}

	public String getRevision() {
		return this.revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<AssemblyVolume> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<AssemblyVolume> volumes) {
		this.volumes = volumes;
	}
	
	



}
