package com.polydyne.api.bom.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MpnStatus {

    private long mpnStatusId;
    private String mpnStatusCode;

    public MpnStatus() {}

    public long getMpnStatusId() {
        return mpnStatusId;
    }

    public void setMpnStatusId(long mpnStatusId) {
        this.mpnStatusId = mpnStatusId;
    }

    public String getMpnStatusCode() {
        return mpnStatusCode;
    }

    public void setMpnStatusCode(String mpnStatusCode) {
        this.mpnStatusCode = mpnStatusCode;
    }


}
