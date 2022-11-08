package com.akj.helpyou.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

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
        rvInf.setAdapter(infAdapter);

        rvInf.setLayoutManager(layoutManager);

    }
    private List<Inf> buildInfList() {
        List<Inf> infList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Inf inf = new Inf("Item "+i,"time","cost", buildInf2List());
            infList.add(inf);
        }
        return infList;
    }
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
