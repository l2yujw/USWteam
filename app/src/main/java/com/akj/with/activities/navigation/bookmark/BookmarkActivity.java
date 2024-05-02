package com.akj.with.activities.navigation.bookmark;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.akj.with.R;
import com.akj.with.activities.navigation.bookmark.fragment.TabAreaFragment;
import com.akj.with.activities.navigation.bookmark.fragment.TabBusFragment;
import com.akj.with.activities.navigation.bookmark.fragment.TabRouteFragment;
import com.google.android.material.tabs.TabLayout;


public class BookmarkActivity extends AppCompatActivity {
    TabLayout tabs;
    TabAreaFragment tabAreaFragment; // 장소
    TabBusFragment tabBusFragment; // 버스
    TabRouteFragment tabRouteFragment; // 경로

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        tabs = findViewById(R.id.tablayout_bookmark);
        tabs.addTab(tabs.newTab().setText("장소"));
        tabs.addTab(tabs.newTab().setText("버스"));
        tabs.addTab(tabs.newTab().setText("경로"));

        tabAreaFragment = new TabAreaFragment();
        tabBusFragment = new TabBusFragment();
        tabRouteFragment = new TabRouteFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_bookmark, tabAreaFragment).commit();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition(); // 탭위치

                Fragment selected = null;

                if(position == 0) {
                    selected = tabAreaFragment;
                }else if(position == 1) {
                    selected = tabBusFragment;
                }else if (position == 2) {
                    selected = tabRouteFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_bookmark, selected).commit();
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


