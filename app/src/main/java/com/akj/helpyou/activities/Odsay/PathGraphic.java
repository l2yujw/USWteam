package com.akj.helpyou.activities.Odsay;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PathGraphic {

    public static void run(String mapObj, int n) {
        try {
            String[] Pathxy = new String[20];
            // ODsay Api Key 정보
            String apiKey = "odsay_api_key";

            String urlInfo = "https://api.odsay.com/v1/api/loadLane?lang=0&mapObject=126:37@" + mapObj + "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

            // http 연결
            URL url = new URL(urlInfo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");

                int resCode = conn.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;

                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        line = reader.readLine();
                        sb.append(line);
                        Log.d("BusTimetable", "line : " + line);

                        if (line == null) {
                            break;
                        }
                        JSONObject odsayData = new JSONObject(String.valueOf(sb));

                        JSONArray real = odsayData.getJSONObject("result").getJSONArray("lane");
                        JSONArray xyPos = real.getJSONObject(0).getJSONArray("section").getJSONObject(0).getJSONArray("graphPos");
                        Pathxy[n] = String.valueOf(xyPos);
                        Log.d("qqtt", "real : " +Pathxy[n]);


                    }
                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {

        }

    }
}

