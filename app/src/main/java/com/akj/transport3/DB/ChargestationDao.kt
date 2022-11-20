package com.akj.transport3.DB

import androidx.room.Dao
import androidx.room.Query


@Dao
interface ChargestationDao {


    //좌표를 중심으로 넓이가 4km^2인 정사각형의 범위안에 있는 곳만 검색
    @Query("SELECT * FROM chargestation WHERE " +
            "((:lat-0.01)<=Latitude AND (:lng-0.01)<=Longitude) AND" +
            "(Latitude<=(:lat+0.01) AND Longitude<=(:lng+0.01)) ")
    fun FindStationin1(lat:Double,lng:Double):List<Chargestation>
}