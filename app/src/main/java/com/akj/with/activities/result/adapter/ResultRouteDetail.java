package com.akj.with.activities.result.adapter;

import java.util.List;

public class ResultRouteDetail {
    private String vehicle;
    private String startPoint;
    private String endPoint;
    private String time;
    private String etc;
    private String details;
    private List<ResultRouteDetailInner> routeDetailInnerList;

    public ResultRouteDetail(String vehicle, String startPoint, String endPoint, String time, String etc, String details, List<ResultRouteDetailInner> routeDetailInnerList) {
        this.vehicle = vehicle;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.time = time;
        this.etc = etc;
        this.details = details;
        this.routeDetailInnerList = routeDetailInnerList;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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

    public List<ResultRouteDetailInner> getRouteDetailInnerList() {
        return routeDetailInnerList;
    }

    public void setRouteDetailInnerList(List<ResultRouteDetailInner> routeDetailInnerList) {
        this.routeDetailInnerList = routeDetailInnerList;
    }
}
