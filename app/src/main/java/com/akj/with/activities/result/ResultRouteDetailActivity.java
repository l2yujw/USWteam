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
import com.akj.with.activities.odsay.dto.BusDto;
import com.akj.with.activities.odsay.dto.SubwayDto;
import com.akj.with.activities.odsay.dto.WalkDto;
import com.akj.with.activities.result.adapter.ResultRouteDetail;
import com.akj.with.activities.result.adapter.ResultRouteDetailInner;
import com.akj.with.activities.result.adapter.ResultRouteDetailAdapter;
import com.akj.with.activities.findroad.Time;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class ResultRouteDetailActivity extends AppCompatActivity {
    private static List<WalkDto> walkDtos = new ArrayList<>();
    private static List<BusDto> busDtos = new ArrayList<>();
    private static List<SubwayDto> subwayDtos = new ArrayList<>();


    public static void walkResult(List<WalkDto> walkDtoList) {
        walkDtos = walkDtoList;
    }
    public static void busResult(List<BusDto> busDtoList) {
        busDtos = busDtoList;
    }
    public static void subwayResult(List<SubwayDto> subwayDtoList) {
        subwayDtos = subwayDtoList;
    }



    public static double[][] detailMapxy = new double[10][1000];
    public static int[] detailMtype = new int[10];
    public static int[] detailMcount = new int[10];

    public static void MapData(double[][] mapXy, int[] mType, int[] mCount) {
        detailMapxy = mapXy;
        detailMtype = mType;
        detailMcount = mCount;
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

        ResultRouteDetail walkRouteDetail = new ResultRouteDetail(
                "도보  ", startRoute + "  ", endRoute + "  ",
                walkDtos.get(position).getMoveTime() + "분  ", "           ", "", buildRouteDetailInnerList());
        routeDetailList.add(walkRouteDetail);

        ResultRouteDetail BusRouteDetail = new ResultRouteDetail(
                "버스  ", startRoute + "  ", endRoute + "  ",
                busDtos.get(position).getMoveTime() + "분  ", " 저상(Y/N) : " + busDtos.get(position).getLow() + "     ",
                "상세보기 ", buildRouteDetailInnerList());
        routeDetailList.add(BusRouteDetail);

        ResultRouteDetail subwayRouteDetail = new ResultRouteDetail(
                "지하철  ", startRoute + "  ", endRoute + "  ",
                subwayDtos.get(position).getMoveTime() + "분  ", "출발역 : " +
                subwayDtos.get(position).getStartTel() + "   \n도착역 : " + subwayDtos.get(position).getEndTel(),
                "      상세보기 ", buildRouteDetailInnerList());
        routeDetailList.add(subwayRouteDetail);

        return routeDetailList;
    }

    private List<ResultRouteDetailInner> buildRouteDetailInnerList() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        List<ResultRouteDetailInner> routeDetailInnerList = new ArrayList<>();

        ResultRouteDetailInner routeDetailInner = new ResultRouteDetailInner("end");

        routeDetailInnerList.add(routeDetailInner);

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