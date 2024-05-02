package com.akj.with.activities.odsay;

//import static com.akj.helpyou.BuildConfig.odsay_api_key;

import android.util.Log;

import com.akj.with.activities.result.ResultRouteActivity;

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
import java.net.URLEncoder;
public class PathGraphic {

    public static void run(String mapObj[], int j) {
        double[][][] MapXY = new double[5][10][1000];
        int[][] count = new int[5][30];
        int[][] type = new int[5][30];

        try {
            String apiKey = "odsay_api_key";
            String urlInfo = "https://api.odsay.com/v1/api/loadLane?lang=0&mapObject=126:37@" + mapObj[j] + "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

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
                        Log.e("BusTimetable", "real : " + real.length());
                        for (int i = 0; i < real.length(); i++) {
                            JSONArray xyPos = real.getJSONObject(i).getJSONArray("section").getJSONObject(0).getJSONArray("graphPos");
                            int l = 0;
                            for (int h = 0; h < xyPos.length(); h++) {
                                Log.e("BusTimetable", "xylength : " + xyPos.length());
                                double x = xyPos.getJSONObject(h).getDouble("x");
                                double y = xyPos.getJSONObject(h).getDouble("y");
                                x = 126 + x;
                                y = 37 + y;
                                MapXY[j][i][l++] = x;
                                MapXY[j][i][l++] = y;
                            }
                            type[j][i] = real.getJSONObject(i).getInt("class");
                            count[j][i] = xyPos.length() * 2;


                            ResultRouteActivity.MapLineData(MapXY, type, count, j, i);
                        }

                    }
                    reader.close();
                }
                conn.disconnect();
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

