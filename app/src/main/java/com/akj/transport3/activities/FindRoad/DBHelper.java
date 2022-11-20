package com.akj.transport3.activities.FindRoad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER_INFO (_num INTEGER PRIMARY KEY AUTOINCREMENT, startpoint TEXT, endpoint TEXT, timestamp TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String startpoint, String endpoint, String timestamp){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USER_INFO VALUES(null, '" + startpoint + "', '" + endpoint + "', '" + timestamp + "');");
        db.close();
    }

    public void delete(String startpoint){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM USER_INFO WHERE startpoint='" + startpoint + "';");
        db.close();
    }
    
    public Cursor getRouteList(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT startpoint, endpoint, timestamp FROM USER_INFO ORDER BY timestamp", null);
        return cursor;
    }
}
