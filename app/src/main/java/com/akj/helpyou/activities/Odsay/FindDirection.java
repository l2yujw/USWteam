package com.akj.helpyou.activities.Odsay;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
// 길찾기 - odsay Json 받아오는 자바 파일 - JasponParser에서 파싱

public class FindDirection extends Thread {
    public void run() {
        try {
            // ODsay Api Key 정보
            String apiKey = "KBZOsvKhbug6iLyW4x9sOPH+YLTTWKjn2S9oPW7tXiQ";

            String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?SX=126.9027279&SY=37.5349277&EX=126.9145430&EY=37.5499421&OPT=0&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

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
