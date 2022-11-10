package com.akj.helpyou.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.akj.helpyou.R;

import java.util.ArrayList;
import java.util.List;

public class ResultRouteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_route_detail);

        RecyclerView rvInfD = findViewById(R.id.recyclerView_inf_d);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResultRouteDetailActivity.this);
        InfDAdapter infDAdapter = new InfDAdapter(buildInfDList());
        rvInfD.setAdapter(infDAdapter);
        rvInfD.setLayoutManager(layoutManager);
        //출발지 도착지에 값 입력
        //지도연결
        //슬라이드바에 상세 정보 표시
        //도보 버스 지하철 이런식으로
        //리사이클러 뷰로 하나만 필요
    }

    private List<InfD> buildInfDList() {
        List<InfD> infDList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            InfD infD = new InfD("Item "+i,"time","cost", buildInfD2List());
            infDList.add(infD);
        }
        return infDList;
    }//dddd
    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들)
    private List<InfD2> buildInfD2List() {
        List<InfD2> infD2List = new ArrayList<>();
        for (int i=0; i<3; i++) {
            InfD2 infD2 = new InfD2("Sub Item "+i, "Description "+i,"","","","");
            infD2List.add(infD2);
        }
        return infD2List;
    }
}