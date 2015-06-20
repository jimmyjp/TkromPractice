package com.example.jim.tkrompractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class UsuariosSQLiteHelper extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE Usuarios (codigo TEXT, nombre TEXT, edad TEXT, ciudad TEXT)";
 
    public UsuariosSQLiteHelper(Context contexto, String nombre,
                               CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creacion de la tabla
        db.execSQL(sqlCreate);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, 
                          int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqui utilizamos directamente
        //      la opcion de eliminar la tabla anterior y crearla de nuevo
        //      vacia con el nuevo formato.
        //      Sin embargo lo normal sera que haya que migrar datos de la
        //      tabla antigua a la nueva, por lo que este metodo deberia
        //      ser mas elaborado.
 
        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
 
        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }
}

