package com.akj.helpyou.activities.Odsay;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SubwayTimetable {
  //  public static String[] run(int code, String num)
    public static void run(String code){
        String[][] SubwayTime = new String[2][25];  // [상/하행][시간]
        try {

            // ODsay Api Key 정보
            String apiKey = "KBZOsvKhbug6iLyW4x9sOPH+YLTTWKjn2S9oPW7tXiQ";

            String urlInfo = "https://api.odsay.com/v1/api/subwayTimeTable?lang=0&stationID=130&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");
          // String urlInfo = "https://api.odsay.com/v1/api/subwayTimeTable?lang=0&stationID="+code+"&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");
            // http 연결
            URL url = new URL(urlInfo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");

                int resCode = conn.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;

                if(resCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        line = reader.readLine();
                        sb.append(line);

                        if(line == null){
                            break;
                        }
                        JSONObject odsayData = new JSONObject(String.valueOf(sb));
                 //       String sName = odsayData.getJSONObject("result").getString("laneName");
                        JSONObject real = odsayData.getJSONObject("result").getJSONObject("OrdList").getJSONObject("up");
                        JSONArray time = real.getJSONArray("time");
                        Log.d("qtq " ,"qtq : " + real);
                        Log.d("qtq " ,"qtq : " + time);
                        int j =5;
                        for(int i =0; i<time.length(); i++) {
                            int n = time.getJSONObject(i).getInt("Idx");
                            SubwayTime[0][j] = time.getJSONObject(i).getString("list");
                            Log.d("qtq " , j +"시 : " + SubwayTime[0][j]);
                            j++;
                        }
                        JSONObject real2 = odsayData.getJSONObject("result").getJSONObject("OrdList").getJSONObject("down");
                        JSONArray time2 = real2.getJSONArray("time");
                        Log.d("qtq " ,"qtq : " + real2);
                        Log.d("qtq " ,"qtq : " + time2);
                        int k =5;
                        for(int i =0; i<time2.length(); i++) {
                            int n = time2.getJSONObject(i).getInt("Idx");
                            SubwayTime[1][k] = time2.getJSONObject(i).getString("list");
                            Log.d("qtq " , k +"시 : " + SubwayTime[1][k]);
                            k++;
                        }
                    }
                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {

        }

    }
}
