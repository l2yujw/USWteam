package com.akj.helpyou.db.chargestation

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChargeStation::class], version = 2)
abstract class ChargeStationDatabase : RoomDatabase() {
    abstract fun chargeStationDao(): ChargeStationDao

    companion object {

        @Volatile
        private var INSTANCE: ChargeStationDatabase? = null

        fun getInstance(context: Context): ChargeStationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ChargeStationDatabase::class.java, "chargeStation.db"
            )
                .createFromAsset("chargeStation.db")
                .build()


    }

}