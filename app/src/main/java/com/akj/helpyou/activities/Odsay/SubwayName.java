package com.akj.helpyou.activities.Odsay;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SubwayName {
    // 이름을 받아오면 지하철 코드와 호선정보 넘기기
    public static void run() {
        String []Code = new String[5];
        String []LineNum = new String[5];
        try {

            // ODsay Api Key 정보
            String urlBuilder = "http://openAPI.seoul.go.kr:8088/756a7746487968773539456a4b4549/json/SearchInfoBySubwayNameService/1/5/동대문역사문화공원/";
        //  String urlBuilder = "http://openAPI.seoul.go.kr:8088/756a7746487968773539456a4b4549/json/SearchInfoBySubwayNameService/1/5/sName/";
            // http 연결
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd;

            // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);

            }
            JSONObject elvtData = new JSONObject(String.valueOf(sb));
            JSONArray row = elvtData.getJSONObject("SearchInfoBySubwayNameService").getJSONArray("row");
            Log.d("qqee", "qqee : " + row);
            for(int i=0 ; i<row.length(); i++) {
                Code[i] = row.getJSONObject(i).getString("FR_CODE");
                LineNum[i] = row.getJSONObject(i).getString("LINE_NUM");
            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

