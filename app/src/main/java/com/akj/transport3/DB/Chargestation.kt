package com.akj.transport3.DB

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chargestation")
data class Chargestation(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="Order")
    val order:Int,
    @NonNull
    @ColumnInfo(name = "Name")
    val name:String?,
    @NonNull
    @ColumnInfo(name = "Address")
    val address:String?,
    @NonNull
    @ColumnInfo(name = "Latitude")
    val lat:Double?,
    @NonNull
    @ColumnInfo(name = "Longitude")
    val lng:Double?
)
