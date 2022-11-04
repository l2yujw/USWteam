package com.akj.helpyou.activities.Odsay;
import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class JsonParser {
    public static void jsonParser(String resultJson) {
//
        int sec;
        ArrayList<DataKeyword> trafficDataList = new ArrayList<>();


        try {
            JSONObject odsayData = new JSONObject(resultJson);
            String searchType = odsayData.getJSONObject("result").getString("searchType");
            JSONArray path = odsayData.getJSONObject("result").getJSONArray("path");
            Log.d("rewq", "num : " + path.length());
            String totalTime = path.getJSONObject(0).getJSONObject("info").getString("totalTime");
            String payment = path.getJSONObject(0).getJSONObject("info").getString("payment");
            Integer trafficCount = path.getJSONObject(0).getJSONArray("subPath").length();
            Log.d("rewq", "num : " + trafficCount);
            String[] busres;
            int i= 0;
            int g= 0;
            for(int j=0; j<path.length(); j++) {
                trafficDataList.add(g , new DataKeyword( null, "출발", null, null, null, 3, null, 3, null, 0, null,null,null,null));
                g++;
                for ( i = 0; i < trafficCount; i++) {
                    Integer trafficType = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("trafficType");
                    if (trafficType == 3) { //도보
                        Integer distance = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("distance");
                        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                        //String array[]={trafficType.toString(),null,null,distance.toString()+"m 이동",secTime.toString()+"분 소요",null,"도보"};
                        trafficDataList.add(g , new DataKeyword( "도보", "도보로 " + distance + "m 이동", " ", secTime.toString() + "분 소요  ", " ", 3," "
                                ,3, null, 0,null,null,null,null));
                        g++;
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
                        Log.d("tq", "code : " + startcode);
                        Log.d("shipar","제발 : " +busstart);
                        busres = BusTimetable.run(startcode, busNo);
                        Log.d("tq", "busres : " + busres[0]);
                        Log.d("tq", "busres : " + busres[1]);
                        sec = Integer.parseInt(busres[1]);
//                                String array[]={trafficType.toString(),busstart,busend,null,secTime.toString()+"분 소요",null,busNo};
//                                trafficDataList.add(array);
                        trafficDataList.add(g, new DataKeyword( busNo, busstart, busend + "  ", secTime.toString() + "분 소요  ", subwayCount.toString() + "개역 이동", startcode," ",3, busres[0], sec ,startX,startY,endX,endY));
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
                        trafficDataList.add(g, new DataKeyword( subwayNo.toString() + "호선", subwaystart + "역", subwayend + "역  ", secTime.toString() + "분 소요  ", subwayCount.toString() + "개역 이동",startcode," ",wayCode, null, 0,
                                startX, startY,endX,endY));
                        g++;
                       // subwayelvator.run(subwaystart, subwayend);
                    }
                }
                trafficDataList.add(g, new DataKeyword( null, "도착", null, null, null,3,null,3,null,0,null,null,null,null));
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
