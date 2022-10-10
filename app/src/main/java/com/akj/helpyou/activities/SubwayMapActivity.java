package com.akj.helpyou.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.akj.helpyou.DB.SubwayDBHelper;
import com.akj.helpyou.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.IOException;

// 바텀 네비게이션 추가 합시다 -> 승강기, 화장실 등등있어요.
public class SubwayMapActivity extends AppCompatActivity {

    private Toolbar toolbar;                                 // 상단 툴바
    public String targetStation;
    private Cursor c = null; // db 커서
    private Bitmap bitmap;  // imageView의 bitmap
    private Bitmap drawBitmap;
    private GestureDetector detector;
    final public String[] versionArray = new String[]{"출발", "경유", "도착"};
    private Button button1;
    private Button button2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_subwaymap);


        // 툴바 만들기
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정


        // DB 데이터 적용 시작
        SubwayDBHelper myDbHelper = new SubwayDBHelper(SubwayMapActivity.this); // Reading SQLite database.
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        c = myDbHelper.query("subwayData", null, null, null, null, null, null); // SQLDataRead
        // String table 테이블 이름
        // String[] columns 가져올 컬럼들
        // String selection WHERE문(WHERE를 제외한)
        // String[] SelectionArgs WHERE문에 전달해줄 값들
        // String groupBy 집합함수 컬럼을 추가해 특정 컬럼의 중복되는 값을을 카운트 할 수 있습니다.
        // String having groupBy절에서 사용할 수 있는 필터로, groupBy에서 카운팅된 수를 원하는 범위로 필터링 할 수 있습니다.
        // String orderBy:정렬방식. 특정컬럼의 값을 기준으로 오름차순 혹은 내림차순으로 정렬할 수 있습니다.

        SubsamplingScaleImageView imageView = findViewById(R.id.subwayMap); // 지하철역 이미지뷰 //Refer to ID value.
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.naver_subway);
        imageView.setImage(ImageSource.bitmap(bitmap));

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent ev) {
                if (imageView.isReady()) {
                    PointF sCoord = imageView.viewToSourceCoord(ev.getX(), ev.getY());
                    int x_cor = (int) sCoord.x;
                    int y_cor = (int) sCoord.y;

                    Log.e("좌표", "X: " + x_cor + ", Y: " + y_cor);
                    // Loop for finding the station.
                    if (c.moveToFirst()) {
                        do {
                            if ((x_cor > c.getInt(2)) && (x_cor < c.getInt(4)) && (y_cor > c.getInt(3)) && (y_cor < c.getInt(5))) {
                                targetStation = c.getString(1); // 유저가 클릭한 지하철역

                                Log.e("Cursor", "X1: " + c.getInt(2) + "Y1: " + c.getInt(3) + "X2: " + c.getInt(4) + " Y2: " + c.getInt(5));
                                Log.e("Check", "만족");
                                Log.e("Station", targetStation);
                                // 대화 상자 생성해서 (출발, 경유, 도착지 설정)
                                AlertDialog.Builder dialog = new AlertDialog.Builder(SubwayMapActivity.this);
                                dialog.setTitle(targetStation); // 대화상자 제목
                                dialog.setSingleChoiceItems(versionArray, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        /// 선택 결과 길찾기로 넘기기.
                                    }
                                });
                                dialog.setPositiveButton("닫기", null);
                                dialog.show();

                            }
                        } while (c.moveToNext());
                    }
                }
                return SubwayMapActivity.super.onTouchEvent(ev);
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return SubwayMapActivity.super.onTouchEvent(motionEvent);
            }
        }); //Touchevent를 Gesture로 넘겨서 실행


        // Button - 엘레베이터 / 리프트
        button1 = findViewById(R.id.btn_Elevator);
        button2 = findViewById(R.id.btn_Lift);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                drawBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(drawBitmap);

                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(30f);

                canvas.drawBitmap(bitmap,0,0, paint);

                RectF rectf = new RectF();
                if (c.moveToFirst()) {
                    do {
                        if (c.getInt(6) == 1) {
                            rectf.set(c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5));
                            canvas.drawArc(rectf, 0, 360, true, paint);
                        }
                    } while (c.moveToNext());
                }

                imageView.setImage(ImageSource.bitmap(drawBitmap));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    } // onCreate 끝

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
