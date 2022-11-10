package com.akj.helpyou.activities;

import java.util.List;

public class InfD {
    private String duration_time;
    private String result_time;
    private String cost;
    private List<InfD2> infD2List;

    public InfD(String duration_time, String result_time, String cost, List<InfD2> infD2List) {
        this.duration_time = duration_time;
        this.result_time = result_time;
        this.cost = cost;
        this.infD2List = infD2List;
    }

    public List<InfD2> getInfD2List() {
        return infD2List;
    }

    public void setInfD2List(List<InfD2> infD2List) {
        this.infD2List = infD2List;
    }

    public String getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(String duration_time) {
        this.duration_time = duration_time;
    }

    public String getResult_time() {
        return result_time;
    }

    public void setResult_time(String result_time) {
        this.result_time = result_time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
