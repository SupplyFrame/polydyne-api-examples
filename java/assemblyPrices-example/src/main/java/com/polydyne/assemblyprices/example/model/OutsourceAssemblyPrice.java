package com.polydyne.assemblyprices.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Date;

// Add this annotation to ignore fields that don't match this class
@JsonIgnoreProperties(ignoreUnknown = true)

public class OutsourceAssemblyPrice {

    private Long assemblyPriceId;  
    
    private Long projectId;
    private Long customManufacturerId;
    private Long assemblyId;
    private Integer volumeId;
    private String priceType;
    private Long currencyId;
    
    private BigDecimal price;
    
    private Date timeStamp;          
    
    // This shows if the attribute belong to Material, Labor, Overhead, etc
    private String costRollupAttributeType;   
    private Long costRollupAttributeId;
        
    private BigDecimal targetPrice;
    private Date targetPriceTimeStamp; 
    
    private String note;
    private Date noteTimeStamp; // note's timestamp    
    

    /**
     * @return the assemblyPriceId
     */
    public Long getAssemblyPriceId() {
        return assemblyPriceId;
    }

    /**
     * @param assemblyPriceId the assemblyPriceId to set
     */
    public void setAssemblyPriceId(Long assemblyPriceId) {
        this.assemblyPriceId = assemblyPriceId;
    }

    /**
     * @return the projectId
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * @return the customManufacturerId
     */
    public Long getCustomManufacturerId() {
        return customManufacturerId;
    }

    /**
     * @param customManufacturerId the customManufacturerId to set
     */
    public void setCustomManufacturerId(Long customManufacturerId) {
        this.customManufacturerId = customManufacturerId;
    }

    /**
     * @return the assemblyId
     */
    public Long getAssemblyId() {
        return assemblyId;
    }

    /**
     * @param assemblyId the assemblyId to set
     */
    public void setAssemblyId(Long assemblyId) {
        this.assemblyId = assemblyId;
    }

    /**
     * @return the volumeId
     */
    public Integer getVolumeId() {
        return volumeId;
    }

    /**
     * @param volumeId the volumeId to set
     */
    public void setVolumeId(Integer volumeId) {
        this.volumeId = volumeId;
    }

    /**
     * @return the priceType
     */
    public String getPriceType() {
        return priceType;
    }

    /**
     * @param priceType the priceType to set
     */
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    /**
     * @return the costRollupAttributeType
     */
    public String getCostRollupAttributeType() {
        return costRollupAttributeType;
    }

    /**
     * @param costRollupAttributeType the costRollupAttributeType to set
     */
    public void setCostRollupAttributeType(String costRollupAttributeType) {
        this.costRollupAttributeType = costRollupAttributeType;
    }

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
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    /**
     * @return the targetPrice
     */
    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    /**
     * @param targetPrice the targetPrice to set
     */
    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }
    
    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
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

    /**
     * @return the noteTimeStamp
     */
    public Date getNoteTimeStamp() {
        return noteTimeStamp;
    }

    /**
     * @param noteTimeStamp the noteTimeStamp to set
     */
    public void setNoteTimeStamp(Date noteTimeStamp) {
        this.noteTimeStamp = noteTimeStamp;
    }

    /**
     * @return the targetPriceTimeStamp
     */
    public Date getTargetPriceTimeStamp() {
        return targetPriceTimeStamp;
    }

    /**
     * @param targetPriceTimeStamp the targetPriceTimeStamp to set
     */
    public void setTargetPriceTimeStamp(Date targetPriceTimeStamp) {
        this.targetPriceTimeStamp = targetPriceTimeStamp;
    }
    
//    @Override
//    public boolean equals(Object o) {
//        
//        if (o instanceof OutsourceAssemblyPrice) {
//            OutsourceAssemblyPrice newAssmPrice = (OutsourceAssemblyPrice) o;
//            
//            if (this.projectId.compareTo(newAssmPrice.projectId) != 0) {
//                return false;
//            } 
//            else if (this.customManufacturerId.compareTo(newAssmPrice.customManufacturerId) != 0) {
//                return false;
//            }
//            else if(this.assemblyId.compareTo(newAssmPrice.assemblyId) != 0) {
//                return false;
//            }
//            else if (this.priceType != newAssmPrice.priceType) {
//                return false;
//            } 
//            else if (this.costRollupAttributeType != null) {
//                if (newAssmPrice.costRollupAttributeType == null) {
//                    return false;
//                } else {
//                    return (this.costRollupAttributeType.compareTo(newAssmPrice.costRollupAttributeType) == 0);
//                }
//            }
//            else if (this.costRollupAttributeId == null) {
//                return newAssmPrice.costRollupAttributeType == null;
//            }
//        }
//        
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 97 * hash + Objects.hashCode(this.projectId);
//        hash = 97 * hash + Objects.hashCode(this.customManufacturerId);
//        hash = 97 * hash + Objects.hashCode(this.assemblyId);
//        hash = 97 * hash + Objects.hashCode(this.priceType);
//        hash = 97 * hash + Objects.hashCode(this.costRollupAttributeType);
//        return hash;
//    }
    
}
