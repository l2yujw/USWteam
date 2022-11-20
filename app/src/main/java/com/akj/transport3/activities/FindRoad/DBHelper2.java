package com.akj.transport3.activities.FindRoad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper2 extends SQLiteOpenHelper {


    public DBHelper2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER_INFO2 (_num INTEGER PRIMARY KEY AUTOINCREMENT, area TEXT, timestamp TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String area, String timestamp){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USER_INFO2 VALUES(null, '" + area + "', '" + timestamp + "');");
        db.close();
    }

    public void delete(String area){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM USER_INFO2 WHERE area='" + area + "';");
        db.close();
    }

    public Cursor getRouteList(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT area, timestamp FROM USER_INFO2 ORDER BY timestamp", null);
        return cursor;
    }
}

