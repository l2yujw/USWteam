package com.akj.helpyou.activities;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;
import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akj.helpyou.R;
import com.akj.helpyou.activities.FindRoad.ListFragment;
import com.akj.helpyou.activities.FindRoad.ListFragment2;
import com.akj.helpyou.activities.FindRoad.ListFragment3;
import com.akj.helpyou.activities.Odsay.SubwayName;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SubwayDetailActivity extends AppCompatActivity {

    private TextView stationName;

    public static String[] code = new String[5];
    public static  String[] LineName = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String targetStation = intent.getStringExtra("targetStation");

        String[] codeLineName = new String[10];
        codeLineName = SubwayName.run(targetStation);

        for(int i=0; i<codeLineName.length/2; i++){
            code[i] = codeLineName[i];
            Log.d("qqww","qqww2 : "+code[i]);
        }
        for(int i=codeLineName.length/2; i<codeLineName.length; i++){
            LineName[i] = codeLineName[i];
            Log.d("qqww","qqww3 : "+LineName[i]);
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
        rvLine.setAdapter(subwayDStationAdapter);
        rvLine.setLayoutManager(layoutManager3);




    }
    private  List<SubwayDTime> buildSubwayDTimeListUp(){
        List<SubwayDTime> subwayDTimeListUp = new ArrayList<>();
        SubwayDTime subwayDTimeUp = new SubwayDTime("dd:dd","출발","도착");
        subwayDTimeListUp.add(subwayDTimeUp);

        return subwayDTimeListUp;
    }
    private  List<SubwayDTime> buildSubwayDTimeListDown(){
        List<SubwayDTime> subwayDTimeListDown = new ArrayList<>();
        SubwayDTime subwayDTimeDown = new SubwayDTime("dd:dd","출발","도착");
        subwayDTimeListDown.add(subwayDTimeDown);

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