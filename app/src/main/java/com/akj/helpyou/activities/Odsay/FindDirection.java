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
    FindRoadActivity findRoadActivity = new FindRoadActivity();

    public void run() {
        try {
            // ODsay Api Key 정보
            String apiKey = odsay_api_key;
            String start_x = findRoadActivity.start_x.toString();
            String start_y = findRoadActivity.start_y.toString();
            String end_x = findRoadActivity.end_x.toString();
            String end_y = findRoadActivity.end_y.toString();

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
