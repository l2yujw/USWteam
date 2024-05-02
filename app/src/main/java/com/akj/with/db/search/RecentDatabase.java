package com.akj.with.db.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RecentDatabase extends SQLiteOpenHelper {

    public RecentDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE RECENT (_num INTEGER PRIMARY KEY AUTOINCREMENT, area TEXT, timeStamp TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String area, String timeStamp){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RECENT VALUES(null, '" + area + "', '" + timeStamp + "');");
        db.close();
    }

    public void delete(String area){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM RECENT WHERE area='" + area + "';");
        db.close();
    }

    public Cursor getRouteList(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT area, timeStamp FROM RECENT ORDER BY timeStamp", null);
        return cursor;
    }
}

