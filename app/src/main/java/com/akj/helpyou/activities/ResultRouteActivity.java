package com.akj.helpyou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;
import com.akj.helpyou.activities.Odsay.DataKeyword;
import com.akj.helpyou.activities.Odsay.JsonParser;
import com.akj.helpyou.activities.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

// 스레드 스타트
public class ResultRouteActivity extends AppCompatActivity {
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
                Intent intent = new Intent(getApplicationContext(), ResultRouteDetail.class);
                startActivity(intent);
            }
        });
        rvInf.setAdapter(infAdapter);

        rvInf.setLayoutManager(layoutManager);

    }//adapter 클릭시 detail로 이동 필요 값들을 미리 넣어줌

    private List<Inf> buildInfList() {
        List<Inf> infList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Inf inf = new Inf("Item "+i,"time","cost", buildInf2List());
            infList.add(inf);
        }
        return infList;
    }//dddd
    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들)
    private List<Inf2> buildInf2List() {
        List<Inf2> inf2List = new ArrayList<>();
        for (int i=0; i<3; i++) {
            Inf2 inf2 = new Inf2("Sub Item "+i, "Description "+i);
            inf2List.add(inf2);
        }
        return inf2List;
    }
}//content에 recyclerview 적용 아이템 생성 text적용 그 안에 recyclerview 적용 아이템 3칸 적용
