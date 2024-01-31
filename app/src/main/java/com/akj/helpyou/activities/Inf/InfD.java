package com.akj.helpyou.activities.Inf;

import java.util.List;

public class InfD {
    private String vh;
    private String startpoint;
    private String endpoint;
    private String time;
    private String etc;
    private String details;
    private List<InfD2> infD2List;

    public InfD(String vh, String startpoint, String endpoint, String time, String etc, String details, List<InfD2> infD2List) {
        this.vh = vh;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
        this.time = time;
        this.etc = etc;
        this.details = details;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<InfD2> getInfD2List() {
        return infD2List;
    }

    public void setInfD2List(List<InfD2> infD2List) {
        this.infD2List = infD2List;
    }
}
