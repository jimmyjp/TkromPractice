package com.example.jim.tkrompractice;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jim on 26/05/15.
 */
public class UsuariosFiltrados extends Activity {

    SQLiteDatabase db;
    ArrayList<Usuario> usuariosFiltrados;
    TextView info;
    ListView lstOpciones;
    String whereClause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios_filtrados);

        info = (TextView)findViewById(R.id.info);
        usuariosFiltrados = new ArrayList<Usuario>();
        whereClause="";

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);

        Log.d("UF Activity", "Conectando con BBDD");

        db = usdbh.getWritableDatabase();

        Intent ufintent = getIntent();
        Bundle b = ufintent.getExtras();

        if(b!=null)
        {
            String name =(String) b.get("Name");
            String age = (String) b.get("Age");
            String city = (String) b.get("City");
            String search = (String) b.get("Search");

            Log.d("UsuariosFiltrados.java", "Buscamos por Nombre= "+name+", Edad="+age+", Ciudad="+city);

            if(!name.equals("")){

                whereClause = " WHERE nombre='"+name+"'";
            }

            if(!age.equals("")){

                if(!whereClause.equals("")) whereClause+= " AND edad='"+age+"'";
                else whereClause=" WHERE edad='"+age+"'";
            }

            if(!city.equals("")){

                if(!whereClause.equals("")) whereClause+= " AND ciudad='"+city+"'";
                else whereClause=" WHERE ciudad='"+city+"'";
            }

            if(!search.equals("")){

                if(!whereClause.equals("")) whereClause+= " AND ciudad LIKE '%" +search+ "%'";
                else whereClause=" WHERE ciudad LIKE '%" +search+ "%'";
            }


        }

        Log.d("UF Activity", "Where = "+whereClause);

        Cursor c = db.rawQuery("SELECT codigo, nombre, edad, ciudad FROM Usuarios"+whereClause, null);


        //Nos aseguramos de que al menos hay una fila coincidente en BBDD.
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String cod = c.getString(0);
                String nom = c.getString(1);
                String ed = c.getString(2);
                String ciu = c.getString(3);

                Log.d("ArrayList element", "Codigo: "+cod+", Nombre:"+nom+", Edad:"+ed+", Ciudad:"+ciu+"\n");

                //Creamos un objeto Usuario cada vez que el cursor haga match.
                Usuario usuario = new Usuario(cod, nom, ed, ciu);
                usuariosFiltrados.add(usuario);

            } while(c.moveToNext());

            Log.d("UF Activity", usuariosFiltrados.size()+" filtrados");

            AdapterUsuarios adaptador =
                    new AdapterUsuarios(this, usuariosFiltrados);

            lstOpciones = (ListView)findViewById(R.id.LstOpciones);

            lstOpciones.setAdapter(adaptador);

            lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                    //Alternativa 1: obtenemos el titulo del objeto Titular

                    Usuario userSelected = ((Usuario)a.getItemAtPosition(position));

                    //String codeSelected = ((Usuario)a.getItemAtPosition(position)).getCode();

                    //Necesitas Parcelable para pasar objetos entre actividades.

                    Intent intent = new Intent(getApplicationContext(), FichaUsuario.class);
                    intent.putExtra("usuario", userSelected);

                    startActivity(intent);

                }
            });

        } else info.setText("No se han encontrado elementos en BBDD");





    }
}
