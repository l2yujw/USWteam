package com.akj.helpyou.activities.Odsay;

import static com.akj.helpyou.BuildConfig.odsay_api_key;

import android.util.Log;

import com.akj.helpyou.activities.FindRoadActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
// 길찾기 - odsay Json 받아오는 자바 파일 - JasponParser에서 파싱


public class FindDirection extends Thread {

    public static String start_x;
    public static String start_y;
    public static String end_x;
    public static String end_y;

    public static void xtdata(double start_X, double start_Y, double end_X, double end_Y){
        start_x = String.valueOf(start_X);
        start_y = String.valueOf(start_Y);
        end_x = String.valueOf(end_X);
        end_y = String.valueOf(end_Y);
    }

    public void run() {
        try {
            String apiKey = odsay_api_key;

            Log.e("findxy", "start_x : " + start_x + " start_y : " + start_y + " || end_x : " + end_x + " end_y: " + end_y);

            String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?SX=" + start_x + "&SY=" + start_y + "&EX=" + end_x + "&EY=" + end_y + "&OPT=0&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

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

                        Log.d("JsonParsing", "line : " + line);

                        if (line == null) {
                            break;
                        }
                        JsonParser.jsonParser(line);
                    }
                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {

        }
    }
}
