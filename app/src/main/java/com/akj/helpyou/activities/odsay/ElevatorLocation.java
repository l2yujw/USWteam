package com.akj.helpyou.activities.odsay;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
// 엘리베이터 위치 정보 Json 받아오기 + 여기서 파싱
public class ElevatorLocation {
    public static String[] run(String sName, String eName) {
        String[] xy = new String[4];
        try {
            String Sxy = null;
            String Exy = null;
            // ODsay Api Key 정보
            String apiKey = "756a7746487968773539456a4b4549";

            String urlBuilder = "http://openapi.seoul.go.kr:8088/715345784a79687733384a674e4345/json/tbTraficElvtr/1/643/";
            Log.d("qqww", "qqww : " + sName + " " + eName);
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
            JSONArray row = elvtData.getJSONObject("tbTraficElvtr").getJSONArray("row");
           // Log.d("qqww", "qqww : " + row);

            for(int i=0; i<row.length(); i++) {
                String SWNM = row.getJSONObject(i).getString("SW_NM");


                if (sName.equals(SWNM)){
                    Sxy = row.getJSONObject(i).getString("NODE_WKT");
                    String Sx = Sxy.substring(6,22);
                    String Sy = Sxy.substring(24,40);
                    xy[0] = Sx;
                    xy[1] = Sy;
                }
                if (eName.equals(SWNM)){
                    Exy = row.getJSONObject(i).getString("NODE_WKT");
                    String Ex = Exy.substring(6,22);
                    String Ey = Exy.substring(24,40);
                    xy[2] = Ex;
                    xy[3] = Ey;
                }
            }

            Log.d("qwq ", "SX : "+ xy[0] + " SY : " + xy[1] + " EX : " + xy[2] + " EY : " +xy[3]);
            //SubwayName.run();
            return xy;

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
        return null;
    }
}
