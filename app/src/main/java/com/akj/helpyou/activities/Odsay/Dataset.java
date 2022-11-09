package com.akj.helpyou.activities.Odsay;

import android.util.Log;

import java.util.ArrayList;

public class Dataset {
    ArrayList<DataKeyword> Data;

    // 도보시
    public int[][] distance; // 걷는 거리
    public int[][] walkSectionTime = {}; // 도보 이동 시간

    // 버스시
    public String[][] busNo; // 버스 번호
    public int[][] busSectionTime; // 버스 이동 시간
    public int[][] busArrivalTime; // 버스 실시간 도착 시간
    public String[][] startBusName; // 버스 출발 정류장 이름
    public String[][] endBusName; // 버스 하차 정류장 이름
    public String[][] startBusStationX; // 출발 X좌표
    public String[][] startBusStationY; // 출발 Y좌표
    public String[][] endBusStationX; // 도착 X좌표
    public String[][] endBusStationY; // 도착 Y좌표
    public String[][] busID; // 버스 정류장 고유 ID
    public int[][] busStationCount; // 정차 정류장 수
    public String[][] busLow; // 저상버스 유무

    // 지하철시
    public String[][] subwayNo; //지하철 호선
    public int[][] subwaySectionTime; // 지하철 이동 시간
    public String[][] startSubwayName; // 지하철 출발역 이름
    public String[][] endSubwayName; // 지하철 하차역 이름
    public String[][] startSubwayStationX; // 출발 X좌표
    public String[][] startSubwayStationY; // 출발 Y좌표
    public String[][] endSubwayStationX; // 도착 X좌표
    public String[][] endSubwayStationY; // 도착 Y좌표
    public int[][] subwayStationCount; // 정차 정류장 수
    public int[][] subwayWaycode; // 1:상행 2:하행
    public String[][] startSubwayTel; // 출발역 전화번호
    public String[][] endSubwayTel; // 도착역 전화번호

    public Dataset(ArrayList<DataKeyword> data, int j, int i, int k, int trafficType) {
        Data = data;
        if (trafficType == 3) { // 도보
            Log.d("mmm", "qqq : " + (trafficType == 3));
            Log.d("mmm", "ttt : " +data.get(k).getmoveTime());
            this.walkSectionTime[j][i] = data.get(k).getmoveTime();
            Log.d("mmm", "ttt : " + getWalkSectionTime(j,i));
            this.distance[j][i] = Integer.parseInt(data.get(k).getstartTraffic());
            Log.d("mmm", "ttt : " + distance[j][i]);

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

    public String getBusNo(int j, int i) {return this.busNo[j][i];}
    public int[][] getBusSectionTime() {return this.busSectionTime;}
    public int getBusArrivalTime(int i, int j) {return this.busArrivalTime[j][i];}
    public String[][] getStartBusName() {return this.startBusName;}
    public String[][] getEndBusName() {return this.endBusName;}
    public String[][] getStartBusStationX() {return this.endBusStationX;}
    public String[][] getStartBusStationY() {return this.endBusStationY;}
    public String[][] getEndBusStationX() {return this.endBusStationX;}
    public String[][] getEndBusStationY() {return this.endBusStationY;}
    public String[][] getBusID() {return this.busID;}
    public int[][] getBusStationCount() {return this.busStationCount;}
    public String[][] getBusLow() {return this.busLow;}

    public String[][] getSubwayNo() {return this.subwayNo;}
    public int[][] getSubwaySectionTime() {return this.subwaySectionTime;}
    public String[][] getStartSubwayName() {return this.startSubwayName;}
    public String[][] getEndSubwayName() {return this.endSubwayName;}
    public String[][] getStartSubwayStationX() {return this.startSubwayStationX;}
    public String[][] getStartSubwayStationY() {return this.startSubwayStationY;}
    public String[][] getEndSubwayStationX() {return this.endSubwayStationX;}
    public String[][] getEndSubwayStationY() {return this.endSubwayStationY;}
    public int[][] getSubwayStationCount() {return this.subwayStationCount;}
    public int[][] getSubwayWaycode() {return this.subwayWaycode;}
    public String[][] getStartSubwayTel() {return this.startSubwayTel;}
    public String[][] getEndSubwayTel() {return this.endSubwayTel;}

}
