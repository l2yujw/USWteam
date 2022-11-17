package com.akj.helpyou.activities.Odsay;

import java.util.ArrayList;

public class Dataset {
    ArrayList<DataKeyword> Data;

    // 도보시
    public int[][] distance = new int[5][20]; // 걷는 거리
    public int[][] walkSectionTime = new int[5][20]; // 도보 이동 시간
    public int[][] totalTime = new int[5][20];
    public String[][] totalFee = new String[5][20];

    // 버스시
    public String[][] busNo = new String[5][20]; // 버스 번호
    public int[][] busSectionTime = new int[5][20]; // 버스 이동 시간
    public int[][] busArrivalTime = new int[5][20];; // 버스 실시간 도착 시간
    public String[][] startBusName = new String[5][20]; // 버스 출발 정류장 이름
    public String[][] endBusName = new String[5][20]; // 버스 하차 정류장 이름
    public String[][] startBusStationX = new String[5][20]; // 출발 X좌표
    public String[][] startBusStationY = new String[5][20]; // 출발 Y좌표
    public String[][] endBusStationX = new String[5][20]; // 도착 X좌표
    public String[][] endBusStationY = new String[5][20]; // 도착 Y좌표
    public String[][] busID = new String[5][20]; // 버스 정류장 고유 ID
    public int[][] busStationCount = new int[5][20];; // 정차 정류장 수
    public String[][] busLow = new String[5][20]; // 저상버스 유무

    // 지하철시
    public String[][] subwayNo = new String[5][20]; //지하철 호선
    public int[][] subwaySectionTime = new int[5][20]; // 지하철 이동 시간
    public String[][] startSubwayName = new String[5][20]; // 지하철 출발역 이름
    public String[][] endSubwayName = new String[5][20]; // 지하철 하차역 이름
    public String[][] startSubwayStationX = new String[5][20]; // 출발 X좌표
    public String[][] startSubwayStationY = new String[5][20]; // 출발 Y좌표
    public String[][] endSubwayStationX = new String[5][20]; // 도착 X좌표
    public String[][] endSubwayStationY = new String[5][20]; // 도착 Y좌표
    public int[][] subwayStationCount = new int[5][20];; // 정차 정류장 수
    public int[][] subwayWaycode = new int[5][20];; // 1:상행 2:하행
    public String[][] startSubwayTel = new String[5][20]; // 출발역 전화번호
    public String[][] endSubwayTel = new String[5][20]; // 도착역 전화번호

    public Dataset(ArrayList<DataKeyword> data, int j, int i, int k, int trafficType) {
        Data = data;
        if (trafficType == 3) { // 도보

            this.totalTime[j][i] = Integer.parseInt(data.get(k).getStartX());
            this.totalFee[j][i] = data.get(k).getStartY();
            this.walkSectionTime[j][i] = data.get(k).getmoveTime();
            this.distance[j][i] = Integer.parseInt(data.get(k).getstartTraffic());

        }
        if (trafficType == 2 ) { // 버스
            this.busNo[j][i] = data.get(k).getcircle();
            this.busSectionTime[j][i] = data.get(k).getmoveTime();
            this.busArrivalTime[j][i] = data.get(k).getBusTime();
            this.startBusName[j][i] = data.get(k).getstartTraffic();
            this.endBusName[j][i] = data.get(k).getendTraffic();
            this.startBusStationX[j][i] = data.get(k).getStartX();
            this.startBusStationY[j][i] = data.get(k).getStartY();
            this.endBusStationX[j][i] = data.get(k).getEndX();
            this.endBusStationY[j][i] = data.get(k).getEndY();
            this.busStationCount[j][i] = data.get(k).getstationCount();
            this.busLow[j][i] = data.get(k).getLow();
            this.busID[j][i] = data.get(k).getBusID();
        }
        if (trafficType == 1 ){ //지하철
            this.subwayNo[j][i] = data.get(k).getcircle();
            this.subwaySectionTime[j][i] = data.get(k).getmoveTime();
            this.startSubwayName[j][i] = data.get(k).getstartTraffic();
            this.endSubwayName[j][i] = data.get(k).getendTraffic();
            this.startSubwayStationX[j][i] = data.get(k).getStartX();
            this.startSubwayStationY[j][i] = data.get(k).getStartY();
            this.endSubwayStationX[j][i] = data.get(k).getEndX();
            this.endSubwayStationY[j][i] = data.get(k).getEndY();
            this.subwayStationCount[j][i] = data.get(k).getstationCount();
            this.subwayWaycode[j][i] = data.get(k).getwCode();
            this.startSubwayTel[j][i] = data.get(k).getStart_tel();
            this.endSubwayTel[j][i]= data.get(k).getEnd_tel();
        }

    }
    public int getDistance(int j, int i) {return this.distance[j][i];}
    public int getWalkSectionTime(int j, int i) {return this.walkSectionTime[j][i];}
    public int gettotalTime(int j, int i) {return this.totalTime[j][i];}
    public String gettotalFee(int j, int i) {return this.totalFee[j][i];}

    public String getBusNo(int j, int i) {return this.busNo[j][i];}
    public int getBusSectionTime(int j, int i) {return this.busSectionTime[j][i];}
    public int getBusArrivalTime(int j, int i) {return this.busArrivalTime[j][i];}
    public String getStartBusName(int j, int i) {return this.startBusName[j][i];}
    public String getEndBusName(int j, int i) {return this.endBusName[j][i];}
    public String getStartBusStationX(int j, int i) {return this.endBusStationX[j][i];}
    public String getStartBusStationY(int j, int i) {return this.endBusStationY[j][i];}
    public String getEndBusStationX(int j, int i) {return this.endBusStationX[j][i];}
    public String getEndBusStationY(int j, int i) {return this.endBusStationY[j][i];}
    public String getBusID(int j, int i) {return this.busID[j][i];}
    public int getBusStationCount(int j, int i) {return this.busStationCount[j][i];}
    public String getBusLow(int j, int i) {return this.busLow[j][i];}

    public String getSubwayNo(int j, int i) {return this.subwayNo[j][i];}
    public int getSubwaySectionTime(int j, int i) {return this.subwaySectionTime[j][i];}
    public String getStartSubwayName(int j, int i) {return this.startSubwayName[j][i];}
    public String getEndSubwayName(int j, int i) {return this.endSubwayName[j][i];}
    public String getStartSubwayStationX(int j, int i) {return this.startSubwayStationX[j][i];}
    public String getStartSubwayStationY(int j, int i) {return this.startSubwayStationY[j][i];}
    public String getEndSubwayStationX(int j, int i) {return this.endSubwayStationX[j][i];}
    public String getEndSubwayStationY(int j, int i) {return this.endSubwayStationY[j][i];}
    public int getSubwayStationCount(int j, int i) {return this.subwayStationCount[j][i];}
    public int getSubwayWaycode(int j, int i) {return this.subwayWaycode[j][i];}
    public String getStartSubwayTel(int j, int i) {return this.startSubwayTel[j][i];}
    public String getEndSubwayTel(int j, int i) {return this.endSubwayTel[j][i];}

}
