package com.akj.with.activities.findroad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.akj.with.R;
import com.akj.with.activities.findroad.fragment.FindRoadRecentFragment;
import com.akj.with.activities.findroad.fragment.FindRoadStarFragment;
import com.akj.with.activities.findroad.fragment.FindRoadRouteFragment;
import com.akj.with.activities.odsay.FindDirection;
import com.akj.with.activities.result.ResultRouteActivity;
import com.akj.with.activities.result.ResultRouteDetailActivity;
import com.akj.with.activities.search.SearchActivity;
import com.akj.with.db.search.RouteDatabase;
import com.akj.with.db.search.RecentDatabase;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;

public class FindRoadActivity extends AppCompatActivity {

    private String TAG = "FindRoadActivityTAG";
    private Button btnFindRoad;
    private int startRequestCode = 100;
    private int endRequestCode = 101;
    private TextView tvStart;
    private TextView tvEnd;

    public Double startX;
    public Double startY;
    public Double endX;
    public Double endY;

    RouteDatabase routeDb;
    RecentDatabase recentDb;
    Time time = new Time();

    FindRoadRecentFragment recentFragment;
    FindRoadRouteFragment routeFragment;
    FindRoadStarFragment starFragment;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findroad);

        tvStart = findViewById(R.id.tv_findroad_start);
        tvEnd = findViewById(R.id.tv_findroad_end);
        tvStart.setInputType(0);
        tvEnd.setInputType(0);

        searchDestination(tvStart, "출발지", startRequestCode);
        searchDestination(tvEnd, "도착지", endRequestCode);

        // 길찾기 버튼을 누르면 경로를 탐색한다. -> 경로를 알려주는 layout + 대중교통 알림 layout추가
        setTabLayout();

        routeDb = new RouteDatabase(getApplicationContext(), "route.db", null, 1);
        recentDb = new RecentDatabase(getApplicationContext(), "recent.db", null, 1);

        findRoadButton();

        //MainActivity에서 마커로 눌러서 와졌을때
        if(getIntent().hasExtra("startPoint") && getIntent().hasExtra("endPoint")){
            tvStart.setText(getIntent().getStringExtra("startPoint"));
            tvEnd.setText(getIntent().getStringExtra("endPoint"));

        }
    }

    private void searchDestination(TextView tvStart, String destination, int startRequestCode) {
        tvStart.setOnClickListener(
                view -> {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("destination", destination);
                    startActivityForResult(intent, startRequestCode);
                }
        );
    }

    private void setTabLayout() {
        recentFragment = new FindRoadRecentFragment();
        routeFragment = new FindRoadRouteFragment();
        starFragment = new FindRoadStarFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_findroad_container, routeFragment).commit();

        tabs = findViewById(R.id.tablayout_bookmark);
        tabs.addTab(tabs.newTab().setText("최근 장소"));
        tabs.addTab(tabs.newTab().setText("최근 경로"));
        tabs.addTab(tabs.newTab().setText("즐겨 찾기"));
        tabs.setTabTextColors(Color.rgb(0,0,0),Color.rgb(255,0,0));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;

                if(position == 0){
                    selected = recentFragment;
                }
                else if(position == 1){
                    selected = routeFragment;
                }
                else if(position ==2){
                    selected = starFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_findroad_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabs.selectTab(tabs.getTabAt(1));
    }

    // TODO: FindDirection 정리
    private void findRoadButton() {
        btnFindRoad = findViewById(R.id.btn_findroad_search);
        btnFindRoad.setOnClickListener(view -> {
            String startPoint = tvStart.getText().toString();
            String endPoint = tvEnd.getText().toString();

            Geocoder geocoder = new Geocoder(FindRoadActivity.this);
            List<Address> startAddressList = null;
            List<Address> endAddressList = null;
            try {
                startAddressList = geocoder.getFromLocationName(startPoint,5);
                endAddressList = geocoder.getFromLocationName(endPoint,5);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (startAddressList == null) {
                Toast.makeText(getApplicationContext(), "시작 주소 검색 오류", Toast.LENGTH_SHORT).show();
            } else if (endAddressList == null) {
                Toast.makeText(getApplicationContext(), "도착 주소 검색 오류", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "start_address: " + startAddressList.get(0));
                Log.d(TAG, "end_address: " + endAddressList.get(0));

                startX = startAddressList.get(0).getLongitude();
                startY = startAddressList.get(0).getLatitude();
                endX = endAddressList.get(0).getLongitude();
                endY = endAddressList.get(0).getLatitude();
            }

            if (startPoint.equals("") || endPoint.equals("")) {

                Toast.makeText(getApplicationContext(), "입력을 다 해주세요", Toast.LENGTH_SHORT).show();
            } else if (startX.isNaN() || startY.isNaN() || endX.isNaN() || endY.isNaN()) {
                Toast.makeText(getApplicationContext(), "좌표 없음", Toast.LENGTH_SHORT).show();
            } else {
                FindDirection runThread = new FindDirection();
                FindDirection.xtdata(startX, startY, endX, endY);
                runThread.start();

                routeDb.insert(startPoint, endPoint, time.set);
                recentDb.insert(startPoint, time.set);
                recentDb.insert(endPoint, time.set);

                try {
                    runThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startResultRouteActivity(startPoint, endPoint);

                Intent toDetail = new Intent(getApplicationContext(), ResultRouteDetailActivity.class);
                toDetail.putExtra("toDetail_x", startX);
                toDetail.putExtra("toDetail_y", startY);
            }
        });
    }

    private void startResultRouteActivity(String startPoint, String endPoint) {
        Intent intent = new Intent(getApplicationContext(), ResultRouteActivity.class);
        intent.putExtra("startText1", startPoint);
        intent.putExtra("endText1", endPoint);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == startRequestCode) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String sendText = data.getExtras().getString("returnValue");
            tvStart.setText(sendText);
            startX = data.getExtras().getDouble("returnValue_x");
            startY = data.getExtras().getDouble("returnValue_y");
        } else if (requestCode == endRequestCode) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String sendText = data.getExtras().getString("returnValue");
            tvEnd.setText(sendText);
            endX = data.getExtras().getDouble("returnValue_x");
            endY = data.getExtras().getDouble("returnValue_y");
        }

    }
}
