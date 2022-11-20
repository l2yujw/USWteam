package com.akj.helpyou.activities;

import java.util.List;

public class Inf {
    private String duration_time;
    private String result_time;
    private String cost;
    private List<Inf2> inf2List;

    public Inf(String duration_time, String result_time, String cost, List<Inf2> inf2List) {
        this.duration_time = duration_time;
        this.result_time = result_time;
        this.cost = cost;
        this.inf2List = inf2List;
    }

    public List<Inf2> getInf2List() {
        return inf2List;
    }

    public void setInf2List(List<Inf2> inf2List) {
        this.inf2List = inf2List;
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
