package com.akj.helpyou.activities.result.adapter;

import java.util.List;

public class ResultRoute {
    private String total;
    private String time;
    private String cost;
    private List<ResultRouteInner> resultRouteInnerList;

    public ResultRoute(String duration_time, String result_time, String cost, List<ResultRouteInner> inf2List) {
        this.total = duration_time;
        this.time = result_time;
        this.cost = cost;
        this.resultRouteInnerList = inf2List;
    }

    public List<ResultRouteInner> getResultRouteInnerList() {
        return resultRouteInnerList;
    }

    public void setResultRouteInnerList(List<ResultRouteInner> resultRouteInnerList) {
        this.resultRouteInnerList = resultRouteInnerList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
