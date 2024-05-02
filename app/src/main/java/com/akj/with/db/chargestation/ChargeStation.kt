package com.akj.with.db.chargestation

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chargeStation")
data class ChargeStation(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Order")
    val order:Int,

    @ColumnInfo(name = "Name")
    val name:String?,

    @ColumnInfo(name = "Address")
    val address:String?,

    @ColumnInfo(name = "Latitude")
    val lat:Double?,

    @ColumnInfo(name = "Longitude")
    val lng:Double?
)
