package com.akj.helpyou.activities;

public class SubwayDTime {
    private String startTime;
    private String startMinute;

    public SubwayDTime(String startTime, String startMinute) {
        this.startTime = startTime;
        this.startMinute = startMinute;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }
}
