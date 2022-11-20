package com.akj.transport3.activities;

public class SubwayDTime {
    private String startTime;
    private String startPoint;
    private String endPoint;

    public SubwayDTime(String startTime, String startPoint, String endPoint) {
        this.startTime = startTime;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
}
