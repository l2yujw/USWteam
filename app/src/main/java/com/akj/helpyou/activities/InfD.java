package com.akj.helpyou.activities;

import java.util.List;

public class InfD {
    private String vh;
    private String startpoint;
    private String endpoint;
    private String details;
    private String etc;
    private List<InfD2> infD2List;

    public InfD(String vh, String startpoint, String endpoint, String details, String etc, List<InfD2> infD2List) {
        this.vh = vh;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
        this.details = details;
        this.etc = etc;
        this.infD2List = infD2List;
    }

    public String getVh() {
        return vh;
    }

    public void setVh(String vh) {
        this.vh = vh;
    }

    public String getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(String startpoint) {
        this.startpoint = startpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public List<InfD2> getInfD2List() {
        return infD2List;
    }

    public void setInfD2List(List<InfD2> infD2List) {
        this.infD2List = infD2List;
    }
}
