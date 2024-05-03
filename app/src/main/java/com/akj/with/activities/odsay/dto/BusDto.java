package com.akj.with.activities.odsay.dto;

public class BusDto {

    private String circle;
    private String startTraffic; // 승차 정류장/역 명
    private String endTraffic; // 하차 정류장/역 명
    private int moveTime; // 구간 별 이동시간
    private int stationCount; // 정차하는 정거장 수
    //  private int img;
    private String busID; // 버스 정류장 고유 ID
    private String passName; // 정차하는 정거장/역 상세 구간 이름
    private int wCode; // 1:상행 2:하행 3: 지하철아님(도보나 버스)
    private String startX;
    private String startY;
    private String endX;
    private String endY;
    private String low; // 저상버스 유무 (Y/N)
    private int busTime; // 버스 도착 시간 (초)

    public BusDto(String circle, String startTraffic, String endTraffic, int moveTime, int stationCount, String busID,
                  String passName, int wCode, String startX, String startY, String endX, String endY, String low, int busTime) {
        this.circle = circle;
        this.startTraffic = startTraffic;
        this.endTraffic = endTraffic;
        this.moveTime = moveTime;
        this.stationCount = stationCount;
        this.busID = busID;
        this.passName = passName;
        this.wCode = wCode;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.low = low;
        this.busTime = busTime;
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

    public String getBusID() {
        return busID;
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

    public String getLow() {
        return low;
    }

    public int getBusTime() {
        return busTime;
    }
}
