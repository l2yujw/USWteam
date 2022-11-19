package com.akj.helpyou.activities.Odsay;

import static com.akj.helpyou.BuildConfig.odsay_api_key;

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
import java.net.URLEncoder;

public class PathGraphic {

    public static void run(String mapObj[], int type[]) {
        Double[][] MapXY = new Double[10][1000];
        int [] count = new int[10];
        Log.d("xyPos", "real : " + mapObj[0]);
        int p = 0;
        try {
            String[] Pathxy = new String[20];

            Log.d("xyPos", "real : " + mapObj[1]);
            Log.d("xyPos", "real : " + mapObj.length);
            // ODsay Api Key 정보
            for (int i = 0; i < mapObj.length; i++) {
                String apiKey = odsay_api_key;
                String urlInfo = "https://api.odsay.com/v1/api/loadLane?lang=0&mapObject=126:37@" + mapObj[i] + "&apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

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
                            int l = 0;
                            for(int h=0; h<xyPos.length(); h++){
                                Double x = xyPos.getJSONObject(h).getDouble("x");
                                Double y = xyPos.getJSONObject(h).getDouble("y");
                                x = 126+x;
                                y = 37+y;
                                MapXY[p][l++] = x;
                                MapXY[p][l++] = y;
                            }
                            count[p] = xyPos.length()*2;
                            for(int k=0; k< count[p]; k++) {

                                    Log.d("xyPos", "MAPXY : " + MapXY[p][k]);

                            }
                            p++;
                            Log.d("xyPos", "real : " + xyPos);


                        }
                        reader.close();
                    }
                    conn.disconnect();
                }
            }

            // 여기서 데이터 넘김 ex:) MapLine(MapXY,Type,count)   MapXY[][], Type[], count[]  count는 MapXY[][]의 배열 개수이 형태고 저번에 메모장에 적힌대로
            // for(i=0 i<type.length i++){ if(type[i] == 1){ 지하철 실행 for (j=0; j<count[i]; j++){ MapXY[i][j] 이런식으로

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

