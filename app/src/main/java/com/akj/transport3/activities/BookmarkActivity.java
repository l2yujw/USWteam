package com.akj.transport3.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.akj.transport3.R;
import com.akj.transport3.fragments.FragmentTabArea;
import com.akj.transport3.fragments.FragmentTabBus;
import com.akj.transport3.fragments.FragmentTabRoute;
import com.google.android.material.tabs.TabLayout;


public class BookmarkActivity extends AppCompatActivity {
    TabLayout tabs;
    FragmentTabArea fragment1; // 장소
    FragmentTabBus fragment2; // 버스
    FragmentTabRoute fragment3; // 경로

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bookmark);

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("장소"));
        tabs.addTab(tabs.newTab().setText("버스"));
        tabs.addTab(tabs.newTab().setText("경로"));

        fragment1 = new FragmentTabArea();
        fragment2 = new FragmentTabBus();
        fragment3 = new FragmentTabRoute();

        getSupportFragmentManager().beginTransaction().replace(R.id.nv_bookmarker, fragment1).commit();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition(); // 탭위치

                Fragment selected = null;

                if(position == 0) {
                    selected = fragment1;
                }else if(position == 1) {
                    selected = fragment2;
                }else if (position == 2) {
                    selected = fragment3;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nv_bookmarker, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }



        });




    }
}


