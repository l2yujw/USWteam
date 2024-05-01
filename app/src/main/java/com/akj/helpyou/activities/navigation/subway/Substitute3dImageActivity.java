package com.akj.helpyou.activities.navigation.subway;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akj.helpyou.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class Substitute3dImageActivity extends AppCompatActivity {
    private String targetStation;
    private String name = null;
    private String[][] stationList = {{"가락시장", "가락시장.jpg",}, {"가산디지털단지", "가산디지털단지.jpg",}, {"강남", "강남.gif",}, {"강남구청", "강남구청.jpg",}, {"강동", "강동.jpg",}, {"강동구청", "강동구청.jpg",}, {"강변", "강변.jpg",}, {"강일", "강일.jpg",}, {"개롱", "개롱.jpg",}, {"개화산", "개화산.jpg",}, {"거여", "거여.jpg",}, {"건대입구", "건대입구.gif",}, {"건대입구", "건대입구.jpg",}, {"경복궁", "경복궁.gif",}, {"경찰병원", "경찰병원.jpg",}, {"고덕", "고덕.jpg",}, {"고려대", "고려대.jpg",}, {"고속터미널", "고속터미널.jpg",}, {"공덕", "공덕.jpg",}, {"공릉", "공릉.jpg",}, {"광나루", "광나루.jpg",}, {"광명사거리", "광명사거리.jpg",}, {"광화문", "광화문.jpg",}, {"광흥창", "광흥창.jpg",}, {"교대", "교대.gif",}, {"구로디지털단지", "구로디지털단지.jpg",}, {"구산", "구산.jpg",}, {"구의", "구의.gif",}, {"구파발", "구파발.jpg",}, {"군자", "군자.jpg",}, {"굽은다리", "굽은다리.jpg",}, {"금호", "금호.jpg",}, {"길동", "길동.jpg",}, {"길음", "길음.jpg",}, {"김포공항", "김포공항.jpg",}, {"까치산", "까치산.jpg",}, {"낙성대", "낙성대.jpg",}, {"남구로", "남구로.jpg",}, {"남부터미널", "남부터미널.jpg",}, {"남성", "남성.jpg",}, {"남위례.png", "남위례.png",}, {"남태령", "남태령.jpg",}, {"남한산성입구", "남한산성입구.jpg",}, {"내방", "내방.jpg",}, {"노원", "노원.jpg",}, {"녹번", "녹번.jpg",}, {"녹사평", "녹사평.jpg",}, {"논현", "논현.jpg",}, {"단대오거리", "단대오거리.jpg",}, {"답십리", "답십리.jpg",}, {"당고개", "당고개.gif",}, {"당산", "당산.jpg",}, {"대림", "대림.jpg",}, {"대청", "대청.jpg",}, {"대치", "대치.jpg",}, {"대흥", "대흥.jpg",}, {"도곡", "도곡.jpg",}, {"도림천", "도림천.jpg",}, {"도봉산", "도봉산.jpg",}, {"독립문", "독립문.jpg",}, {"독바위", "독바위.jpg",}, {"돌곶이", "돌곶이.jpg",}, {"동대문", "동대문.png",}, {"동대문역사문화공원", "동대문역사문화공원.jpg",}, {"동대입구", "동대입구.jpg",}, {"동묘앞", "동묘앞.jpg",}, {"동작", "동작.jpg",}, {"둔촌동", "둔촌동.jpg",}, {"둔촌오륜", "둔촌오륜.jpg",}, {"디지털미디어시티", "디지털미디어시티.jpg",}, {"뚝섬", "뚝섬.jpg",}, {"뚝섬유원지", "뚝섬유원지.jpg",}, {"마곡", "마곡.jpg",}, {"마들", "마들.jpg",}, {"마장", "마장.jpg",}, {"마천", "마천.jpg",}, {"마포", "마포.jpg",}, {"마포구청", "마포구청.jpg",}, {"망원", "망원.jpg",}, {"매봉", "매봉.jpg",}, {"먹골", "먹골.jpg",}, {"면목", "면목.jpg",}, {"명동", "명동.gif",}, {"명일", "명일.jpg",}, {"모란", "모란.jpg",}, {"목동", "목동.jpg",}, {"몽촌토성", "몽촌토성.jpg",}, {"무악재", "무악재.jpg",}, {"문래", "문래.jpg",}, {"문정", "문정.jpg",}, {"미사", "미사.jpg",}, {"미아.png", "미아.png",}, {"미아사거리", "미아사거리.jpg",}, {"반포", "반포.jpg",}, {"발산", "발산.jpg",}, {"방배", "방배.jpg",}, {"방이", "방이.jpg",}, {"방화", "방화.jpg",}, {"버티고개", "버티고개.jpg",}, {"보라매", "보라매.jpg",}, {"보문수정", "보문수정.jpg",}, {"복정", "복정.jpg",}, {"봉은사", "봉은사.jpg",}, {"봉천", "봉천.jpg",}, {"봉화산", "봉화산.jpg",}, {"불광", "불광.jpg",}, {"사가정", "사가정.jpg",}, {"사당", "사당.jpg",}, {"산성", "산성.jpg",}, {"삼각지", "삼각지.jpg",}, {"삼성", "삼성.gif",}, {"삼성중앙", "삼성중앙.jpg",}, {"삼전", "삼전.jpg",}, {"상계", "상계.jpg",}, {"상도", "상도.jpg",}, {"상봉", "상봉.jpg",}, {"상수", "상수.jpg",}, {"상왕십리", "상왕십리.jpg",}, {"상월곡", "상월곡.jpg",}, {"상일동", "상일동.jpg",}, {"새절", "새절.jpg",}, {"서대문", "서대문.jpg",}, {"서울대입구", "서울대입구.gif",}, {"서울역", "서울역.gif",}, {"서울역", "서울역.jpg",}, {"서초", "서초.jpg",}, {"석계", "석계.jpg",}, {"석촌", "석촌.jpg",}, {"석촌고분", "석촌고분.jpg",}, {"선릉", "선릉.gif",}, {"선정릉", "선정릉.jpg",}, {"성수", "성수.gif",}, {"성신여대입구", "성신여대입구.jpg",}, {"송정", "송정.jpg",}, {"송파", "송파.jpg",}, {"송파나루", "송파나루.jpg",}, {"수락산", "수락산.jpg",}, {"수서", "수서.jpg",}, {"수유", "수유.jpg",}, {"수진", "수진.jpg",}, {"숙대입구", "숙대입구.jpg",}, {"숭실대입구", "숭실대입구.jpg",}, {"시청", "시청.jpg",}, {"신금호", "신금호.jpg",}, {"신길", "신길.obj",}, {"신내", "신내.jpg",}, {"신답", "신답.jpg",}, {"신당", "신당.jpg",}, {"신대방", "신대방.jpg",}, {"신대방삼거리", "신대방삼거리.jpg",}, {"신도림", "신도림.jpg",}, {"신림", "신림.jpg",}, {"신사", "신사.jpg",}, {"신설동.png", "신설동.png",}, {"신용산", "신용산.jpg",}, {"신정", "신정.jpg",}, {"신정네거리", "신정네거리.jpg",}, {"신촌", "신촌.gif",}, {"신풍", "신풍.jpg",}, {"신흥", "신흥.jpg",}, {"쌍문", "쌍문.jpg",}, {"아차산", "아차산.jpg",}, {"아현", "아현.jpg",}, {"안국", "안국.gif",}, {"안암", "안암.jpg",}, {"암사", "암사.jpg",}, {"압구정", "압구정.jpg",}, {"애오개", "애오개.jpg",}, {"약수", "약수.jpg",}, {"양재", "양재.gif",}, {"양천구청", "양천구청.jpg",}, {"양평", "양평.jpg",}, {"어린이대공원", "어린이대공원.jpg",}, {"언주", "언주.jpg",}, {"여의나루", "여의나루.obj",}, {"여의도", "여의도.jpg",}, {"역삼", "역삼.gif",}, {"역촌", "역촌.jpg",}, {"연신내", "연신내.jpg",}, {"영등포구청", "영등포구청.gif",}, {"영등포구청", "영등포구청.jpg",}, {"영등포시장", "영등포시장.jpg",}, {"오금", "오금.jpg",}, {"오목교", "오목교.jpg",}, {"옥수", "옥수.jpg",}, {"온수", "온수.jpg",}, {"올림픽공원", "올림픽공원.jpg",}, {"왕십리", "왕십리.jpg",}, {"용답", "용답.jpg",}, {"용두", "용두.jpg",}, {"용마산", "용마산.jpg",}, {"우장산", "우장산.jpg",}, {"월곡", "월곡.jpg",}, {"월드컵경기장", "월드컵경기장.jpg",}, {"을지로3가", "을지로3가.gif",}, {"을지로4가", "을지로4가.gif",}, {"을지로4가", "을지로4가.jpg",}, {"을지로입구", "을지로입구.gif",}, {"응암", "응암.jpg",}, {"이대입구", "이대입구.gif",}, {"이수", "이수.jpg",}, {"이촌", "이촌.jpg",}, {"이태원", "이태원.jpg",}, {"일원", "일원.jpg",}, {"잠실", "잠실.jpg",}, {"잠실나루", "잠실나루.jpg",}, {"잠실새내.png", "잠실새내.png",}, {"잠원", "잠원.jpg",}, {"장승배기", "장승배기.jpg",}, {"장암", "장암.jpg",}, {"장지", "장지.jpg",}, {"장한평", "장한평.jpg",}, {"제기동", "제기동.jpg",}, {"종각", "종각.jpg",}, {"종로3가", "종로3가.gif",}, {"종로3가", "종로3가.jpg",}, {"종로5가.png", "종로5가.png",}, {"종합운동장", "종합운동장.jpg",}, {"중계", "중계.jpg",}, {"중곡", "중곡.jpg",}, {"중앙보훈병원", "중앙보훈병원.jpg",}, {"중화", "중화.jpg",}, {"증산", "증산.jpg",}, {"지축", "지축.jpg",}, {"창동", "창동.jpg",}, {"창신", "창신.jpg",}, {"천왕", "천왕.jpg",}, {"천호", "천호.jpg",}, {"철산", "철산.jpg",}, {"청구", "청구.jpg",}, {"청담", "청담.jpg",}, {"청량리", "청량리.jpg",}, {"총신대입구이수", "총신대입구이수.jpg",}, {"충무로", "충무로.jpg",}, {"충정로", "충정로.jpg",}, {"태릉입구", "태릉입구.jpg",}, {"하계", "하계.jpg",}, {"하남검단산", "하남검단산.jpg",}, {"하남시청", "하남시청.jpg",}, {"하남풍산", "하남풍산.jpg",}, {"학동", "학동.jpg",}, {"학여울", "학여울.jpg",}, {"한강진", "한강진.jpg",}, {"한성대입구", "한성대입구.jpg",}, {"한성백제", "한성백제.jpg",}, {"한양대", "한양대.jpg",}, {"합정", "합정.gif",}, {"합정", "합정.jpg",}, {"행당", "행당.jpg",}, {"혜화", "혜화.jpg",}, {"홍대입구", "홍대입구.gif",}, {"홍제", "홍제.jpg",}, {"화곡", "화곡.jpg",}, {"화랑대", "화랑대.jpg",}, {"회현", "회현.jpg",}, {"효창공원앞", "효창공원앞.jpg",}, {"수원", "수원.obj"}};
    private String uriString = "android://app.application.viewer/assets/models/";
    private String ext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_subsitute3dimage);


        SubsamplingScaleImageView imageStation = findViewById(R.id.image3dSubstitute);
        targetStation = getIntent().getStringExtra("targetStation");

        for (int i = 0; i < stationList.length; i++) {
            if (stationList[i][0].equals(targetStation)) {
                name = stationList[i][1];
                ext = name.substring(name.lastIndexOf(".") + 1);
            }
        }
        Log.e("targetstation", "역명1: " + targetStation);
        Log.e("name", "역명2: " + name);
        Log.e("ext", "a: " + ext);


        if (name != null && ext.equals("obj")) {
            uriString = uriString + name;
//            loadModel(uriString);
            Toast.makeText(getApplicationContext(), "H: " + uriString, Toast.LENGTH_SHORT).show();
        } else if (name != null) {
            imageStation.setImage(ImageSource.asset(name));
        } else {
            imageStation.setImage(ImageSource.asset("null.png"));
        }


        // if문으로 파일이 있는지 확인 -> 3d 또는 image 파일 실행. // 파일확장자가 .obj면 함수 콜 아니면 일반 image 콜
        //                            -> 이마저도 없으면, 문구 출력 "관련 자료가 없습니다."

    }

//    public void loadModel(String address) {
//
//        ContentUtils.provideAssets(this);
//        launchModelRendererActivity(Uri.parse(address));
//
//    }
//    private void launchModelRendererActivity(Uri uri) {
//        Log.i("Menu", "Launching renderer for '" + uri + "'");
//        Intent intent = new Intent(getApplicationContext(),com.akj.helpyou.activities.ModelViewer.class);
//        try {
//            URI.create(uri.toString());
//            intent.putExtra("uri", uri.toString());
//        } catch (Exception e) {
//            // info: filesystem url may contain spaces, therefore we re-encode URI
//        }
//        intent.putExtra("immersiveMode", "false");
//
//        startActivity(intent);
//    }

}
