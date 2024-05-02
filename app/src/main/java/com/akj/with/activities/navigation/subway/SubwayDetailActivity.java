package com.akj.with.activities.navigation.subway;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.akj.with.R;
import com.akj.with.activities.navigation.subway.adapter.SubwayDetailStation;
import com.akj.with.activities.navigation.subway.adapter.SubwayDetailStationAdapter;
import com.akj.with.activities.navigation.subway.adapter.SubwayDetailTime;
import com.akj.with.activities.navigation.subway.adapter.SubwayDetailTimeAdapter;
import com.akj.with.activities.odsay.SubwayName;
import com.akj.with.activities.odsay.SubwayTimetable;

import java.util.ArrayList;
import java.util.List;

public class SubwayDetailActivity extends AppCompatActivity {

    private TextView stationName;

    public static String[] code = new String[5];
    public static  String[] LineName = new String[5];


    private static boolean[][] checkNum = {{false},{false},{false},{false},{false},{false},{false},{false}};

    public static int checkLine = 0;

    public static void subwayLine(String [] Line, String[] Code){
        for(int i=0; i<5; i++){
            if(Line[i] != null){
                LineName[i] = Line[i];
            }
            if(Code[i] != null){
                code[i] = Code[i];
            }
        }
    }

    public static String[][] SubwayTimeTable = new String[2][25];
    public static void SubwayTime(String [][] subwaytime){
        SubwayTimeTable = subwaytime;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_detail);

        Intent intent = getIntent();
        String targetStation = intent.getStringExtra("targetStation");

        SubwayName subwayName = new SubwayName();
        SubwayName.subwayname(targetStation);
        subwayName.start();

        try {
            subwayName.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stationName = findViewById(R.id.tv_subway_detail_station);
        stationName.setText(targetStation);

        LinearLayoutManager layoutManagerTimeUp = new LinearLayoutManager(SubwayDetailActivity.this);
        RecyclerView rvUp = findViewById(R.id.rv_subway_detail_up);
        SubwayDetailTimeAdapter timeUpAdapter = new SubwayDetailTimeAdapter(buildTimeUpList());

        LinearLayoutManager layoutManagerTimeDown = new LinearLayoutManager(SubwayDetailActivity.this);
        RecyclerView rvDown = findViewById(R.id.rv_subway_detail_down);
        SubwayDetailTimeAdapter timeDownAdapter = new SubwayDetailTimeAdapter(buildTimeDownList());

        LinearLayoutManager layoutManagerStation = new LinearLayoutManager(SubwayDetailActivity.this, HORIZONTAL,false);
        RecyclerView rvLine = findViewById(R.id.rv_subway_detail_line);
        SubwayDetailStationAdapter stationAdapter = new SubwayDetailStationAdapter(buildStationList());

        stationAdapter.setOnItemClickListener((v, position) -> {
            checkLine = position;

            if (checkNum[position][0]) {
                rvUp.setVisibility(View.GONE);
                rvDown.setVisibility(View.GONE);
                checkNum[position][0] = false;
            } else {
                rvUp.setVisibility(View.VISIBLE);
                rvDown.setVisibility(View.VISIBLE);
                checkNum[position][0] = true;
            }

            SubwayTimetable subwayTimeTable = new SubwayTimetable();
            SubwayTimetable.SubwayCode(code[position]);
            subwayTimeTable.start();
            try {
                subwayTimeTable.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            timeUpAdapter.notifyDataSetChanged();
            timeDownAdapter.notifyDataSetChanged();
        });

        rvUp.setAdapter(timeUpAdapter);
        rvUp.setLayoutManager(layoutManagerTimeUp);

        rvDown.setAdapter(timeDownAdapter);
        rvDown.setLayoutManager(layoutManagerTimeDown);

        rvLine.setAdapter(stationAdapter);
        rvLine.setLayoutManager(layoutManagerStation);
    }
    private  List<SubwayDetailTime> buildTimeUpList(){
        List<SubwayDetailTime> timeUpList = new ArrayList<>();
        for(int i=5; i<25; i++) {
            SubwayDetailTime timeUp = new SubwayDetailTime(i+"시", SubwayTimeTable[0][i]);
            Log.d("zzxxcc","zzxxcc : " + SubwayTimeTable[0][i]);
            timeUpList.add(timeUp);
        }
        return timeUpList;
    }
    private  List<SubwayDetailTime> buildTimeDownList(){
        List<SubwayDetailTime> timeDownList = new ArrayList<>();
        for(int i=5; i<25; i++) {
            SubwayDetailTime timeDown = new SubwayDetailTime(i+"시", SubwayTimeTable[0][i]);
            timeDownList.add(timeDown);
        }
        return timeDownList;
    }

    private List<SubwayDetailStation> buildStationList() {
        List<SubwayDetailStation> stationList = new ArrayList<>();
        for(int i=0; i<LineName.length; i++) {
            if(LineName[i] != null) {
                SubwayDetailStation station = new SubwayDetailStation(LineName[i]);
                stationList.add(station);
            }
        }
        return stationList;
    }
}