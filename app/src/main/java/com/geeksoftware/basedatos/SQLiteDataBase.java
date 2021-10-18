package com.geeksoftware.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.ParadaRuta;
import com.geeksoftware.modelos.Ruta;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDataBase extends SQLiteOpenHelper implements ConectorBaseDatos {
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

    public SQLiteDataBase(Context context) {
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

    @Override
    public boolean agregarRuta(Ruta ruta) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RUTA_COL_2, ruta.getNombre());
        contentValues.put(RUTA_COL_3, ruta.getColor());
        long result = sqLiteDatabase.insert(TABLE_RUTA_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean agregarParada(Parada parada) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, parada.getDescripcion());
        contentValues.put(COL_3, parada.getLatitud());
        contentValues.put(COL_4, parada.getLongitud());
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean agregarParadaRuta(Parada parada, Ruta ruta) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARADA_RUTA_COL_1, parada.getId());
        contentValues.put(PARADA_RUTA_COL_2, ruta.getId());
        long result = sqLiteDatabase.insert(TABLE_PARADA_RUTA_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public List<Ruta> obtenerRutas() {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_RUTA_NAME, null);
            List<Ruta> listaRutas = new ArrayList<>();

            while (res.moveToNext()) {
                Integer id = res.getInt(0);
                String nombre = res.getString(1);
                String color = res.getString(2);
                listaRutas.add(new Ruta(id, nombre, color));
            }
            return listaRutas;

        } catch (SQLiteException ex) {
            return null;
        }
    }

    @Override
    public List<Parada> obtenerParadas() {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
            List<Parada> listaParadas = new ArrayList<>();

            while (res.moveToNext()) {
                Integer id = res.getInt(0);
                String descripcion = res.getString(1);
                Double latitud = res.getDouble(2);
                Double longitud = res.getDouble(3);
                listaParadas.add(new Parada(id, latitud, longitud, descripcion));
            }
            return listaParadas;

        } catch (SQLiteException ex) {
            return null;
        }
    }

    @Override
    public List<ParadaRuta> obtenerParadasRutas() {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM parada_rutas", null);
            List<ParadaRuta> listaParadasRutas = new ArrayList<>();

            while (res.moveToNext()) {
                Integer idParada = res.getInt(0);
                Integer idRuta = res.getInt(1);
                listaParadasRutas.add(new ParadaRuta(idParada, idRuta));
            }
            return listaParadasRutas;

        } catch (SQLiteException ex) {
            return null;
        }
    }

    @Override
    public List<Ruta> obtenerRutasPorParada(Integer idParada) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM parada_rutas pr" + " JOIN Ruta r "
                    + " ON pr.id_ruta = r.id WHERE pr.id_parada =" +  idParada, null);
            List<Ruta> listaRutasPorParada = new ArrayList<>();

            while (res.moveToNext()) {
                Integer idRuta = res.getInt(1);
                String nombre = res.getString(3);
                String color = res.getString(4);
                listaRutasPorParada.add(new Ruta(idRuta, nombre, color));
            }
            return listaRutasPorParada;

        } catch (SQLiteException ex) {
            return null;
        }
    }
}
