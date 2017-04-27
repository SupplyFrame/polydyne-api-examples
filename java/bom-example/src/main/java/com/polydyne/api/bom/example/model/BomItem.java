package com.polydyne.api.bom.example.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BomItem {

	private Long itemId;

	@NotNull
	private Boolean assembly;

	@NotNull
	private Long parentAssemblyId;

	@NotNull
	private Boolean buy; //optional

	@NotNull
	private Long componentId; //either a valid assemblyId or a validPartId

	@NotNull
	private Long itemNumber;

	@Size(max=2000)
	private String location;

	@NotNull
	private Long projectId;

	@NotNull
	private Integer quantity;

	private Date timeStamp;


	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Boolean getAssembly() {
		return this.assembly;
	}

	public void setAssembly(Boolean assembly) {
		this.assembly = assembly;
	}

	public Long getParentAssemblyId() {
		return this.parentAssemblyId;
	}

	public void setParentAssemblyId(Long parentAssemblyId) {
		this.parentAssemblyId = parentAssemblyId;
	}

	public Boolean getBuy() {
		return this.buy;
	}

	public void setBuy(Boolean buy) {
		this.buy = buy;
	}

	public Long getComponentId() {
		return this.componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public Long getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(Long itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
