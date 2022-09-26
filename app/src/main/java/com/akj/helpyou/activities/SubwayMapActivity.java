package com.akj.helpyou.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.akj.helpyou.R;
import com.akj.helpyou.fragments.FragmentSubway1;
import com.akj.helpyou.fragments.FragmentSubway2;
import com.akj.helpyou.fragments.FragmentSubway3;
import com.google.android.material.tabs.TabLayout;

// 바텀 네비게이션 추가 합시다 -> 승강기, 화장실 등등있어요.
public class SubwayMapActivity extends AppCompatActivity {

    private TabLayout tabs;
    private FragmentSubway1 fragment1; // 장소
    private FragmentSubway2 fragment2; // 버스
    private FragmentSubway3 fragment3; // 경로
    private Toolbar toolbar;                                 // 상단 툴바
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_subwaymap);


        // 툴바 만들기
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정


        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("엘레베이터"));
        tabs.addTab(tabs.newTab().setText("휠체어리프트"));
        tabs.addTab(tabs.newTab().setText("화장실"));

        fragment1 = new FragmentSubway1();
        fragment2 = new FragmentSubway2();
        fragment3 = new FragmentSubway3();

        getSupportFragmentManager().beginTransaction().replace(R.id.nv_subway, fragment1).commit();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition(); // 탭위치

                Fragment selected = null;

                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = fragment2;
                } else if (position == 2) {
                    selected = fragment3;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nv_subway, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
