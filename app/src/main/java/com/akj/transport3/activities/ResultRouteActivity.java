package com.akj.transport3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.transport3.R;
import com.akj.transport3.activities.FindRoad.Time;
import com.akj.transport3.activities.Odsay.DataKeyword;
import com.akj.transport3.activities.Odsay.Dataset;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 스레드 스타트
public class ResultRouteActivity extends AppCompatActivity {
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
    public static int[][] busArrivalTime = new int[5][20];; // 버스 실시간 도착 시간
    public static String[][] endName = new String[5][20]; // 버스 하차 정류장 이름
    public static String[][] busID = new String[5][20]; // 버스 정류장 고유 ID
    public static int[][] busStationCount = new int[5][20];; // 정차 정류장 수
    public static String[][] busLow = new String[5][20]; // 저상버스 유무

    //지하철
    public static int[][] subwaySectionTime = new int[5][20]; // 지하철 이동 시간
    public static int[][] subwayStationCount = new int[5][20];; // 정차 정류장 수
    public static int[][] subwayWaycode = new int[5][20];; // 1:상행 2:하행
    public static String[][] startSubwayTel = new String[5][20]; // 출발역 전화번호
    public static String[][] endSubwayTel = new String[5][20]; // 도착역 전화번호

    public static String startRoute;
    public static String endRoute;

    public static String[] MapObj = new String[10];
    public static int[] Type = new int[10];
    public static int z=0;
    public static int p=0;

    public static void resdata (ArrayList<DataKeyword> data, int j, int i, int k, int trafficType){
        Dataset dataset = new Dataset(data, j, i, k, trafficType);


        if(i==0) {
            totalFee[j][0] = dataset.gettotalFee(j,i);
            totalTime[j][0] = dataset.gettotalTime(j,i);
            Log.d("hkhk","totalFee : " + totalFee[j][0]);
            Log.d("hkhk","totalTime : " + totalTime[j][0]);
        }
        traffic[j][i] = trafficType;

        resj = j;
        resi[j] = i;
        Log.d("ddd","dd" + resi[j] + " " + resj);
        if(traffic[j][i] == 3) {  // 도보값만 저장
            //test[resj][resi] = dataset.getDistance(resj, resi);
            // 검색출발지
            distance[j][i] = String.valueOf(dataset.getDistance(j,i));
            walkSectionTime[j][i] = dataset.getWalkSectionTime(j,i);

        }
        if(traffic[j][i] == 2) {  // 버스값만 저장
            // 이동수단 번호
            startName[j][i] = dataset.getStartBusName(j,i);
            trafficNum[j][i] = dataset.getBusNo(j,i);
            Log.d("kkk", "startName : " + startName[j][i] +" resj : " + j);
            Log.d("kkk", "trafficNum : " + trafficNum[j][i] + " resi[j] : " + i);
            busSectionTime[j][i] = dataset.getBusSectionTime(j,i);
            busArrivalTime[j][i] = dataset.getBusArrivalTime(j,i);
            endName[j][i] = dataset.getEndBusName(j,i);
            busID[j][i] = dataset.getBusID(j,i);
            busStationCount[j][i] = dataset.getBusStationCount(j,i);
            busLow[j][i] = dataset.getBusID(j,i);
        }
        if(traffic[j][i] == 1) {  // 지하철 값만 저장
            //이동수단 번호
            Log.d("kkk", "startName : " + startName[j][i] +" resj : " + resj);
            Log.d("kkk", "trafficNum : " + trafficNum[j][i] + " resi[j] : " + resi[j]);
            startName[j][i] = dataset.getStartSubwayName(j,i);
            trafficNum[j][i] = dataset.getSubwayNo(j,i);

            subwaySectionTime[j][i] = dataset.getSubwaySectionTime(j,i);
            endName[j][i] = dataset.getEndSubwayName(j,i);
            subwayStationCount[j][i] = dataset.getSubwayStationCount(j,i);
            startSubwayTel[j][i] = dataset.getStartSubwayTel(j,i);
            endSubwayTel[j][i] = dataset.getEndSubwayTel(j,i);
            subwayWaycode[j][i] = dataset.getSubwayWaycode(j,i);
        }
    }
    public static double [][][]Mapxy = new double[10][10][1000];
    public static int [][] Mtype = new int[10][10];
    public static int [][] MCount = new int[10][10];
    public static int n = 0;
    public static void MapLineData (double [][][]MapXY, int [][]type, int [][]count, int j, int i){


        Mapxy[j][n] = MapXY[j][i];
        Mtype[j][n] = type[j][i];
        MCount[j][n] = count[j][i];

        for(int l=0; l<MCount[j][n]; l++){
            Mapxy[j][n][l] = MapXY[j][i][l];
        }

        n++;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_road);

        RecyclerView rvInf = findViewById(R.id.recyclerView_inf);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResultRouteActivity.this);
        InfAdapter infAdapter = new InfAdapter(buildInfList());
        infAdapter.setOnItemClickListener(new InfAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                ResultRouteDetailActivity.MapData(Mapxy[position], Mtype[position], MCount[position]);
                //MapLine(Mapxy[position], Mtype[position], MCount[position]); 받을때 Mapxy[][], type[], count[] 이렇게 받으면됌
                // mapxy, type은 저번에 메모장에 적은 그대로 하면 되고 count의 경우 x,y좌표 길이임. count[] < 인덱스도 type이랑 mapxy[] <이랑 같음

               Intent intent = new Intent(getApplicationContext(), ResultRouteDetailActivity.class);
                //눌린 포지션 전송
                intent.putExtra("position", position);
                intent.putExtra("startRoute", startRoute);
                intent.putExtra("endRoute", endRoute);
                Log.d("posss"," "+position);
                startActivity(intent);
            }
        });
        rvInf.setAdapter(infAdapter);
        rvInf.setLayoutManager(layoutManager);

    }//adapter 클릭시 detail로 이동 필요 값들을 미리 넣어줌

    private List<Inf> buildInfList() {
        String today = null;
        String[][] resultTime = new String[5][1];
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        List<Inf> infList = new ArrayList<>();
        for (int j=0; j<resj; j++) {
            cal.add(Calendar.MINUTE, totalTime[j][0]);
            today = sdformat.format(cal.getTime());
            Inf inf = new Inf( Integer.toString(totalTime[j][0]), today, totalFee[j][0], buildInf2List());
            infList.add(inf);
        }
        return infList;
    }//dddd

    private List<Inf2> buildInf2List() {
        Intent intent = getIntent();
        startRoute = intent.getStringExtra("startText1");
        endRoute = intent.getStringExtra("endText1");
        Log.d("www"," " + startRoute);
        List<Inf2> inf2List = new ArrayList<>();

            for (int i=0; i<resi[resjj]+1; i++) {
                if(traffic[resjj][i] == 3) {  // 도보값만 저장
                    // 검색출발지
                    if(i == 0){
                        Inf2 inf2 = new Inf2("도보", startRoute, R.drawable.ic_outline_directions_walk_24);
                        inf2List.add(inf2);
                    }
                    else{
                        startName[resjj][i] = endName[resjj][i-1];
                        Inf2 inf2 = new Inf2("도보", startName[resjj][i],R.drawable.ic_outline_directions_walk_24);
                        inf2List.add(inf2);
                    }
                }
                if(traffic[resjj][i] == 2) {  // 버스값만 저장
                    // 이동수단 번호
                    Inf2 inf2 = new Inf2(trafficNum[resjj][i], startName[resjj][i], R.drawable.ic_outline_directions_bus_24);
                    inf2List.add(inf2);
                }
                if(traffic[resjj][i] == 1) {  // 지하철 값만 저장
                    //이동수단 번호
                    Inf2 inf2 = new Inf2(trafficNum[resjj][i], startName[resjj][i], R.drawable.ic_outline_subway_24);
                    inf2List.add(inf2);
                }
            }
            resjj++;
        return inf2List;
    }
}//content에 recyclerview 적용 아이템 생성 text적용 그 안에 recyclerview 적용 아이템 3칸 적용
