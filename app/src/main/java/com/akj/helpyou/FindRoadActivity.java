package com.akj.helpyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindRoadActivity extends Activity {

    private Button btnrecent1;
    private Button btnrecent2;
    private Button btnrecent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_findroad);

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


    }
}
