package com.akj.helpyou.activities;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.akj.helpyou.R;
import com.akj.helpyou.activities.Odsay.SubwayName;
import com.akj.helpyou.activities.Odsay.SubwayTimetable;

import java.util.ArrayList;
import java.util.List;

public class SubwayDetailActivity extends AppCompatActivity {

    private TextView stationName;

    public static String[] code = new String[5];
    public static  String[] LineName = new String[5];


    private static boolean[][] checkNum = {{true},{true},{true},{true},{true},{true},{true},{true}};

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



        setContentView(R.layout.activity_subway_detail);

        stationName = findViewById(R.id.subwayd_station);
        stationName.setText(targetStation);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(SubwayDetailActivity.this);
        RecyclerView rvUp = findViewById(R.id.recyclerView_subwayd_up);
        SubwayDTimeAdapter subwayDTimeAdapter = new SubwayDTimeAdapter(buildSubwayDTimeListUp());
        rvUp.setAdapter(subwayDTimeAdapter);
        rvUp.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(SubwayDetailActivity.this);
        RecyclerView rvDown = findViewById(R.id.recyclerView_subwayd_down);
        SubwayDTimeAdapter subwayDTimeAdapterDown = new SubwayDTimeAdapter(buildSubwayDTimeListDown());
        rvDown.setAdapter(subwayDTimeAdapterDown);
        rvDown.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(SubwayDetailActivity.this, HORIZONTAL,false);
        RecyclerView rvLine = findViewById(R.id.recyclerView_subwayd_line);
        SubwayDStationAdapter subwayDStationAdapter = new SubwayDStationAdapter(buildSubwayDStationList());
        subwayDStationAdapter.setOnItemClickListener(new SubwayDStationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                checkLine = position;
                if(checkNum[position][0]){
                    rvUp.setVisibility(View.GONE);
                    rvDown.setVisibility(View.GONE);
                    checkNum[position][0] = false;
                }
                else{
                    rvUp.setVisibility(View.VISIBLE);
                    rvDown.setVisibility(View.VISIBLE);
                    checkNum[position][0] = true;
                }

                Log.d("dddd"," "+position);

                SubwayTimetable subwayTimetable = new SubwayTimetable();
                SubwayTimetable.SubwayCode(code[position]);
                subwayTimetable.start();
                try {
                    subwayTimetable.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        rvLine.setAdapter(subwayDStationAdapter);
        rvLine.setLayoutManager(layoutManager3);


    }
    private  List<SubwayDTime> buildSubwayDTimeListUp(){
        List<SubwayDTime> subwayDTimeListUp = new ArrayList<>();
        for(int i=5; i<25; i++) {
            SubwayDTime subwayDTimeUp = new SubwayDTime(i+"시", SubwayTimeTable[0][i]);
            Log.d("zzxxcc","zzxxcc : " + SubwayTimeTable[0][i]);
            subwayDTimeListUp.add(subwayDTimeUp);
        }
        return subwayDTimeListUp;
    }
    private  List<SubwayDTime> buildSubwayDTimeListDown(){
        List<SubwayDTime> subwayDTimeListDown = new ArrayList<>();
        for(int i=5; i<25; i++) {
            SubwayDTime subwayDTimeDown = new SubwayDTime(i+"시", SubwayTimeTable[0][i]);
            subwayDTimeListDown.add(subwayDTimeDown);
        }
        return subwayDTimeListDown;
    }

    private List<SubwayDStation> buildSubwayDStationList() {
        List<SubwayDStation> subwayDStationList = new ArrayList<>();
        for(int i=0; i<LineName.length; i++) {
            if(LineName[i] != null) {
                SubwayDStation subwayDStation = new SubwayDStation(LineName[i]);
                subwayDStationList.add(subwayDStation);
            }
        }
        return subwayDStationList;
    }
}