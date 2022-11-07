package com.akj.helpyou.activities.Odsay;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BusCompany {
    public static String run(String busNo, int citycode) {
        String name = null;
        try {
            // ODsay Api Key 정보
            String apiKey = "KBZOsvKhbug6iLyW4x9sOPH+YLTTWKjn2S9oPW7tXiQ";

            String urlInfo = "https://api.odsay.com/v1/api/searchBusLane?lang=0&busNo="+busNo+"&CID="+citycode+"&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

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
                        JSONObject companyinfo = new JSONObject(line);
                        JSONArray cpinfo = companyinfo.getJSONObject("result").getJSONArray("lane");

                        for(int i=0; i<cpinfo.length(); i++){
                            String bN = cpinfo.getJSONObject(i).getString("busNo");

                            Log.d("BusCompany11","버스번호 : "+ bN);
                            if(busNo.equals(bN)) {
                                name = cpinfo.getJSONObject(i).getString("busCompanyNameKor");
                                Log.d("BusCompany11","운수회사 명 : "+ name);
                                return name;
                            }
                        }
                    }
                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {

        }
        return name;
    }
}

