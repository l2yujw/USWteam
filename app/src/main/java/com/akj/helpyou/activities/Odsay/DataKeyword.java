package com.akj.helpyou.activities.Odsay;
// 자체 *.java 파일에서 파싱한 값을 필요한 값을 정리한 곳
public class DataKeyword {
    private String circle;
    private String startTraffic; // 승차 정류장/역 명
    private String endTraffic; // 하차 정류장/역 명
    private String moveTime; // 구간 별 이동시간
    private String stationCount; // 정차하는 정거장 수
  //  private int img;
    private int startcd; // 출발 정류장/역 코드
    private String passName; // 정차하는 정거장/역 상세 구간 이름
    private int wCode; // 1:상행 2:하행 3: 지하철아님(도보나 버스)
    private String lowbus; // 저상버스 유무, (Y/N)
    private int sec;
    private String StartX;
    private String StartY;
    private String EndX;
    private String EndY;


    public DataKeyword( String  circle, String startTraffic, String endTraffic, String moveTime, String stationCount, int startcd, String passName,
                         int wCode, String lowbus, int sec, String startX, String startY, String endX, String endY){
       // this.img = img;
        this.circle = circle; // 도보 시 '도보', 버스시 버스 번호, 지하철시 호선 번호
        this.startTraffic = startTraffic; // 도보시 '도보 distance 값', 버스시 버스 첫 정거장, 지하철시 지하철 첫 역
        this.endTraffic = endTraffic; // 도보시 'null', 버스시 버스 마지막 정거장, 지하철시 지하철 마지막 역
        this.moveTime = moveTime; // sectiontime : 소요시간
        this.stationCount = stationCount; //버스, 지하철 시 각 정차 정거장/역 수
        this.startcd = startcd;
        this.passName = passName;
        this.wCode = wCode;
        this.lowbus = lowbus;
        this.sec = sec;
        this.StartX=startX;
        this.StartY=startY;
        this.EndX=endX;
        this.EndY=endY;
    }

//    public int getImg()
//    {
//        return this.img;
//    }

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

    public String getmoveTime()
    {
        return this.moveTime;
    }

    public String getstationCount()
    {
        return this.stationCount;
    }

    public int getStartcd() {return this.startcd;}

    public String getPassName() {return this.passName;}

    public int getwCode() {return this.wCode;}

    public String getLowbus() {return this.lowbus;}

    public int getSec() {return this.sec;}

    public String getStartX() {return this.StartX;}

    public String getStartY() {return this.StartY;}

    public String getEndX() {return this.EndX;}

    public String getEndY() {return this.EndY;}

    public String getjsonFormat(){
        return "{"+circle+","+"승차 정거장/역 : "+ startTraffic +","+ "하차 정거장/역 : "+ endTraffic +","+ moveTime +","+ "  정차하는 정거장 수 : " +stationCount + "버스면 버스 도착 시간 : " +
                sec + "   버스면 저상버스 유무(Y/N)" + lowbus + "  출발지 좌표 : " + StartX + "  " + StartY+" 도착지 좌표 : " + EndX + "  " + EndY+"}";
    }
}
