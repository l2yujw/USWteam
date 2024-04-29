package com.akj.helpyou.activities.main;

import static net.daum.mf.map.api.MapReverseGeoCoder.*;
import static net.daum.mf.map.api.MapView.*;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.akj.helpyou.db.chargestation.ChargeStation;
import com.akj.helpyou.db.chargestation.ChargeStationDatabase;
import com.akj.helpyou.R;
import com.akj.helpyou.activities.BookmarkActivity;
import com.akj.helpyou.activities.FindRoadActivity;
import com.akj.helpyou.activities.SettingActivity;
import com.akj.helpyou.activities.subway.SubwayMapActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CurrentLocationEventListener, MapViewEventListener, POIItemEventListener {

    private Toolbar toolbar;                                 // 상단 툴바
    private DrawerLayout drawerLayout;                       // 네비게이션 드로우
    private NavigationView navigationView;                   // 네비게이션 드로우
    private Button btnFindRoad;                              // 길찾기 버튼
    private static final String LOG_TAG = "MainActivity";
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private LocationManager lm;
    private Location locCurrent;
    private boolean fabLocationState = true;
    public double fabLatitude = 0.0;
    public double fabLongitude = 0.0;
    public String pointToAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_SHORT).show();
            return;
        }

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locCurrent = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        // 맵뷰 생성
        createMapView();

        // 툴바 생성
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_common_menu); //왼쪽 상단 버튼 아이콘 지정

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        // 툴바 생성 완료 + menu 버튼 생성 + 네비게이션 뷰 생성 완료

        // 길찾기 버튼 클릭시
        findRoadButton();

        //현재 위치로 이동 및 표시
        currentLocationButton();

        //현위치 기준 주변 전동 휠체어 충전소 위치 마크 표시
        electricStationButton();

        //전화
        supportCallButton();

        // 네비게이션 뷰 안 메뉴 선택시 뜨는 창
        createMenuNavigation();
    }

    private void createMapView() {
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
    }

    private void findRoadButton() {
        btnFindRoad = (Button) findViewById(R.id.findRoad);
        btnFindRoad.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FindRoadActivity.class);

            startActivity(intent);
        });
    }

    private void currentLocationButton() {
        FloatingActionButton fabBtnLocation = (FloatingActionButton) findViewById(R.id.fab_location);
        fabBtnLocation.setOnClickListener(view -> {
            if (fabLocationState) {
                mapView.setCurrentLocationTrackingMode(CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                fabLocationState = false;
            } else {
                mapView.setCurrentLocationTrackingMode(CurrentLocationTrackingMode.TrackingModeOff);
                fabLocationState = true;
            }
        });
    }

    private void electricStationButton() {
        FloatingActionButton fabBtnStation = (FloatingActionButton) findViewById(R.id.fab_elctric_station);
        fabBtnStation.setOnClickListener(view -> {
            //api에서 충전소 리스트 불러와서 가까운 순서로 정렬
            findElectricStation();
        });
    }

    private void supportCallButton() {
        FloatingActionButton fabBtnCall = (FloatingActionButton) findViewById(R.id.fab_call);
        fabBtnCall.setOnClickListener(view -> {
            fabLatitude = locCurrent.getLatitude(); //위도
            fabLongitude = locCurrent.getLongitude(); //경도
            MapPoint currentPoint = MapPoint.mapPointWithGeoCoord(fabLatitude, fabLongitude);
            MapReverseGeoCoder reverseGeoCoder = new MapReverseGeoCoder("b71bf16d2f21ac2a4c5efa68ddd32e3f", currentPoint, new ReverseGeoCodingResultListener() {
                @Override
                public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
                    pointToAddress = s;
                }
                @Override
                public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
                    Toast.makeText(getApplicationContext(),"주소 변환 실패", Toast.LENGTH_SHORT).show();
                }
            }, MainActivity.this);

            reverseGeoCoder.startFindingAddress();

            if (pointToAddress == null) {
                Toast.makeText(getApplicationContext(), "다시 한번 눌러주세요", Toast.LENGTH_SHORT).show();
            } else {
                CallTaxiDialog dialog = new CallTaxiDialog(MainActivity.this);
                dialog.callTaxiAddress = pointToAddress;
                dialog.show();
            }
        });
    }

    private void createMenuNavigation() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();

            int id = menuItem.getItemId();
            //메인 홈 선택시 -> 새창으로 안뜨게 하기 + 다른 창에서도 메뉴바 선택할 수 있게 할까?
            if (id == R.id.item_mainhome) {
            }
            //즐겨찾기 선택시
            if (id == R.id.item_bookmark) {
                startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
            }
            //지하철 노선도 선택시
            if (id == R.id.item_subway_map) {
                startActivity(new Intent(getApplicationContext(), SubwayMapActivity.class));
            }
            //설정 선택시
            if (id == R.id.item_setting) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }

            return true;
        });
    }

    private void findElectricStation() {
        double latitude;
        double longitude;

        latitude = locCurrent.getLatitude(); //위도
        longitude = locCurrent.getLongitude(); //경도

        MapPoint centerPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);

        ChargeStationDatabase db = ChargeStationDatabase.Companion.getInstance(getApplicationContext());

        double finalLatitude = latitude;
        double finalLongitude = longitude;

        addChargeStationBluePin(db, finalLatitude, finalLongitude);

        mapView.setMapCenterPointAndZoomLevel(centerPoint, 6, true);
    }

    private void addChargeStationBluePin(ChargeStationDatabase db, double finalLatitude, double finalLongitude) {
        Thread thread = new Thread(() -> {
            Log.d(LOG_TAG, "run: " + finalLatitude + " / " + finalLongitude);
            List<ChargeStation> chargeStationList = db.chargeStationDao().findStationIn100km(finalLatitude, finalLongitude);
            Log.d(LOG_TAG, "run: " + chargeStationList);

            if(chargeStationList.size()==0){
                Toast.makeText(getApplicationContext(),"반경 5km내 충전 시설 없음",Toast.LENGTH_SHORT).show();
            }

            for (int i = 0; i < chargeStationList.size(); i++) {
                MapPoint mappoint = MapPoint.mapPointWithGeoCoord(
                        chargeStationList.get(i).getLat(),
                        chargeStationList.get(i).getLng());
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(chargeStationList.get(i).getAddress());
                marker.setMapPoint(mappoint);
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);

                mapView.addPOIItem(marker);
            }
        });
        thread.start();
    }

    // 메인 화면에서 사이드바의 메뉴 버튼을 클릭했을 때
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //어플이 가끔 죽어서 일단 주석처리
        //mapViewContainer.removeAllViews();
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
    }


    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_SHORT).show();
            return "주소 미발견";
        }

        return addresses.get(0).getAddressLine(0);
    }


    @Override
    // MapView가 사용가능 한 상태가 되었음을 알려준다. onMapViewInitialized()가 호출된 이후에 MapView 객체가 제공하는 지도 조작 API들을 사용할 수 있다.
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override // 지도 중심 좌표가 이동한 경우 호출된다.
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override // 지도 확대/축소 레벨이 변경된 경우 호출된다.
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override // 사용자가 지도 위를 터치한 경우 호출된다.
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override // 사용자가 지도 위 한 지점을 더블 터치한 경우 호출된다.
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override // 사용자가 지도 위 한 지점을 길게 누른 경우(long press) 호출된다.
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override // 사용자가 지도 드래그를 시작한 경우 호출된다.
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override // 사용자가 지도 드래그를 끝낸 경우 호출된다.
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override // 지도의 이동이 완료된 경우 호출된다.
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double latitude = location.getLatitude(); // 경도
        double longitude = location.getLongitude(); // 위도
        String startPoint = getCurrentAddress(latitude, longitude);
        startPoint = startPoint.split("대한민국 ")[1];
        String endpoint = mapPOIItem.getItemName();

        Intent intent = new Intent(MainActivity.this, FindRoadActivity.class);
        intent.putExtra("startPoint", startPoint);
        intent.putExtra("endPoint", endpoint);
        startActivity(intent);

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}