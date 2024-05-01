package com.akj.helpyou.activities.navigation.subway;

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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.akj.helpyou.db.chargestation.SubwayDatabase;
import com.akj.helpyou.R;
import com.akj.helpyou.activities.Viewer;
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
    private Button btnElevator;
    private Button btnLift;

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

    private String uriString = "android://app.application.viewer/assets/www/models/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_subwaymap);

        //DB 설정
        SubwayDatabase subwayDb = new SubwayDatabase(SubwayMapActivity.this); // Reading SQLite database.
        try {
            subwayDb.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            subwayDb.openDataBase();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        c = subwayDb.query("subwayData", null, null, null, null, null, null); // SQLDataRead

        // 툴바 만들기
        createToolbar();

        // QUICK ACTION 만들기
        ActionItem sourceItem = new ActionItem(ID_SOURCE, "출발", R.drawable.ic_baseline_source_pin_24);
        ActionItem viaItem = new ActionItem(ID_VIA, "경유", R.drawable.ic_baseline_via_pin_24);
        ActionItem destinationItem = new ActionItem(ID_DESTINATION, "도착", R.drawable.ic_baseline_destination_pin_24);
        ActionItem timetableItem = new ActionItem(ID_TIMETABLE, "시간표", R.drawable.ic_baseline_timetable_24);
        ActionItem viewItem = new ActionItem(ID_3DVIEW, "3D", R.drawable.ic_baseline_3dview_24);

        createQuickAction(sourceItem, viaItem, destinationItem, timetableItem, viewItem);

        SubsamplingScaleImageView ivMap = findViewById(R.id.subwaymap_map); // 지하철역 이미지뷰 //Refer to ID value.
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.naver_subway);
        ivMap.setImage(ImageSource.bitmap(bitmap));

        RelativeLayout quickView = findViewById(R.id.relativelayout_subwaymap_quickview);
        createGestureDetector(ivMap, quickView);

        ivMap.setOnTouchListener((view, motionEvent) -> {
            detector.onTouchEvent(motionEvent);
            return SubwayMapActivity.super.onTouchEvent(motionEvent);
        });

        // Button - 엘레베이터 / 리프트
        elevatorButton(ivMap);
        liftButton(ivMap);

        quickActionClick(sourceItem, viaItem, destinationItem, timetableItem, viewItem);

        // 지하철 역 검색하면 화면 확대 + 이동
        subwaySearchView(ivMap);
    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_subwaymap);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24); //왼쪽 상단 버튼 아이콘 지정
    }

    private void createQuickAction(ActionItem sourceItem,
                                   ActionItem viaItem,
                                   ActionItem destinationItem,
                                   ActionItem timetableItem,
                                   ActionItem viewItem) {
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
    }

    private void createGestureDetector(SubsamplingScaleImageView imageView, RelativeLayout quickView) {
        detector = new GestureDetector(
                this,
                new GestureDetector.OnGestureListener() {
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
                            int xCor = (int) sCoord.x;
                            int yCor = (int) sCoord.y;

                            Log.e("좌표", "X: " + xCor + ", Y: " + yCor);
                            // Loop for finding the station.
                            if (c.moveToFirst()) {
                                do {
                                    if ((xCor > c.getInt(2)) && (xCor < c.getInt(4)) && (yCor > c.getInt(3)) && (yCor < c.getInt(5))) {
                                        targetStation = c.getString(1); // 유저가 클릭한 지하철역

                                        Log.e("Cursor", "X1: " + c.getInt(2) + " Y1: " + c.getInt(3) + " X2: " + c.getInt(4) + " Y2: " + c.getInt(5));
                                        Log.e("Station", targetStation);
                                        Log.e("ev", "X: " + ev.getX() + "  Y: " + ev.getY());
                                        quickView.setX(ev.getX());
                                        quickView.setY(ev.getY());
                                        quickAction.show(quickView);
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
                }
        );
    }

    private void elevatorButton(SubsamplingScaleImageView imageView) {
        btnElevator = findViewById(R.id.btn_subwaymap_elevator);
        btnElevator.setOnClickListener(view -> {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.naver_subway);
            elevatorBitmap = makeElevatorBitmap(bitmap);
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
        });
    }

    private void liftButton(SubsamplingScaleImageView imageView) {
        btnLift = findViewById(R.id.btn_subwaymap_lift);
        btnLift.setOnClickListener(view -> {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.naver_subway);
            liftBitmap = makeLiftBitmap(bitmap);
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
        });
    }

    private void quickActionClick(ActionItem sourceItem,
                                  ActionItem viaItem,
                                  ActionItem destinationItem,
                                  ActionItem timetableItem,
                                  ActionItem viewItem) {
        quickAction.setOnActionItemClickListener(item -> {
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
                Intent intent;
                if (targetStation.equals("수원") ||
                        targetStation.equals("신길") ||
                        targetStation.equals("여의나루")) {
                    intent = new Intent(getApplicationContext(), Viewer.class);
                } else {
                    intent = new Intent(getApplicationContext(), Substitute3dImageActivity.class);
                }
                intent.putExtra("targetStation", targetStation);
                startActivity(intent);
            }
        });
    }

    private void subwaySearchView(SubsamplingScaleImageView imageView) {
        searchView = findViewById(R.id.subwaymap_search);
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

    public Bitmap makeElevatorBitmap(Bitmap original) {
        Bitmap copy = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
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

    public Bitmap makeLiftBitmap(Bitmap original) {
        Bitmap copy = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
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
