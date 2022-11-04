package com.akj.helpyou.activities.Odsay;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
// 엘리베이터 위치 정보 Json 받아오기 + 여기서 파싱
public class ElevatorLocation {
    public static void run(String sName, String eName) {
        try {
            // ODsay Api Key 정보
            String apiKey = "715345784a79687733384a674e4345";

            String urlInfo = "http://openapi.seoul.go.kr:8088/715345784a79687733384a674e4345/json/tbTraficElvtr/1/643";

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
                    while (true) {
                        line = reader.readLine();

                        Log.d("kk", "line : " + line);

                        if (line == null) {
                            break;
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
