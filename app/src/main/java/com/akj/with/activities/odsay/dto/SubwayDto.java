package com.akj.with.activities.odsay.dto;

public class SubwayDto {
    private String circle;
    private String startTraffic; // 승차 정류장/역 명
    private String endTraffic; // 하차 정류장/역 명
    private int moveTime; // 구간 별 이동시간
    private int stationCount; // 정차하는 정거장 수
    //  private int img;
    private String passName; // 정차하는 정거장/역 상세 구간 이름
    private int wCode; // 1:상행 2:하행 3: 지하철아님(도보나 버스)
    private String startX;
    private String startY;
    private String endX;
    private String endY;
    private int busTime; // 버스 도착 시간 (초)
    private String startTel; // 지하철 승차역 전화번호
    private String endTel; // 지하철 하차역 전화번호

    public SubwayDto(String circle, String startTraffic, String endTraffic, int moveTime, int stationCount,
                     String passName, int wCode, String startX, String startY, String endX, String endY, int busTime,
                     String startTel, String endTel) {
        this.circle = circle;
        this.startTraffic = startTraffic;
        this.endTraffic = endTraffic;
        this.moveTime = moveTime;
        this.stationCount = stationCount;
        this.passName = passName;
        this.wCode = wCode;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.busTime = busTime;
        this.startTel = startTel;
        this.endTel = endTel;
    }

    public String getCircle() {
        return circle;
    }

    public String getStartTraffic() {
        return startTraffic;
    }

    public String getEndTraffic() {
        return endTraffic;
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

    public String getEndX() {
        return endX;
    }

    public String getEndY() {
        return endY;
    }

    public int getBusTime() {
        return busTime;
    }

    public String getStartTel() {
        return startTel;
    }

    public String getEndTel() {
        return endTel;
    }
}
