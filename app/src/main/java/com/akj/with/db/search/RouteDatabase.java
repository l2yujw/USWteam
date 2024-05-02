package com.akj.with.db.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RouteDatabase extends SQLiteOpenHelper {

    public RouteDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ROUTE (_num INTEGER PRIMARY KEY AUTOINCREMENT, startPoint TEXT, endPoint TEXT, timeStamp TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String startPoint, String endPoint, String timeStamp){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ROUTE VALUES(null, '" + startPoint + "', '" + endPoint + "', '" + timeStamp + "');");
        db.close();
    }

    public void delete(String startPoint){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM ROUTE WHERE startpoint='" + startPoint + "';");
        db.close();
    }

    public Cursor getRouteList(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT startpoint, endpoint, timeStamp FROM ROUTE ORDER BY timeStamp", null);
        return cursor;
    }
}
