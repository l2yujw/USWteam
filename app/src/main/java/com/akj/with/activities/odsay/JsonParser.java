package com.akj.with.activities.odsay;
import android.util.Log;


import androidx.annotation.NonNull;

import com.akj.with.activities.odsay.dto.BusDto;
import com.akj.with.activities.odsay.dto.SubwayDto;
import com.akj.with.activities.odsay.dto.WalkDto;
import com.akj.with.activities.result.ResultRouteActivity;
import com.akj.with.activities.result.ResultRouteDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static void jsonParser(String resultJson) {
        String[][][] Pass = new String[5][20][1000];
        String[] mapObj = new String[10];

        ArrayList<DataKeyword> trafficDataList = new ArrayList<>();
        List<WalkDto> walkDtoList = new ArrayList<>();
        List<BusDto> busDtoList = new ArrayList<>();
        List<SubwayDto> subwayDtoList = new ArrayList<>();

        String[] Tel;
        String[] XY;
        String[] bus;
        String low = null;
        String BCompany;
        int busArrival = 0;
        try {
            JSONObject odsayData = new JSONObject(resultJson);
            JSONArray path = odsayData.getJSONObject("result").getJSONArray("path");

            Integer trafficCount = path.getJSONObject(0).getJSONArray("subPath").length();
            int pth;
            int i;
            if (path.length() > 5) {
                pth = 5;
            } else {
                pth = path.length();
            }
            for (int j = 0; j < pth; j++) {

                String totalTime = path.getJSONObject(j).getJSONObject("info").getString("totalTime");
                String payment = path.getJSONObject(j).getJSONObject("info").getString("payment");
                mapObj[j] = path.getJSONObject(j).getJSONObject("info").getString("mapObj");
                PathGraphic.run(mapObj, j);

                for (i = 0; i < trafficCount; i++) {
                    Integer trafficType = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("trafficType");
                    if (trafficType == 3) { //도보
                        WalkDto walkDto = getWalkDto(path, j, i, totalTime, payment);
                        walkDtoList.add(walkDto);
//                        ResultRouteActivity.resdata(walkDtoList);
                    }
                    if (trafficType == 2) { //버스

                        JSONArray pass = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONObject("passStopList").getJSONArray("stations");

                        for (int z = 0; z < pass.length(); z++) {
                            Pass[j][i][z] = pass.getJSONObject(z).getString("stationName");
                        }

                        Integer startCode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("startID");
                        String busNo = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONArray("lane").getJSONObject(0).getString("busNo");
                        if (BusTimetable.run(startCode, busNo) != null) {
                            bus = BusTimetable.run(startCode, busNo);
                            low = bus[1];
                            busArrival = Integer.parseInt(bus[0]);
                            Log.d("busdata", "버스도착시간 : " + busArrival + "저상버스유무 : " + low);
                        }

                        BusDto busDto = getBusDto(path, j, i, busNo, low, busArrival);
                        busDtoList.add(busDto);
//                        ResultRouteActivity.resdata(busDtoList);
                    }
                    if (trafficType == 1) { //지하철
                        SubwayDto subwayDto = getSubwayDto(path, j, i, Pass);
                        subwayDtoList.add(subwayDto);
//                        ResultRouteActivity.resdata(subwayDtoList);
                    }
                }
            }
            ResultRouteDetailActivity.walkResult(walkDtoList);
            ResultRouteDetailActivity.busResult(busDtoList);
            ResultRouteDetailActivity.subwayResult(subwayDtoList);


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    @NonNull
    private static WalkDto getWalkDto(JSONArray path, int j, int i, String totalTime, String payment) throws JSONException {
        Integer distance = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("distance");
        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");

        WalkDto walkDto = new WalkDto(
                "도보", distance.toString(), secTime, 0, " ", 3, totalTime, payment, 0
        );
        return walkDto;
    }

    @NonNull
    private static BusDto getBusDto(JSONArray path, int j, int i, String busNo, String low, int busArrival) throws JSONException {
        String busStart = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startName");
        String busEnd = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endName");
        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
        Integer subwayCount = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("stationCount");
        String busID = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startArsID");
        String startX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startX");
        String startY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startY");
        String endX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endX");
        String endY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endY");


        BusDto busDto = new BusDto(busNo, busStart, busEnd + "  ", secTime, subwayCount, busID,
                " ", 3, startX, startY, endX, endY, low, busArrival
        );
        return busDto;
    }

    @NonNull
    private static SubwayDto getSubwayDto(JSONArray path, int j, int i, String[][][] Pass) throws JSONException {
        String[] Tel;
        String[] XY;
        String subwayStart = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startName");
        String subwayEnd = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endName");
        String startX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startX");
        String startY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("startY");
        String endX = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endX");
        String endY = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getString("endY");
        Integer secTime = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
        Integer subwayCount = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("stationCount");
        Integer subwayNo = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONArray("lane").getJSONObject(0).getInt("subwayCode");
        Integer startCode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("startID");
        Integer endCode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("endID");
        Integer wayCode = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getInt("wayCode");

        JSONArray pass = path.getJSONObject(j).getJSONArray("subPath").getJSONObject(i).getJSONObject("passStopList").getJSONArray("stations");
        for (int z = 0; z < pass.length(); z++) {
            Pass[j][i][z] = pass.getJSONObject(z).getString("stationName");

        }
        Tel = Subwaytell.run(startCode, endCode);
        XY = ElevatorLocation.run(subwayStart, subwayEnd);
        if (XY[0] != null) {
            startX = XY[0];
            startY = XY[1];
            endX = XY[2];
            endY = XY[3];
        }

        SubwayDto subwayDto = new SubwayDto(
                subwayNo + "호선", subwayStart + "역", subwayEnd + "역  ", secTime, subwayCount,
                " ", wayCode, startX, startY, endX, endY, 0, Tel[0], Tel[1]
        );
        return subwayDto;
    }
}
