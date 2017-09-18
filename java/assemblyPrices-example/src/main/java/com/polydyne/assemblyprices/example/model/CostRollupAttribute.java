package com.polydyne.assemblyprices.example.model;

import java.math.BigDecimal;
import java.util.Date;

public class CostRollupAttribute {    
    
    private Long costRollupAttributeId;
    private String name;
    
    private String attributeType;
    
    private String comment;
    private BigDecimal fixedAdder;
    private BigDecimal percentAdder;
    private Long currencyId;
    private Date timeStamp;
    
    
    /**
     * @return the costRollupAttributeId
     */
    public Long getCostRollupAttributeId() {
        return costRollupAttributeId;
    }

    /**
     * @param costRollupAttributeId the costRollupAttributeId to set
     */
    public void setCostRollupAttributeId(Long costRollupAttributeId) {
        this.costRollupAttributeId = costRollupAttributeId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the attributeType
     */
    public String getAttributeType() {
        return attributeType;
    }


    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the fixedAdder
     */
    public BigDecimal getFixedAdder() {
        return fixedAdder;
    }

    /**
     * @param fixedAdder the fixedAdder to set
     */
    public void setFixedAdder(BigDecimal fixedAdder) {
        this.fixedAdder = fixedAdder;
    }

    /**
     * @return the percentAdder
     */
    public BigDecimal getPercentAdder() {
        return percentAdder;
    }

    /**
     * @param percentAdder the percentAdder to set
     */
    public void setPercentAdder(BigDecimal percentAdder) {
        this.percentAdder = percentAdder;
    }

    /**
     * @return the currencyId
     */
    public Long getCurrencyId() {
        return currencyId;
    }

    /**
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    

    
}
