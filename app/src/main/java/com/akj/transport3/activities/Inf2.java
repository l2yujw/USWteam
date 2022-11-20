package com.akj.transport3.activities;

public class Inf2 {
    int img;
    String vhnum;
    String startpoint;

    public Inf2(String vhnum, String startpoint, int img) {
        this.vhnum = vhnum;
        this.startpoint = startpoint;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getVhnum() {
        return vhnum;
    }

    public void setVhnum(String vhnum) {
        this.vhnum = vhnum;
    }

    public String getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(String startpoint) {
        this.startpoint = startpoint;
    }
}
