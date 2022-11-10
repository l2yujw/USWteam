package com.akj.helpyou.activities;

public class InfD2 {
    private String vh;
    private String startpoint;
    private String destination;
    private String vhnum;
    private String details;
    private String duration;

    public InfD2(String vh, String startpoint, String destination, String vhnum, String details, String duration) {
        this.vh = vh;
        this.startpoint = startpoint;
        this.destination = destination;
        this.vhnum = vhnum;
        this.details = details;
        this.duration = duration;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getVhnum() {
        return vhnum;
    }

    public void setVhnum(String vhnum) {
        this.vhnum = vhnum;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
