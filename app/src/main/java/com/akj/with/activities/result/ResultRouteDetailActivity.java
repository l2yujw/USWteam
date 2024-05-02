package com.akj.with.activities.result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.akj.with.R;
import com.akj.with.activities.result.adapter.ResultRouteDetail;
import com.akj.with.activities.result.adapter.ResultRouteDetailInner;
import com.akj.with.activities.result.adapter.ResultRouteDetailAdapter;
import com.akj.with.activities.findroad.Time;
import com.akj.with.activities.odsay.DataKeyword;
import com.akj.with.activities.odsay.Dataset;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class ResultRouteDetailActivity extends AppCompatActivity {
    public static int[] resi = new int[20];
    public static int resj = 0;
    public static int resjj = 0;
    public static String start;
    public static String[][] test = new String[5][20];
    public static int[][] traffic = new int[5][20];
    public static int[][] totalTime = new int[5][1];
    public static String[][] totalFee = new String[5][1];
    public static String[][] trafficNum = new String[5][20];
    public static String[][] startName = new String[5][20];
    public static Time time = new Time();


    //도보시
    public static String[][] distance = new String[5][20]; // 걷는 거리
    public static int[][] walkSectionTime = new int[5][20]; // 도보 이동 시간

    //버스
    public static int[][] busSectionTime = new int[5][20]; // 버스 이동 시간
    public static int[][] busArrivalTime = new int[5][20];
    ; // 버스 실시간 도착 시간
    public static String[][] endName = new String[5][20]; // 버스 하차 정류장 이름
    public static String[][] busID = new String[5][20]; // 버스 정류장 고유 ID
    public static int[][] busStationCount = new int[5][20];
    ; // 정차 정류장 수
    public static String[][] busLow = new String[5][20]; // 저상버스 유무

    //지하철
    public static int[][] subwaySectionTime = new int[5][20]; // 지하철 이동 시간
    public static int[][] subwayStationCount = new int[5][20];
    ; // 정차 정류장 수
    public static int[][] subwayWaycode = new int[5][20];
    ; // 1:상행 2:하행
    public static String[][] startSubwayTel = new String[5][20]; // 출발역 전화번호
    public static String[][] endSubwayTel = new String[5][20]; // 도착역 전화번호
    public static int[][] count = new int[5][1000];
    // 지하철or 버스 경유 정차명
    public static String[][][] passName = new String[5][20][100];  // [j][i][z] j/i는 그전에 썻던 그대로 위치값 z는 경유 정차 갯수
    // ex) [j][i][0]~[j][i][k]는 [j][i]가 버스일때 해당 버스 출발지부터 도착지까지의 경유 정류장이름이 들어가있음
    // 쓸 때 버스랑 지하철일 경우(if문)만 for문으로 z=0; z<100; z++하고 if(passName[j][i][z] !=null) 이면 passName[j][i][z] 호출 <<하면됄듯

//    public static String startRoute;
//    public static String endRoute;

    public static int i;

    public static void resdata2(ArrayList<DataKeyword> data, int j, int i, int k, int trafficType, String[][][] Pass, int[][] count) {
        Dataset dataset = new Dataset(data, j, i, k, trafficType);
        ResultRouteDetailActivity.count[j][i] = count[j][i];
        passName = Pass;
        for (int z = 0; z < 100; z++) {
            if (passName[j][i][z] != null) {
                Log.d("aassdd", "passName" + passName[j][i][z]);
            }
        }
        Log.d("aassdd", "j / i : " + j + " " + i);
        if (i == 0) {
            totalFee[j][0] = dataset.gettotalFee(j, i);
            totalTime[j][0] = dataset.gettotalTime(j, i);
            Log.d("hkhk", "totalFee : " + totalFee[j][0]);
            Log.d("hkhk", "totalTime : " + totalTime[j][0]);
        }
        traffic[j][i] = trafficType;

        resj = j;
        resi[j] = i;
        Log.d("ddd", "dd" + resi[j] + " " + resj);
        if (traffic[j][i] == 3) {  // 도보값만 저장
            //test[resj][resi] = dataset.getDistance(resj, resi);
            // 검색출발지
            distance[j][i] = String.valueOf(dataset.getDistance(j, i));
            walkSectionTime[j][i] = dataset.getWalkSectionTime(j, i);

        }
        if (traffic[j][i] == 2) {  // 버스값만 저장
            // 이동수단 번호
            startName[j][i] = dataset.getStartBusName(j, i);
            trafficNum[j][i] = dataset.getBusNo(j, i);
            Log.d("kkk", "startName : " + startName[j][i] + " resj : " + j);
            Log.d("kkk", "trafficNum : " + trafficNum[j][i] + " resi[j] : " + i);
            busSectionTime[j][i] = dataset.getBusSectionTime(j, i);
            busArrivalTime[j][i] = dataset.getBusArrivalTime(j, i);
            endName[j][i] = dataset.getEndBusName(j, i);
            busID[j][i] = dataset.getBusID(j, i);
            busStationCount[j][i] = dataset.getBusStationCount(j, i);
            busLow[j][i] = dataset.getBusLow(j, i);
        }
        if (traffic[j][i] == 1) {  // 지하철 값만 저장
            //이동수단 번호
            Log.d("kkk", "startName : " + startName[j][i] + " resj : " + resj);
            Log.d("kkk", "trafficNum : " + trafficNum[j][i] + " resi[j] : " + resi[j]);
            startName[j][i] = dataset.getStartSubwayName(j, i);
            trafficNum[j][i] = dataset.getSubwayNo(j, i);

            subwaySectionTime[j][i] = dataset.getSubwaySectionTime(j, i);
            endName[j][i] = dataset.getEndSubwayName(j, i);
            subwayStationCount[j][i] = dataset.getSubwayStationCount(j, i);
            startSubwayTel[j][i] = dataset.getStartSubwayTel(j, i);
            endSubwayTel[j][i] = dataset.getEndSubwayTel(j, i);
            subwayWaycode[j][i] = dataset.getSubwayWaycode(j, i);
        }
    }

    public static double[][] detailMapxy = new double[10][1000];
    public static int[] detailMtype = new int[10];
    public static int[] detailMcount = new int[10];

    public static void MapData(double[][] mapxy, int[] MType, int[] MCount) {
        detailMapxy = mapxy;
        detailMtype = MType;
        detailMcount = MCount;

        for(int i=0; i<30; i++){
            Log.d("hjhjhj","type : " + detailMtype[i]);
            Log.d("hjhjhj","count : " + detailMcount[i]);
            for(int j=0; j<MCount[i]; j++){
                Log.d("hjhjhj","xy : " + detailMapxy[i][j]);
            }
        }
    }

    private MapView mapView;
    private ViewGroup mapViewContainer;
    private MapPolyline mapPolyline;
    private MapPoint[] mapPoint;
    private double lx, ly;
    private int geocodeSize = 0;

    private MapPolyline mapPolyline0;
    private MapPolyline mapPolyline1;
    private MapPolyline mapPolyline2;
    private MapPolyline mapPolyline3;
    private MapPolyline mapPolyline4;
    private MapPolyline mapPolyline5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_route_detail);

        double startX = 37.60518;
        double startY = 127.026027;
        MapPoint centerPoint = MapPoint.mapPointWithGeoCoord(startX, startY);

        mapView = new MapView(this);
        mapViewContainer = findViewById(R.id.framelayout_result_route_detail_map);
        mapViewContainer.addView(mapView);
        mapView.setMapCenterPointAndZoomLevel(centerPoint, 4, true);

        mapDraw(detailMapxy, detailMtype, detailMcount);

        RecyclerView rv = findViewById(R.id.rv_result_route_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResultRouteDetailActivity.this);
        ResultRouteDetailAdapter adapter = new ResultRouteDetailAdapter(buildRouteDetailList());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
    }

    private List<ResultRouteDetail> buildRouteDetailList() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        List<ResultRouteDetail> routeDetailList = new ArrayList<>();
        String startRoute = intent.getStringExtra("startRoute");
        String endRoute = intent.getStringExtra("endRoute");

        for (i = 0; i < resi[position] + 1; i++) {
            if (traffic[position][i] == 3) {  // 도보값만 저장
                // 검색출발지
                if (i == 0) {
                    endName[position][i] = startName[position][i + 1];
                    ResultRouteDetail routeDetail = new ResultRouteDetail(
                            "도보  ", startRoute + "  ", endName[position][i] + "  ",
                            walkSectionTime[position][i] + "분  ", "           ", "", buildRouteDetailInnerList());
                    routeDetailList.add(routeDetail);
                } else if (i == resi[position]) {
                    startName[position][i] = endName[position][i - 1];
                    ResultRouteDetail routeDetail = new ResultRouteDetail(
                            "도보  ", startName[position][i] + "  ", endRoute + "  ",
                            walkSectionTime[position][i] + "분  ", "           ", "", buildRouteDetailInnerList());
                    routeDetailList.add(routeDetail);
                } else {
                    startName[position][i] = endName[position][i - 1];
                    ResultRouteDetail routeDetail = new ResultRouteDetail("도보  ", startName[position][i] + "  ", endName[position][i] + "  ",
                            walkSectionTime[position][i] + "분  ", " ", " ", buildRouteDetailInnerList());
                    routeDetailList.add(routeDetail);
                }
            }
            if (traffic[position][i] == 2) {  // 버스값만 저장
                // 이동수단 번호
                ResultRouteDetail routeDetail = new ResultRouteDetail("버스  ", startName[position][i] + "  ", endName[position][i] + "  ",
                        busSectionTime[position][i] + "분  ", " 저상(Y/N) : "+busLow[position][i]+ "     ", "상세보기 ", buildRouteDetailInnerList());
                routeDetailList.add(routeDetail);
            }
            if (traffic[position][i] == 1) {  // 지하철 값만 저장
                //이동수단 번호
                ResultRouteDetail routeDetail = new ResultRouteDetail("지하철  ", startName[position][i] + "  ", endName[position][i] + "  ",
                        subwaySectionTime[position][i] + "분  ", "출발역 : " + startSubwayTel[position][i] + "   \n도착역 : " + endSubwayTel[position][i],
                        "      상세보기 ", buildRouteDetailInnerList());
                routeDetailList.add(routeDetail);
            }
        }
        return routeDetailList;
    }

    private List<ResultRouteDetailInner> buildRouteDetailInnerList() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        List<ResultRouteDetailInner> routeDetailInnerList = new ArrayList<>();
        if (traffic[position][i] != 3) {
            for (int z = 0; z < count[position][i]; z++) {
                ResultRouteDetailInner routeDetailInner = new ResultRouteDetailInner(passName[position][i][z]);
                routeDetailInnerList.add(routeDetailInner);
            }
        }

        return routeDetailInnerList;
    }

    private void mapDraw(double[][] detailMapxy, int[] detailMtype, int[] detailMcount) {
        for (int i = 0; i < detailMtype.length; i++) {
            geocodeSize = detailMcount[i] / 2;
            mapPoint = new MapPoint[geocodeSize];
            Log.e("detail", "detailMtype[" + i + "] : " + detailMtype[i]);
            if (detailMtype[i] == 1) {
                // 지하철
                Log.e("subway", "지하철 실행");
                mapPoint[i] = null;
                for (int j = 0; j < geocodeSize; j++) {
                    lx = detailMapxy[i][j * 2 + 1];
                    ly = detailMapxy[i][j * 2];
                    mapPoint[j] = MapPoint.mapPointWithGeoCoord(lx, ly);
                }
                makePolyline(mapPoint, i);
                Log.e("x", "mapPoint[0][0]" + mapPoint[0].getMapPointGeoCoord().latitude);
                Log.e("y", "mapPoint[0][1]" + mapPoint[0].getMapPointGeoCoord().longitude);
            } else if (detailMtype[i] == 2) {
                // 버스
                Log.e("subway", "버스 실행");
                mapPoint[i] = null;
                for (int j = 0; j < geocodeSize; j++) {
                    lx = detailMapxy[i][j * 2 + 1];
                    ly = detailMapxy[i][j * 2];
                    mapPoint[j] = MapPoint.mapPointWithGeoCoord(lx, ly);
                }
                makePolyline(mapPoint, i);
                Log.e("x", "mapPoint[0][0]" + mapPoint[0].getMapPointGeoCoord().latitude);
                Log.e("y", "mapPoint[0][1]" + mapPoint[0].getMapPointGeoCoord().longitude);
            }
        }
    }

    private void makePolyline(MapPoint[] mapPoint, int count) {
        switch (count) {
            case 0:
                Log.e("polyline", "poly0");
                mapPolyline0 = new MapPolyline();
                mapPolyline0.setLineColor(Color.argb(255, 255, 0, 0));
                mapPolyline0.addPoints(mapPoint);
                mapView.addPolyline(mapPolyline0);
                break;
            case 1:
                Log.e("polyline", "poly1");
                mapPolyline1 = new MapPolyline();
                mapPolyline1.setLineColor(Color.argb(255, 0, 255, 0));
                mapPolyline1.addPoints(mapPoint);
                mapView.addPolyline(mapPolyline1);
                break;
            case 2:
                Log.e("polyline", "poly2");
                mapPolyline2 = new MapPolyline();
                mapPolyline2.setLineColor(Color.argb(255, 0, 0, 255));
                mapPolyline2.addPoints(mapPoint);
                mapView.addPolyline(mapPolyline2);
                break;
            case 3:
                Log.e("polyline", "poly3");
                mapPolyline3 = new MapPolyline();
                mapPolyline3.setLineColor(Color.argb(255, 255, 255, 0));
                mapPolyline3.addPoints(mapPoint);
                mapView.addPolyline(mapPolyline3);
                break;
            case 4:
                Log.e("polyline", "poly4");
                mapPolyline4 = new MapPolyline();
                mapPolyline4.setLineColor(Color.argb(255, 0, 0, 0));
                mapPolyline4.addPoints(mapPoint);
                mapView.addPolyline(mapPolyline4);
                break;
            default:
                Log.e("polyline", "poly5");
                mapPolyline5 = new MapPolyline();
                mapPolyline5.setLineColor(Color.argb(255, 255, 0, 0));
                mapPolyline5.addPoints(mapPoint);
                mapView.addPolyline(mapPolyline5);
                break;
        }
    }

    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들)
}