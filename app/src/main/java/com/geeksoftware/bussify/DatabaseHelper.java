package com.geeksoftware.bussify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "bussify.db";
    public static final String TABLE_NAME = "paradas";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DESCRIPCION";
    public static final String COL_3 = "LATITUD";
    public static final String COL_4 = "LONGITUD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPCION TEXT, LATITUD REAL, LONGITUD REAL, UNIQUE(LATITUD,LONGITUD))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertarParada(String descripcion, double latitud, double longitud){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, descripcion);
        contentValues.put(COL_3, latitud);
        contentValues.put(COL_4, longitud);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public void registrarParadas(){
        insertarParada("Ruta 1",22.761301,-102.67053);
        insertarParada("Ruta 1",22.751152,-102.666829);
        insertarParada("Ruta 1",22.756438,-102.663113);
    }
}
