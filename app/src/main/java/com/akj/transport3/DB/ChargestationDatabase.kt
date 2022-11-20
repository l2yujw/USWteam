package com.akj.transport3.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Chargestation::class], version = 2)
abstract class ChargestationDatabase:RoomDatabase() {
    abstract fun ChargestationDao():ChargestationDao

    companion object{

        @Volatile private var INSTANCE: ChargestationDatabase? = null

        fun getInstance(context: Context): ChargestationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ChargestationDatabase::class.java, "chargestation.db")
                .createFromAsset("chargestation.db")
                .build()


    }

}