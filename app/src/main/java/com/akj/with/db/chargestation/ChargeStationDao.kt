package com.akj.with.db.chargestation

import androidx.room.Dao
import androidx.room.Query


@Dao
interface ChargeStationDao {

    //좌표를 중심으로 넓이가 100km^2인 정사각형의 범위안에 있는 곳만 검색
    @Query("SELECT * FROM chargestation WHERE " +
            "((:lat-0.05)<=Latitude AND (:lng-0.05)<=Longitude) AND" +
            "(Latitude<=(:lat+0.05) AND Longitude<=(:lng+0.05)) ")
    fun findStationIn100km(lat:Double, lng:Double):List<ChargeStation>
}