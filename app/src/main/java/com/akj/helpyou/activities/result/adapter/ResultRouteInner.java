package com.akj.helpyou.activities.result.adapter;

public class ResultRouteInner {
    int img;
    String vehicle;
    String startPoint;

    public ResultRouteInner(String vhnum, String startpoint, int img) {
        this.vehicle = vhnum;
        this.startPoint = startpoint;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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
}
