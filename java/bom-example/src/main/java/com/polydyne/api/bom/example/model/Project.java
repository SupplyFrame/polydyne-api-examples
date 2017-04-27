package com.polydyne.api.bom.example.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Project {
	private Long projectId;

	private Long awardsFlags;

	private Long branchId;

	private Integer combo1;

	private Integer combo2;

	private Integer combo3;

	@NotNull
	private Long currencyId;

	private Long customerContactId;

	@NotNull
	private Long customerId;

	@Size(max=1000)
	private String description;

	private Date dueDate;

	@Size(max=2000)
	private String globalInformationUrl;

	private Date completedDate;

	private Boolean groupByCpn;

	private String laborType;

	private String lockedBy;

	private Boolean locked;

	@NotNull
	@Size(max=150)
	private String name;

	@Size(max=2000)
	private String notes;

	@Size(max=25)
	private String origin;

	@NotNull
	//	    @Pattern(regexp="^(RFQ|CONTRACTS|LABOR|APPEND|APPENDED-CHILD)$")
	@Pattern(regexp="^(RFQ|VPA|RFQ by CPN|VPA by CPN|Site View)$")
	private String projectType;

	private Date receivedDate;

	@NotNull
	private Long regionId;

	@Size(max=2000)
	private String rfqNotes;

	@Size(max=100)
	private String rfqTemplateName;

	private Long projectOwnerId;

	private Date productionStartDate;

	@NotNull
	@Pattern(regexp="^(IN PROCESS|COMPLETED|WON|LOST|CANCELLED|PROSPECT|UNDECIDED)$")
	private String status;

	private Date supplierDueDate;

	@Size(max=255)
	private String text1;

	private Date timeStamp;

	private Long userGroupId;

	private BigDecimal value;

	private BigDecimal value2;

	@Min(1)
	@Max(20)
	private Integer volumes;

	private Long workflowId;

	public Long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getAwardsFlags() {
		return this.awardsFlags;
	}

	public void setAwardsFlags(Long awardsFlags) {
		this.awardsFlags = awardsFlags;
	}

	public Long getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Integer getCombo1() {
		return this.combo1;
	}

	public void setCombo1(Integer combo1) {
		this.combo1 = combo1;
	}

	public Integer getCombo2() {
		return this.combo2;
	}

	public void setCombo2(Integer combo2) {
		this.combo2 = combo2;
	}

	public Integer getCombo3() {
		return this.combo3;
	}

	public void setCombo3(Integer combo3) {
		this.combo3 = combo3;
	}

	public Long getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getCustomerContactId() {
		return this.customerContactId;
	}

	public void setCustomerContactId(Long customerContactId) {
		this.customerContactId = customerContactId;
	}

	public Long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getGlobalInformationUrl() {
		return this.globalInformationUrl;
	}

	public void setGlobalInformationUrl(String globalInformationUrl) {
		this.globalInformationUrl = globalInformationUrl;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Boolean getGroupByCpn() {
		return this.groupByCpn;
	}

	public void setGroupByCpn(Boolean groupByCpn) {
		this.groupByCpn = groupByCpn;
	}

	public String getLaborType() {
		return this.laborType;
	}

	public void setLaborType(String laborType) {
		this.laborType = laborType;
	}

	public String getLockedBy() {
		return this.lockedBy;
	}

	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getProjectType() {
		return this.projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Date getReceivedDate() {
		return this.receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Long getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRfqNotes() {
		return this.rfqNotes;
	}

	public void setRfqNotes(String rfqNotes) {
		this.rfqNotes = rfqNotes;
	}

	public String getRfqTemplateName() {
		return this.rfqTemplateName;
	}

	public void setRfqTemplateName(String rfqTemplateName) {
		this.rfqTemplateName = rfqTemplateName;
	}

	public Long getProjectOwnerId() {
		return this.projectOwnerId;
	}

	public void setProjectOwnerId(Long projectOwnerId) {
		this.projectOwnerId = projectOwnerId;
	}

	public Date getProductionStartDate() {
		return this.productionStartDate;
	}

	public void setProductionStartDate(Date productionStartDate) {
		this.productionStartDate = productionStartDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSupplierDueDate() {
		return this.supplierDueDate;
	}

	public void setSupplierDueDate(Date supplierDueDate) {
		this.supplierDueDate = supplierDueDate;
	}

	public String getText1() {
		return this.text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue2() {
		return this.value2;
	}

	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}

	public Integer getVolumes() {
		return this.volumes;
	}

	public void setVolumes(Integer volumes) {
		this.volumes = volumes;
	}

	public Long getWorkflowId() {
		return this.workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

}
