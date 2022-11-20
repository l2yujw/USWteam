package com.akj.helpyou.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.akj.helpyou.DB.SubwayDBHelper;
import com.akj.helpyou.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.IOException;

import me.piruin.quickaction.ActionItem;
import me.piruin.quickaction.QuickAction;

// 바텀 네비게이션 추가 합시다 -> 승강기, 화장실 등등있어요.
public class SubwayMapActivity extends AppCompatActivity {

    private Toolbar toolbar;                                 // 상단 툴바
    public String targetStation;
    private Cursor c = null; // db 커서
    private Bitmap bitmap;  // imageView의 bitmap
    private Bitmap elevatorBitmap;
    private Bitmap liftBitmap;
    private boolean checkE = true;
    private boolean checkL = true;
    private GestureDetector detector;
    final public String[] versionArray = new String[]{"출발", "경유", "도착"};
    private Button button1;
    private Button button2;

    //Quick Action
    private static final int ID_SOURCE = 1;
    private static final int ID_VIA = 2;
    private static final int ID_DESTINATION = 3;
    private static final int ID_TIMETABLE = 4;
    private static final int ID_3DVIEW = 5;
    private QuickAction quickAction;

    // 데이터 값 저장
    public String Source = null;
    public String Via = null;
    public String Destination = null;
    // 검색창
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_subwaymap);

        // 툴바 만들기
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정

        // QUICK ACTION 만들기

        ActionItem sourceItem = new ActionItem(ID_SOURCE, "출발", R.drawable.ic_baseline_source_pin_24);
        ActionItem viaItem = new ActionItem(ID_VIA, "경유", R.drawable.ic_baseline_via_pin_24);
        ActionItem destinationItem = new ActionItem(ID_DESTINATION, "도착", R.drawable.ic_baseline_destination_pin_24);
        ActionItem timetableItem = new ActionItem(ID_TIMETABLE, "시간표", R.drawable.ic_baseline_timetable_24);
        ActionItem viewItem = new ActionItem(ID_3DVIEW, "3D", R.drawable.ic_baseline_3dview_24);

        sourceItem.setSticky(true);
        viaItem.setSticky(true);
        destinationItem.setSticky(true);
        timetableItem.setSticky(true);
        viewItem.setSticky(true);

        quickAction = new QuickAction(this, QuickAction.HORIZONTAL);
        quickAction.setColorRes(R.color.fabcolor);
        quickAction.setTextColorRes(R.color.white);

        quickAction.addActionItem(sourceItem);
        quickAction.addActionItem(viaItem);
        quickAction.addActionItem(destinationItem);
        quickAction.addActionItem(timetableItem);
        quickAction.addActionItem(viewItem);

        RelativeLayout quickview = findViewById(R.id.quickview);


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

                                Log.e("Cursor", "X1: " + c.getInt(2) + " Y1: " + c.getInt(3) + " X2: " + c.getInt(4) + " Y2: " + c.getInt(5));
                                Log.e("Station", targetStation);
                                Log.e("ev", "X: " + ev.getX() + "  Y: " + ev.getY() );
                                quickview.setX(ev.getX());
                                quickview.setY(ev.getY());
                                quickAction.show(quickview);
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
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.naver_subway);
                elevatorBitmap = makeEle(bitmap, elevatorBitmap);
                if (checkE == true && checkL == true) {
                    imageView.setImage(ImageSource.bitmap(elevatorBitmap));
                    checkE = false;
                } else if (checkE == false && checkL == true) {
                    imageView.setImage(ImageSource.bitmap(bitmap));
                    checkE = true;
                } else if (checkE == true && checkL == false) {
                    imageView.setImage(ImageSource.bitmap(elevatorBitmap));
                    checkE = false;
                    checkL = true;
                } else if (checkE == false && checkL == false) {
                    imageView.setImage(ImageSource.bitmap(elevatorBitmap));
                    checkE = true;
                    checkL = true;
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.naver_subway);
                liftBitmap = makeLift(bitmap, liftBitmap);
                if (checkE == true && checkL == true) {
                    imageView.setImage(ImageSource.bitmap(liftBitmap));
                    checkL = false;
                } else if (checkE == true && checkL == false) {
                    imageView.setImage(ImageSource.bitmap(bitmap));
                    checkL = true;
                } else if (checkE == false && checkL == true) {
                    imageView.setImage(ImageSource.bitmap(liftBitmap));
                    checkE = true;
                    checkL = false;
                } else if (checkE == false && checkL == false) {
                    imageView.setImage(ImageSource.bitmap(liftBitmap));
                    checkE = true;
                    checkL = true;
                }
            }
        });

        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(ActionItem item) {
                if (item == sourceItem) {
                    Source = targetStation;
                    Toast.makeText(getApplicationContext(), Source, Toast.LENGTH_SHORT).show();
                } else if (item == viaItem) {
                    Via = targetStation;
                    Toast.makeText(getApplicationContext(), Via, Toast.LENGTH_SHORT).show();
                } else if (item == destinationItem) {
                    Destination = targetStation;
                    Toast.makeText(getApplicationContext(), Destination, Toast.LENGTH_SHORT).show();
                } else if (item == timetableItem) {
                    Intent intent = new Intent(getApplicationContext(), SubwayDetailActivity.class);
                    intent.putExtra("targetStation", targetStation);
                    startActivity(intent);
                } else if (item == viewItem) {
                    Intent intent = new Intent(getApplicationContext(), Substitute3dImageActivity.class);
                    intent.putExtra("targetStation", targetStation);
                    startActivity(intent);
                }
            }
        });


        // 지하철 역 검색하면 화면 확대 + 이동
        searchView = findViewById(R.id.search_subway);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                if (c.moveToFirst()) {
                    do {
                        if (c.getString(1).indexOf(searchText)>-1) {
                            // db에서 좌표값 받아오고 확대해서 보여주기.
                            PointF point = new PointF();
                            point.x = (c.getInt(2) + c.getInt(4)) / 2;
                            point.y = (c.getInt(3) + c.getInt(5)) / 2;
                            imageView.setScaleAndCenter(2, point);
                        }
                    } while (c.moveToNext());
                }

                return true;
            } // 입력받은 문자열 처리

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            } // 입력란의 문자가 바뀌었을 떄 처리
        });

        // 마커 생성


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

    public Bitmap makeEle(Bitmap original, Bitmap copy) {
        copy = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
        Canvas canvas = new Canvas(copy);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        canvas.drawBitmap(original, 0, 0, paint);
        RectF rectf = new RectF();
        if (c.moveToFirst()) {
            do {
                if (c.getInt(6) == 1) {
                    rectf.set(c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5));
                    canvas.drawArc(rectf, 0, 360, true, paint);
                }
            } while (c.moveToNext());
        }

        return copy;
    }

    public Bitmap makeLift(Bitmap original, Bitmap copy) {
        copy = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
        Canvas canvas = new Canvas(copy);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        canvas.drawBitmap(original, 0, 0, paint);
        RectF rectf = new RectF();
        if (c.moveToFirst()) {
            do {
                if (c.getInt(7) == 1) {
                    rectf.set(c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5));
                    canvas.drawArc(rectf, 0, 360, true, paint);
                }
            } while (c.moveToNext());
        }

        return copy;
    }


}
