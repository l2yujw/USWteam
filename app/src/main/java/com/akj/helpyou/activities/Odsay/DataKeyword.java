package com.akj.helpyou.activities.Odsay;
// 자체 *.java 파일에서 파싱한 값을 필요한 값을 정리한 곳
public class DataKeyword {
    private String circle;
    private String startTraffic; // 승차 정류장/역 명
    private String endTraffic; // 하차 정류장/역 명
    private int moveTime; // 구간 별 이동시간
    private int stationCount; // 정차하는 정거장 수
  //  private int img;
    private int startcd; // 출발 정류장/역 코드
    private String passName; // 정차하는 정거장/역 상세 구간 이름
    private int wCode; // 1:상행 2:하행 3: 지하철아님(도보나 버스)
    private String Low; // 저상버스 유무 (Y/N)
    private int busTime; // 버스 도착 시간 (초)
    private String StartX;
    private String StartY;
    private String EndX;
    private String EndY;
    private String Start_tel; // 지하철 승차역 전화번호
    private String End_tel; // 지하철 하차역 전화번호
    private String BusID; // 버스 정류장 고유 ID

    public DataKeyword( String  circle, String startTraffic, String endTraffic, int moveTime, int stationCount, String busID, String passName,
                         int wCode, String startX, String startY, String endX, String endY, String low, int bustime, String start_tel, String end_tel){

        this.BusID=busID;
        this.circle = circle; // 도보 시 '도보', 버스시 버스 번호, 지하철시 호선 번호
        this.startTraffic = startTraffic; // 도보시 '도보 distance 값', 버스시 버스 첫 정거장, 지하철시 지하철 첫 역
        this.endTraffic = endTraffic; // 도보시 'null', 버스시 버스 마지막 정거장, 지하철시 지하철 마지막 역
        this.moveTime = moveTime; // sectiontime : 소요시간
        this.stationCount = stationCount; //버스, 지하철 시 각 정차 정거장/역 수
        this.startcd = startcd;
        this.passName = passName;
        this.wCode = wCode;
        this.StartX=startX;
        this.StartY=startY;
        this.EndX=endX;
        this.EndY=endY;
        this.Low=low;
        this.busTime=bustime;
        this.Start_tel=start_tel;
        this.End_tel=end_tel;
    }




    public String getBusID() {return this.BusID;}

    public String getStart_tel() {return this.Start_tel;}

    public String getEnd_tel() {return this.End_tel;}

    public String getcircle()
    {
        return this.circle;
    }

    public String getstartTraffic()
    {
        return this.startTraffic;
    }

    public String getendTraffic()
    {
        return this.endTraffic;
    }

    public int getmoveTime()
    {
        return this.moveTime;
    }

    public int getstationCount()
    {
        return this.stationCount;
    }

    public int getStartcd() {return this.startcd;}

    public String getPassName() {return this.passName;}

    public int getwCode() {return this.wCode;}

    public String getLow() {return this.Low;}

    public int getBusTime() {return this.busTime;}

    public String getStartX() {return this.StartX;}

    public String getStartY() {return this.StartY;}

    public String getEndX() {return this.EndX;}

    public String getEndY() {return this.EndY;}

    public String getjsonFormat(){
        return "{"+circle+","+ startTraffic +", "+ endTraffic +", "+ moveTime +", "+ "  정차하는 정거장 수 : " +stationCount
                 + "  출발지 좌표 : " + StartX + "  " + StartY+" 도착지 좌표 : " + EndX + "  " + EndY+ "저상버스유무 : "+ Low + "버스 도찻 시간 : "+ busTime+"}";
    }
}
