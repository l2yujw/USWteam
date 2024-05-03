package com.akj.with.activities.odsay.dto;

public class WalkDto {

    private String circle;
    private String startTraffic; // 승차 정류장/역 명
    private int moveTime; // 구간 별 이동시간
    private int stationCount; // 정차하는 정거장 수
    //  private int img;
    private String passName; // 정차하는 정거장/역 상세 구간 이름
    private int wCode; // 1:상행 2:하행 3: 지하철아님(도보나 버스)
    private String startX;
    private String startY;
    private int busTime; // 버스 도착 시간 (초)

    public WalkDto(String circle, String startTraffic, int moveTime, int stationCount, String passName, int wCode, String startX, String startY, int busTime) {
        this.circle = circle;
        this.startTraffic = startTraffic;
        this.moveTime = moveTime;
        this.stationCount = stationCount;
        this.passName = passName;
        this.wCode = wCode;
        this.startX = startX;
        this.startY = startY;
        this.busTime = busTime;
    }

    public String getCircle() {
        return circle;
    }

    public String getStartTraffic() {
        return startTraffic;
    }

    public int getMoveTime() {
        return moveTime;
    }

    public int getStationCount() {
        return stationCount;
    }

    public String getPassName() {
        return passName;
    }

    public int getwCode() {
        return wCode;
    }

    public String getStartX() {
        return startX;
    }

    public String getStartY() {
        return startY;
    }

    public int getBusTime() {
        return busTime;
    }
}
