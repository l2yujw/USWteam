package com.akj.helpyou.activities.Odsay;
import android.app.Activity;
import android.util.Log;


import com.akj.helpyou.activities.ResultRouteActivity;
import com.akj.helpyou.activities.ResultRouteDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class JsonParser extends Activity {

    public static void jsonParser(String resultJson) {
//
        int[][] Type = new int[10][10];
        String [][][] Pass = new String[5][20][1000];
        String[][] mapObj = new String[10][10];
        ArrayList<DataKeyword> trafficDataList = new ArrayList<>();
        String []Tel = new String[2];
        String []XY = new String[4];
        String []bus = new String[2];
        String low = null;
        String BCompany;
        int busarrival = 0;
        try {
            JSONObject odsayData = new JSONObject(resultJson);
            String searchType = odsayData.getJSONObject("result").getString("searchType");
            JSONArray path = odsayData.getJSONObject("result").getJSONArray("path");

            Integer trafficCount = path.getJSONObject(0).getJSONArray("subPath").length();

            int[][] count = new int[10][100];
            int i=0;
            int g= 0;

            for(int j=0; j<path.length(); j++) {

                String totalTime = path.getJSONObject(j).getJSONObject("info").getString("totalTime");
                String payment = path.getJSONObject(j).getJSONObject("info").getString("payment");

                for ( i=0; i<trafficCount; i++) {

                    Integer trafficType = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("trafficType");
                    if (trafficType == 3) { //도보
                        Integer distance = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("distance");
                        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                        trafficDataList.add(g , new DataKeyword( "도보",   distance.toString(), null, secTime , 0, null," "
                                ,3, totalTime,payment,null,null, null,0,null,null));
                        g++;
                        Log.d("rewq", "secTime : " + secTime);
                    }
                    if (trafficType == 2) { //버스
                        String busstart = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startName");
                        String busend = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endName");
                        String startX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startX");
                        String startY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startY");
                        String endX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endX");
                        String endY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endY");
                        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                        Integer subwayCount = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("stationCount");
                        String busNo = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONArray("lane").getJSONObject(0).getString("busNo");
                        Integer startcode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("startID");
                        String busID = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startArsID");
                        int CityCode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONArray("lane").getJSONObject(0).getInt("busCityCode");


                        JSONArray pass = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONObject("passStopList").getJSONArray("stations");
                        count[j][i] = pass.length();

                        for(int z=0; z<pass.length(); z++) {
                                Pass[j][i][z] = pass.getJSONObject(z).getString("stationName");

                        }
                        // 노선 그래픽 데이터 좌표 불러오기
                        mapObj[j][i] = path.getJSONObject(j).getJSONObject("info").getString("mapObj");
                        //그래픽데이터 타입 저장
                        Type[j][i] = trafficType;


                        BCompany = BusCompany.run(busNo, CityCode); //버스회사 받아옴. 엑셀로 버스회사 매칭되는거 전화번호 값 받아오기 설정필요

                        Log.d("BusCompany","버스회사 : "+BCompany);

                        Log.d("rewq","trafficType : " + trafficType);
                        if(BusTimetable.run(startcode,busNo) != null) {
                            bus = BusTimetable.run(startcode, busNo);
                            busarrival = Integer.parseInt(bus[0]);
                            low = bus[1];
                            Log.d("busdata", "버스도착시간 : " + busarrival + "저상버스유무 : " + low);
                        }
                        trafficDataList.add(g, new DataKeyword( busNo, busstart, busend + "  ", secTime, subwayCount, busID," ",3,startX,startY,endX,endY, low, busarrival,null,null));
                        g++;
                    }
                    if (trafficType == 1) { //지하철
                        String subwaystart = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startName");
                        String subwayend = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endName");
                        String startX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startX");
                        String startY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startY");
                        String endX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endX");
                        String endY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endY");
                        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                        Integer subwayCount = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("stationCount");
                        Integer subwayNo = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONArray("lane").getJSONObject(0).getInt("subwayCode");
                        Integer startcode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("startID");
                        Integer endcode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("endID");
                        Integer wayCode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("wayCode");

                        Type[j][i] = trafficType;
                        mapObj[j][i] = path.getJSONObject(j).getJSONObject("info").getString("mapObj");
                        Log.d("qqtt", "mapobj : " +mapObj);

                       // PathGraphic.run(mapObj,g);

                        JSONArray pass = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONObject("passStopList").getJSONArray("stations");
                        Log.d("qwe","qwe : " +pass);
                        for(int z=0; z<pass.length(); z++) {
                            Pass[j][i][z] = pass.getJSONObject(z).getString("stationName");

                        }
                        count[j][i] = pass.length();
                        Log.d("qwe","count : " +count[j][i]);
                        Tel = Subwaytell.run(startcode, endcode);
                        Log.d("TEL", "Start_tell : " + Tel[0] + " End_tell : "+ Tel[1]);
                        XY = ElevatorLocation.run(subwaystart, subwayend);
                        if(XY[0] != null) {
                            startX = XY[0];
                            startY = XY[1];
                            endX = XY[2];
                            endY = XY[3];
                        }
                        Log.d("qwq ", "SX : "+ startX + " SY : " + startY + " EX : " + endX + " EY : " +endY);
                        trafficDataList.add(g, new DataKeyword( subwayNo.toString() + "호선", subwaystart + "역", subwayend + "역  ", secTime , subwayCount ,null," ",wayCode,
                                startX, startY,endX,endY,null,0,Tel[0],Tel[1]));
                        g++;

                    }

                    g--;
                    PathGraphic.run(mapObj,Type, j,i);
                    ResultRouteActivity.resdata(trafficDataList, j, i, g, trafficType);
                    ResultRouteDetailActivity.resdata2(trafficDataList, j, i, g, trafficType, Pass, count);
                    g++;
                }
            }


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}
