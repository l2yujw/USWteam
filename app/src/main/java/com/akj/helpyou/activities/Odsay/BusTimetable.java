package com.akj.helpyou.activities.Odsay;
// 버스 시간 받아오는 Json
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class BusTimetable  {
    public static String[] run(int code, String num) {


        try {
            String []data = new String[2];
            int sec;
            String low;
            // ODsay Api Key 정보
            String apiKey = "odsay_api_key";

            String urlInfo = "https://api.odsay.com/v1/api/realtimeStation?lang=0&stationID="+ code +"&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

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
                        Log.d("BusTimetable", "line : " + line);

                        if(line == null){
                            break;
                        }
                        JSONObject odsayData = new JSONObject(String.valueOf(sb));
                        JSONArray real = odsayData.getJSONObject("result").getJSONArray("real");
                        //Log.d("qtq", "real : " +real);
                        for(int i =0; i<real.length(); i++){
                            String busNm = real.getJSONObject(i).getString("routeNm");
//                            Log.d("qt", "11111 : " + num);
//                            Log.d("qt", "22222 : " +busNm);
                            Log.d("hh", "hh : " + num.equals(busNm));
                            if(num.equals(busNm)) {
                                sec = real.getJSONObject(i).getJSONObject("arrival1").getInt("arrivalSec");
                                low = real.getJSONObject(i).getJSONObject("arrival1").getString("lowBusYn");
                                Log.d("qt", "sec : " + sec );
                                Log.d("qt", "low : " + low );
                                data[0] = String.valueOf(sec);
                                data[1] = low;
                                return data;
                            }

                        }
                    }
                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {

        }

        return null;
    }
}
