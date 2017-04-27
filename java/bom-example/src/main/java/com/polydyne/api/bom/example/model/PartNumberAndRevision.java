package com.polydyne.api.bom.example.model;

public class PartNumberAndRevision {
    
    public String partNumber;
    public String revision;
    
    
    public PartNumberAndRevision(String partNumber, String revision) {
        super();
        this.partNumber = partNumber;
        this.revision = revision;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((partNumber == null) ? 0 : partNumber.hashCode());
        result = prime * result
                + ((revision == null) ? 0 : revision.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PartNumberAndRevision other = (PartNumberAndRevision) obj;
        if (partNumber == null) {
            if (other.partNumber != null)
                return false;
        } else if (!partNumber.equals(other.partNumber))
            return false;
        if (revision == null) {
            if (other.revision != null)
                return false;
        } else if (!revision.equals(other.revision))
            return false;
        return true;
    }

}
