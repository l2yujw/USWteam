package com.akj.helpyou.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.akj.helpyou.R;
import com.akj.helpyou.activities.findroad.DBHelper;
import com.akj.helpyou.activities.findroad.DBHelper2;
import com.akj.helpyou.activities.findroad.ListFragment;
import com.akj.helpyou.activities.findroad.ListFragment2;
import com.akj.helpyou.activities.findroad.ListFragment3;
import com.akj.helpyou.activities.findroad.Time;
import com.akj.helpyou.activities.odsay.FindDirection;
import com.akj.helpyou.activities.result.ResultRouteActivity;
import com.akj.helpyou.activities.result.ResultRouteDetailActivity;
import com.akj.helpyou.activities.search.SearchActivity;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;

public class FindRoadActivity extends AppCompatActivity {

    private String TAG = "FindRoadActivity123";

    private Button btnfindroad;
    private int startrequestcode = 100;
    private int endrequestcode = 101;
    private TextView startText;
    private TextView endText;

    public Double start_x;
    public Double start_y;
    public Double end_x;
    public Double end_y;

    DBHelper dbHelper;
    DBHelper2 dbHelper2;
    Time time = new Time();

    ListFragment listFragment;
    ListFragment2 listFragment2;
    ListFragment3 listFragment3;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_findroad);

        startText = (TextView) findViewById(R.id.addressSearchEditText1);
        endText = (TextView) findViewById(R.id.addressSearchEditText2);
        startText.setInputType(0);
        endText.setInputType(0);


        startText.setOnClickListener( //출발지 EditText버튼 클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.putExtra("textView1","출발지");
                        startActivityForResult(intent, startrequestcode);
                    }
                }
        );
        endText.setOnClickListener( //도착지 EditText버튼 클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.putExtra("textView1","도착지");
                        startActivityForResult(intent, endrequestcode);
                    }
                }
        );


        // 길찾기 버튼을 누르면 경로를 탐색한다. -> 경로를 알려주는 layout + 대중교통 알림 layout추가
        listFragment = new ListFragment();
        listFragment2 = new ListFragment2();
        listFragment3 = new ListFragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, listFragment).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("최근 장소"));
        tabs.addTab(tabs.newTab().setText("최근 경로"));
        tabs.addTab(tabs.newTab().setText("즐겨찾기"));

        tabs.setTabTextColors(Color.rgb(0,0,0),Color.rgb(255,0,0));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if(position == 0){
                    selected = listFragment2;
                }
                else if(position == 1){
                    selected = listFragment;
                }
                else if(position ==2){
                    selected = listFragment3;
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabs.selectTab(tabs.getTabAt(1));

        dbHelper = new DBHelper(getApplicationContext(), "USER_INFO.db", null, 1);
        dbHelper2 = new DBHelper2(getApplicationContext(), "USER_INFO2.db", null, 1);

        btnfindroad = findViewById(R.id.findRoad);
        btnfindroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String startpoint = startText.getText().toString();
                String endpoint = endText.getText().toString();

                Geocoder geocoder = new Geocoder(FindRoadActivity.this);
                List<Address> start_address = null;
                List<Address> end_address = null;
                try {
                    start_address = geocoder.getFromLocationName(startpoint,5);
                    end_address = geocoder.getFromLocationName(endpoint,5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(start_address==null){
                    Toast.makeText(getApplicationContext(), "시작 주소 검색 오류", Toast.LENGTH_SHORT).show();
                }else if(end_address==null){
                    Toast.makeText(getApplicationContext(), "도착 주소 검색 오류", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "start_address: "+start_address.get(0));
                    Log.d(TAG, "end_address: "+end_address.get(0));

                    start_x = start_address.get(0).getLongitude();
                    start_y = start_address.get(0).getLatitude();
                    end_x = end_address.get(0).getLongitude();
                    end_y = end_address.get(0).getLatitude();
                }

                if(startpoint.equals("") || endpoint.equals("")){
                    Toast.makeText(getApplicationContext(), "입력을 다 해주세요", Toast.LENGTH_SHORT).show();
                }else if(start_x.isNaN() || start_y.isNaN() || end_x.isNaN() || end_y.isNaN()){
                    Toast.makeText(getApplicationContext(),"좌표 없음",Toast.LENGTH_SHORT).show();
                }
                else{

                    FindDirection runThread = new FindDirection();
                    FindDirection.xtdata(start_x,start_y,end_x,end_y);
                    runThread.start();

                    dbHelper.insert(startpoint,endpoint,time.set);
                    dbHelper2.insert(startpoint,time.set);
                    dbHelper2.insert(endpoint,time.set);

                    try {
                        runThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //reset();
                    Intent intent = new Intent(getApplicationContext(), ResultRouteActivity.class);
                    intent.putExtra("startText1", startpoint);
                    intent.putExtra("endText1", endpoint);
                    startActivity(intent);


                    Intent toDetail = new Intent(getApplicationContext(), ResultRouteDetailActivity.class);
                    toDetail.putExtra("toDetail_x", start_x);
                    toDetail.putExtra("toDetail_y", start_y);

                }
            }
        });

        //MainActivity에서 마커로 눌러서 와졌을때
        if(getIntent().hasExtra("startPoint") && getIntent().hasExtra("endPoint")){
            startText.setText(getIntent().getStringExtra("startPoint"));
            endText.setText(getIntent().getStringExtra("endPoint"));

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==startrequestcode){
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String sendText = data.getExtras().getString("returnValue");
            startText.setText(sendText);
            start_x = data.getExtras().getDouble("returnValue_x");
            start_y = data.getExtras().getDouble("returnValue_y");
        }else if(requestCode==endrequestcode){
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            String sendText = data.getExtras().getString("returnValue");
            endText.setText(sendText);
            end_x = data.getExtras().getDouble("returnValue_x");
            end_y = data.getExtras().getDouble("returnValue_y");
        }

    }

    public void reset(){
        startText.setText("");
        endText.setText("");
    }


}
