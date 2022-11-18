package com.akj.helpyou.activities.Odsay;

import static com.akj.helpyou.BuildConfig.odsay_api_key;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Subwaytell {
    public static String[] run(int startid, int endid) {
        String[] tel = new String[2];
        try {
            // ODsay Api Key 정보
            String apiKey = odsay_api_key;

            String urlInfo = "https://api.odsay.com/v1/api/subwayStationInfo?lang=0&stationID="+startid+"&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

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
                        if(line == null){
                            break;
                        }
                        JSONObject Subwayinfo = new JSONObject(line);
                        JSONObject Subwaytel = Subwayinfo.getJSONObject("result").getJSONObject("defaultInfo");
                        String Starttel = Subwaytel.getString("tel");
                        tel[0] = Starttel;
                    }
                    reader.close();
                }
                conn.disconnect();
            }

            String urlInfo2 = "https://api.odsay.com/v1/api/subwayStationInfo?lang=0&stationID="+endid+"&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

            // http 연결
            URL url2 = new URL(urlInfo2);
            HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();

            if (conn2 != null) {
                conn2.setConnectTimeout(10000);
                conn2.setRequestMethod("GET");

                int resCode = conn2.getResponseCode();
                int HTTP_OK = HttpURLConnection.HTTP_OK;

                if(resCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
                    String line2 = null;
                    while (true) {
                        line2 = reader.readLine();
                        if(line2 == null){
                            break;
                        }
                        JSONObject Subwayinfo = new JSONObject(line2);
                        JSONObject Subwaytel = Subwayinfo.getJSONObject("result").getJSONObject("defaultInfo");
                        String Starttel2 = Subwaytel.getString("tel");
                        tel[1] = Starttel2;
                    }

                    reader.close();
                }
                conn.disconnect();
            }

        } catch (Exception e) {

        }
        return tel;
    }
}
