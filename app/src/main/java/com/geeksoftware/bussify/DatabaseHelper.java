package com.geeksoftware.bussify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Tabla PARADAS
    public static final String DATABASE_NAME = "bussify.db";
    public static final String TABLE_NAME = "parada";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DESCRIPCION";
    public static final String COL_3 = "LATITUD";
    public static final String COL_4 = "LONGITUD";

    // Tabla RUTA
    public static final String TABLE_RUTA_NAME = "ruta";
    public static final String RUTA_COL_1 = "ID";
    public static final String RUTA_COL_2 = "NOMBRE";
    public static final String RUTA_COL_3 = "COLOR";

    // Tabla PARADA_RUTAS;
    public static final String TABLE_PARADA_RUTA_NAME = "parada_rutas";
    public static final String PARADA_RUTA_COL_1 = "ID_PARADA";
    public static final String PARADA_RUTA_COL_2 = "ID_RUTA";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPCION TEXT, LATITUD REAL, LONGITUD REAL, UNIQUE(LATITUD,LONGITUD))");
        sqLiteDatabase.execSQL("create table " + TABLE_RUTA_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT UNIQUE, COLOR TEXT DEFAULT '#ffffff')");
        sqLiteDatabase.execSQL("create table " + TABLE_PARADA_RUTA_NAME + "(ID_PARADA INTEGER, ID_RUTA INTEGER, FOREIGN KEY(ID_PARADA) REFERENCES PARADA(ID), FOREIGN KEY(ID_RUTA) REFERENCES RUTA(ID), PRIMARY KEY(ID_PARADA, ID_RUTA))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PARADA_RUTA_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RUTA_NAME);

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

    public boolean insertarRuta(String nombre, String color){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RUTA_COL_2, nombre);
        contentValues.put(RUTA_COL_3, color);
        long result = sqLiteDatabase.insert(TABLE_RUTA_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertarParadaRuta(int id_parada, int id_ruta){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARADA_RUTA_COL_1, id_parada);
        contentValues.put(PARADA_RUTA_COL_2, id_ruta);
        long result = sqLiteDatabase.insert(TABLE_PARADA_RUTA_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataParadas(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    public Cursor getAllDataRutas(int idParada){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res2 = sqLiteDatabase.rawQuery("SELECT * FROM parada_rutas pr" + " JOIN Ruta r " + " ON pr.id_ruta = r.id WHERE pr.id_parada =" +  idParada, null);
        return res2;
    }

    public Cursor getAllDataParadaRutas(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_PARADA_RUTA_NAME, null);
        return res;
    }

    public void registrarParadas(){
        insertarParada("Ruta 1",22.761301,-102.67053);
        insertarParada("Ruta 1",22.751152,-102.666829);
        insertarParada("Ruta 1",22.756438,-102.663113);
    }

    public void registrarRutas(){
        insertarRuta("Ruta 1","#02baed");
        insertarRuta("Ruta 2 Zacatecas","#037b1a");
        insertarRuta("Ruta 2 Guadalupe","#037b1a");
        insertarRuta("Ruta 3","#6a6c69");
        insertarRuta("Ruta 4","#292cb1");
        insertarRuta("Ruta 5","#fee800");
        insertarRuta("Ruta 6","#f7a4d0");
        insertarRuta("Ruta 7","#8f0c12");
        insertarRuta("Ruta 8","#de8239");
        insertarRuta("Ruta 14","#a95942");
        insertarRuta("Ruta 15","#7117aa");
        insertarRuta("Ruta 16","#5e247b");
        insertarRuta("Ruta 17","#44ba16");
        insertarRuta("Transportes de Guadalupe","#8e180e");
        insertarRuta("Transportes de Guadalupe Tierra y Libertad","#8e180e");
    }

    public void registrarParadaRutas(){
        insertarParadaRuta(1,1);
        insertarParadaRuta(1,3);
        insertarParadaRuta(1,5);
        insertarParadaRuta(2,1);
        insertarParadaRuta(3,1);
    }
}
