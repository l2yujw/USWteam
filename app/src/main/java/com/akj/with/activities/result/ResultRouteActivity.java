package com.akj.with.activities.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.with.R;
import com.akj.with.activities.odsay.dto.BusDto;
import com.akj.with.activities.odsay.dto.SubwayDto;
import com.akj.with.activities.odsay.dto.WalkDto;
import com.akj.with.activities.result.adapter.ResultRoute;
import com.akj.with.activities.result.adapter.ResultRouteInner;
import com.akj.with.activities.result.adapter.ResultRouteAdapter;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 스레드 스타트
public class ResultRouteActivity extends AppCompatActivity {
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
    public static double [][][]Mapxy = new double[5][10][1000];
    public static int [][] Mtype = new int[5][30];
    public static int [][] MCount = new int[5][30];

    public static void MapLineData (double [][][]MapXY, int [][]type, int [][]count, int j, int i){

        Mtype[j][i] = type[j][i];
        MCount[j][i] = count[j][i];
        Mapxy[j][i] = MapXY[j][i];


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_route);

        RecyclerView rv = findViewById(R.id.rv_result_route_external);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResultRouteActivity.this);
        ResultRouteAdapter adapter = new ResultRouteAdapter(buildRouteList());

        adapterClick(adapter);

        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);

    }//adapter 클릭시 detail로 이동 필요 값들을 미리 넣어줌

    private List<ResultRoute> buildRouteList() {
        String today;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        List<ResultRoute> routeList = new ArrayList<>();
        cal.add(Calendar.MINUTE, 10);
        today = sdf.format(cal.getTime());
        ResultRoute route = new ResultRoute(Integer.toString(10), today, "10", buildRouteInnerList());
        routeList.add(route);

        return routeList;
    }

    static String startRoute;
    static String endRoute;

    private List<ResultRouteInner> buildRouteInnerList() {
        Intent intent = getIntent();
        startRoute = intent.getStringExtra("startText1");
        endRoute = intent.getStringExtra("endText1");
        List<ResultRouteInner> routeInnerList = new ArrayList<>();

        ResultRouteInner walkRouteInner = new ResultRouteInner("도보", startRoute, R.drawable.ic_outline_directions_walk_24);
        routeInnerList.add(walkRouteInner);

        ResultRouteInner subwayRouteInner = new ResultRouteInner(subwayDtos.get(0).getPassName(), subwayDtos.get(0).getPassName(), R.drawable.ic_outline_subway_24);
        routeInnerList.add(subwayRouteInner);

        return routeInnerList;
    }

    private void adapterClick(ResultRouteAdapter adapter) {
        adapter.setOnItemClickListener((v, position) -> {
            ResultRouteDetailActivity.MapData(Mapxy[position], Mtype[position], MCount[position]);
            //MapLine(Mapxy[position], Mtype[position], MCount[position]); 받을때 Mapxy[][], type[], count[] 이렇게 받으면됌
            // mapxy, type은 저번에 메모장에 적은 그대로 하면 되고 count의 경우 x,y좌표 길이임. count[] < 인덱스도 type이랑 mapxy[] <이랑 같음

            Intent intent = new Intent(getApplicationContext(), ResultRouteDetailActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("startRoute", startRoute);
            intent.putExtra("endRoute", endRoute);
            startActivity(intent);
        });
    }
}