package com.polydyne.api.bom.example.model;

import java.math.BigDecimal;

public class ParsedBomLine {
	
	private String assemblyName; //;parent of bom ->assembly part num?
	private String assemblyRevision;//assembly
	private String partNumber; //part
	private String partRevision; //part
	private boolean subassemblyFlag; //order is key and this-b?
	private Integer quantity; //b?
	private String location;//b?
	private String description;//part?
	private String partStatus;//part?
	private String commodity;//part? id is in part
	private String packageType; //->packageCode?, packageId is in parts
	private String units;// part has unit ID
	private String referencePartNumber; //part
	private Double standardCost; //part
	private Double targetPrice; //part
	private Double referencePrice; //part
	private String manufacturerId;//manu-> is manu short name in API
	private String manufacturerName;//manu
	private boolean amlFlag; //mp
	private String manufacturerPartNumber;//mp
	private String internalManufacturerPartNumber;//mp
	private String matrixCategory;//mp 
	private BigDecimal oemPrice; //mp
	private BigDecimal oemPercentage;//mp
	private String shortComment;//mp
	private String longComment;//mp
	private String mpnStatusCode;//mp
	private boolean obsolete;//mp
	private String customField1;//custom field
	private String customField2; ///custom field
	
	private Long projectId;
	
	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getAssemblyRevision() {
		return assemblyRevision;
	}

	public void setAssemblyRevision(String assemblyRevision) {
		this.assemblyRevision = assemblyRevision;
	}

	public String getPartRevision() {
		return partRevision;
	}

	public void setPartRevision(String partRevision) {
		this.partRevision = partRevision;
	}

	public boolean isSubassemblyFlag() {
		return subassemblyFlag;
	}

	public void setSubassemblyFlag(boolean subassembly) {
		this.subassemblyFlag = subassembly;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPartStatus() {
		return partStatus;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getReferencePartNumber() {
		return referencePartNumber;
	}

	public void setReferencePartNumber(String referencePartNumber) {
		this.referencePartNumber = referencePartNumber;
	}

	public Double getStandardCost() {
		return standardCost;
	}

	public void setStandardCost(Double standardCost) {
		this.standardCost = standardCost;
	}

	public Double getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(Double targetPrice) {
		this.targetPrice = targetPrice;
	}

	public Double getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(Double referencePrice) {
		this.referencePrice = referencePrice;
	}

	public String getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public boolean isAmlFlag() {
		return amlFlag;
	}

	public void setAmlFlag(boolean amlFlag) {
		this.amlFlag = amlFlag;
	}

	public String getManufacturerPartNumber() {
		return manufacturerPartNumber;
	}

	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		this.manufacturerPartNumber = manufacturerPartNumber;
	}

	public String getInternalManufacturerPartNumber() {
		return internalManufacturerPartNumber;
	}

	public void setInternalManufacturerPartNumber(String internalManufacturerPartNumber) {
		this.internalManufacturerPartNumber = internalManufacturerPartNumber;
	}

	public String getMatrixCategory() {
		return matrixCategory;
	}

	public void setMatrixCategory(String matrixCategory) {
		this.matrixCategory = matrixCategory;
	}

	public BigDecimal getOemPrice() {
		return oemPrice;
	}

	public void setOemPrice(BigDecimal oemPrice) {
		this.oemPrice = oemPrice;
	}

	public BigDecimal getOemPercentage() {
		return oemPercentage;
	}

	public void setOemPercentage(BigDecimal oemPercentage) {
		this.oemPercentage = oemPercentage;
	}

	public String getShortComment() {
		return shortComment;
	}

	public void setShortComment(String shortComment) {
		this.shortComment = shortComment;
	}

	public String getLongComment() {
		return longComment;
	}

	public void setLongComment(String longComment) {
		this.longComment = longComment;
	}

	public String getMpnStatusCode() {
		return mpnStatusCode;
	}

	public void setMpnStatusCode(String mpnStatusCode) {
		this.mpnStatusCode = mpnStatusCode;
	}

	public boolean getObsolete() {
		return obsolete;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}

	public String getCustomField1() {
		return customField1;
	}

	public void setCustomField1(String rohst) {
		this.customField1 = rohst;
	}

	public String getCustomField2() {
		return customField2;
	}

	public void setCustomField2(String bomCombo57) {
		this.customField2 = bomCombo57;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
	
	
	

}
