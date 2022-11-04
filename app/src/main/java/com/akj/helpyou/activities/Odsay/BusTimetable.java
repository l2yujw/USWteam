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
public class BusTimetable  extends Thread {
    public static String[] run(int code, String num) {
        try {
            String[] res = new String[2] ;

            // ODsay Api Key 정보
            String apiKey = "KBZOsvKhbug6iLyW4x9sOPH+YLTTWKjn2S9oPW7tXiQ";

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
                    while (true) {
                        line = reader.readLine();

                        Log.d("JsonParsing", "line : " + line);

                        if(line == null){
                            break;
                        }
                        JSONObject odsayData = new JSONObject(line);
                        JSONArray real = odsayData.getJSONObject("result").getJSONArray("real");
                        //Log.d("qtq", "real : " +real);
                        for(int i =0; i<real.length(); i++){
                            String busNm = real.getJSONObject(i).getString("routeNm");
//                            Log.d("qt", "11111 : " + num);
//                            Log.d("qt", "22222 : " +busNm);
//                            sec = real.getJSONObject(i).getJSONObject("arrival1").getInt("arrivalSec");
//                            Log.d("qt", "sec : " + sec );
                            if(num.equals(busNm)) {
                                res[0] = String.valueOf(real.getJSONObject(i).getJSONObject("arrival1").getInt("arrivalSec"));
                                res[1] = real.getJSONObject(i).getJSONObject("arrival1").getString("lowBusYn");
                                Log.d("qt", "sec : " + res[0] + "   저상버스 여부(Y/N) : " + res[1]);
                                return res;
                            }
                        }
                    }
                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {

        }

        return new String[0];
    }
}
