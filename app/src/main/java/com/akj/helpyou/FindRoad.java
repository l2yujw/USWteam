package com.akj.helpyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

public class FindRoad extends Activity {



    private Button btnrecent1;
    private Button btnrecent2;
    private Button btnrecent3;
    private Button btnfindroad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_findroad);

        EditText startText = (EditText) findViewById(R.id.addressSearchEditText1);
        EditText endText = (EditText) findViewById(R.id.addressSearchEditText2);

        startText.setOnClickListener( //출발지 EditText버튼 클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity1.class);
                        startActivity(intent);
//                        startActivityForResult(intent, 100);
                    }
                }
        );
        endText.setOnClickListener( //도착지 EditText버튼 클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity2.class);
                        startActivity(intent);
//                        startActivityForResult(intent, 101);
                    }
                }
        );

        btnrecent1 = (Button) findViewById(R.id.recent_area);
        btnrecent2 = (Button) findViewById(R.id.recent_route);
        btnrecent3 = (Button) findViewById(R.id.recent_bookmark);

        btnrecent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecentAreaActivity.class);

                startActivity(intent);
            }
        });
        btnrecent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecentRouteActivity.class);

                startActivity(intent);
            }
        });
        btnrecent3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecentBookmarkActivity.class);

                startActivity(intent);
            }
        });


        // 길찾기 버튼을 누르면 경로를 탐색한다. -> 경로를 알려주는 layout + 대중교통 알림 layout추가
        btnfindroad = (Button) findViewById(R.id.findRoad);
        btnfindroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
