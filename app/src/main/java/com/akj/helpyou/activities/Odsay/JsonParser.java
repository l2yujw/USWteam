package com.akj.helpyou.activities.Odsay;
import android.app.Activity;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class JsonParser extends Activity {

    public static void jsonParser(String resultJson) {
//
        int sec;
        ArrayList<DataKeyword> trafficDataList = new ArrayList<>();
        String []XY = new String[4];
        String []bus = new String[2];
        String low = null;
        int busarrival = 0;
        try {
            JSONObject odsayData = new JSONObject(resultJson);
            String searchType = odsayData.getJSONObject("result").getString("searchType");
            JSONArray path = odsayData.getJSONObject("result").getJSONArray("path");

            String totalTime = path.getJSONObject(0).getJSONObject("info").getString("totalTime");
            String payment = path.getJSONObject(0).getJSONObject("info").getString("payment");
            Integer trafficCount = path.getJSONObject(0).getJSONArray("subPath").length();


            int i=0;
            int g= 0;
            for(int j=0; j<path.length(); j++) {
                trafficDataList.add(g , new DataKeyword( null, "출발", null, null, null, 3, null, 3, null,null,null,null, null, 0));
                g++;
//                String totalTime = path.getJSONObject(j).getJSONObject("info").getString("totalTime");
//                String payment = path.getJSONObject(j).getJSONObject("info").getString("payment");
//                Integer trafficCount = path.getJSONObject(j).getJSONArray("subPath").length();

                for ( i=0; i<trafficCount; i++) {
                    Integer trafficType = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("trafficType");

                    if (trafficType == 3) { //도보
                        Integer distance = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("distance");
                        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                        trafficDataList.add(g , new DataKeyword( "도보", "도보로 " + distance + "m 이동", null, secTime.toString() + "분 소요  ", " ", 3," "
                                ,3, null,null,null,null, null,0));
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

                        Log.d("rewq","trafficType : " + trafficType);
                        if(BusTimetable.run(startcode,busNo) != null) {
                            bus = BusTimetable.run(startcode, busNo);
                            busarrival = Integer.parseInt(bus[0]);
                            low = bus[1];
                            Log.d("busdata", "버스도착시간 : " + busarrival + "저상버스유무 : " + low);
                        }
                        trafficDataList.add(g, new DataKeyword( busNo, busstart, busend + "  ", secTime.toString() + "분 소요  ", subwayCount.toString() + "개역 이동", startcode," ",3,startX,startY,endX,endY, low, busarrival));
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
                        Integer wayCode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("wayCode");
                        Log.d("elvator","시작 X : "+ startX +"시작 Y : "+ startY +"끝 X : "+ endX +"끝 Y : "+ endY);

                          XY = ElevatorLocation.run(subwaystart, subwayend);
                        startX = XY[0];
                        startY = XY[1];
                        endX = XY[2];
                        endY = XY[3];
                        Log.d("elvator","시작 X : "+ startX +"시작 Y : "+ startY +"끝 X : "+ endX +"끝 Y : "+ endY);
                        trafficDataList.add(g, new DataKeyword( subwayNo.toString() + "호선", subwaystart + "역", subwayend + "역  ", secTime.toString() + "분 소요  ", subwayCount.toString() + "개역 이동",startcode," ",wayCode,
                                startX, startY,endX,endY,null,0));
                        g++;
                    }
                }
                trafficDataList.add(g, new DataKeyword( null, "도착", null, null, null,3,null,3,null,null,null,null,null,0));
                g++;

            }

            for( i=0; i<100; i++) {
                Log.d("rewq", "result : " + trafficDataList.get(i).getjsonFormat());

            }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}
