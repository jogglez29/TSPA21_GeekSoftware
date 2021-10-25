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

/**
 * Manipula la base de datos directamente.
 */
public class SQLiteDataBase extends SQLiteOpenHelper implements ConectorBaseDatos {

    /** Nombre de la base de datos. */
    public static final String DATABASE_NAME = "bussify.db";

                    /** TABLA PARADA */
    /** Nombre de la tabla de las paradas de autobuses. */
    public static final String TABLA_PARADA = "parada";
    /** Nombre de la primer columna de la tabla Parada. */
    public static final String PARADA_COL_ID = "ID";
    /** Nombre de la segunda columna de la tabla Parada. */
    public static final String PARADA_COL_DESC = "DESCRIPCION";
    /** Nombre de la tercera columna de la tabla Parada.  */
    public static final String PARADA_COL_LAT = "LATITUD";
    /** Nombre de la cuarta columna de la tabla Parada.  */
    public static final String PARADA_COL_LONG = "LONGITUD";

                    /** TABLA RUTA */
    /** Nombre de la tabla de las rutas. */
    public static final String TABLA_RUTA = "ruta";
    /** Nombre de la primer columna de la tabla Ruta. */
    public static final String RUTA_COL_ID = "ID";
    /** Nombre de la segunda columna de la tabla Ruta. */
    public static final String RUTA_COL_NOM = "NOMBRE";
    /** Nombre de la tercera columna de la tabla Color. */
    public static final String RUTA_COL_COLOR = "COLOR";

                    /** Tabla PARADAS_RUTAS */
    /** Nombre de la tabla que establece relaciones entre paradas y rutas. */
    public static final String TABLA_PARADA_RUTA = "parada_rutas";
    /** Nombre de la primer columna de la tabla paradas_rutas. */
    public static final String PARADA_RUTA_COL_ID_PAR = "ID_PARADA";
    /** Nombre de la segunda columna de la tabla paradas_rutas. */
    public static final String PARADA_RUTA_COL_ID_RUTA = "ID_RUTA";

    /**
     * Define el constructor de la clase.
     * @param context Contexto a utilizarse
     */
    public SQLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLA_PARADA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPCION TEXT, LATITUD REAL, LONGITUD REAL, UNIQUE(LATITUD,LONGITUD))");
        sqLiteDatabase.execSQL("create table " + TABLA_RUTA + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT UNIQUE, COLOR TEXT DEFAULT '#ffffff')");
        sqLiteDatabase.execSQL("create table " + TABLA_PARADA_RUTA + "(ID_PARADA INTEGER, ID_RUTA INTEGER, FOREIGN KEY(ID_PARADA) REFERENCES PARADA(ID), FOREIGN KEY(ID_RUTA) REFERENCES RUTA(ID), PRIMARY KEY(ID_PARADA, ID_RUTA))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_PARADA_RUTA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_PARADA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_RUTA);
        onCreate(sqLiteDatabase);
    }

    @Override
    public boolean agregarRuta(Ruta ruta) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RUTA_COL_NOM, ruta.getNombre());
        contentValues.put(RUTA_COL_COLOR, ruta.getColor());
        long result = sqLiteDatabase.insert(TABLA_RUTA, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean agregarParada(Parada parada) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARADA_COL_DESC, parada.getDescripcion());
        contentValues.put(PARADA_COL_LAT, parada.getLatitud());
        contentValues.put(PARADA_COL_LONG, parada.getLongitud());
        long result = sqLiteDatabase.insert(TABLA_PARADA, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean agregarParadaRuta(Parada parada, Ruta ruta) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARADA_RUTA_COL_ID_PAR, parada.getId());
        contentValues.put(PARADA_RUTA_COL_ID_RUTA, ruta.getId());
        long result = sqLiteDatabase.insert(TABLA_PARADA_RUTA, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public List<Ruta> obtenerRutas() {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLA_RUTA, null);
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
            Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLA_PARADA, null);
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