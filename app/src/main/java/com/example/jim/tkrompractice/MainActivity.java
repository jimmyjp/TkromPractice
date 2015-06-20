package com.example.jim.tkrompractice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

	private EditText txtCodigo;
	private EditText txtNombre;
    private EditText txtAge;
    private EditText txtCity;
    private EditText txtSearch;
	private TextView txtResultado;
	
	private Button btnInsertar;
	private Button btnActualizar;
	private Button btnEliminar;
	private Button btnConsultar;
    private Button btnDrop;
    private Button btnFiltrar;
    private Button btnXml;
	
	private SQLiteDatabase db;

    public ArrayList<Usuario> listUsuarios = new ArrayList<Usuario>();
    public ArrayList<Usuario> listFiltrados = new ArrayList<Usuario>();

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Obtenemos las referencias a los controles
		txtCodigo = (EditText)findViewById(R.id.txtReg);
		txtNombre = (EditText)findViewById(R.id.txtVal);
        txtAge = (EditText)findViewById(R.id.txtAge);
        txtCity = (EditText)findViewById(R.id.txtCity);
        txtSearch = (EditText)findViewById(R.id.txtSearch);

		txtResultado = (TextView)findViewById(R.id.txtResultado);
		
		btnInsertar = (Button)findViewById(R.id.btnInsertar);
		btnActualizar = (Button)findViewById(R.id.btnActualizar);
		btnEliminar = (Button)findViewById(R.id.btnEliminar);
		btnConsultar = (Button)findViewById(R.id.btnConsultar);
        btnDrop = (Button)findViewById(R.id.btnDropTable);
        btnFiltrar = (Button)findViewById(R.id.btnFiltrar);
        btnXml = (Button)findViewById(R.id.btnShowXml);


        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh =
            new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
 
        db = usdbh.getWritableDatabase();
		
		btnInsertar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				//Recuperamos los valores de los campos de texto
				String cod = txtCodigo.getText().toString();
				String nom = txtNombre.getText().toString();
                String age = txtAge.getText().toString();
                String city = txtCity.getText().toString();
				
				//Alternativa 1: metodo sqlExec()
				//String sql = "INSERT INTO Usuarios (codigo,nombre) VALUES ('" + cod + "','" + nom + "') ";
				//db.execSQL(sql);

                if(cod.equals("") || nom.equals("") || age.equals("") || city.equals("")) {

                    Toast toast1 = Toast.makeText(getApplicationContext(), "Ningún campo puede quedar vacío", Toast.LENGTH_LONG);
                    toast1.show();

                } else {

                    Usuario usuario = new Usuario(cod, nom, age, city);
                    listUsuarios.add(usuario);

                    //Alternativa 2: metodo insert()
                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("codigo", cod);
                    nuevoRegistro.put("nombre", nom);
                    nuevoRegistro.put("edad", age);
                    nuevoRegistro.put("ciudad", city);

                    db.insert("Usuarios", null, nuevoRegistro);

                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "Elemento insertado en BBDD", Toast.LENGTH_LONG);

                    toast2.show();

                    txtCodigo.setText("");
                    txtNombre.setText("");
                    txtAge.setText("");
                    txtCity.setText("");

                }
			}
		});

        //Actualmente este botón actualiza el nombre en función de su código.
		btnActualizar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				//Recuperamos los valores de los campos de texto
				String cod = txtCodigo.getText().toString();
				String nom = txtNombre.getText().toString();
                String age = txtAge.getText().toString();
                String city = txtCity.getText().toString();

                ContentValues valores = new ContentValues();

                if(cod.equals("") ) {

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "El campo código no puede estar vacío", Toast.LENGTH_LONG);

                    toast1.show();
                } else if(!nom.equals("") || !age.equals("") || !city.equals("") ){

                    if (!nom.equals("")) valores.put("nombre", nom);
                    if (!age.equals("")) valores.put("edad", age);
                    if (!city.equals("")) valores.put("ciudad", city);

                    db.update("Usuarios", valores, "codigo=" + cod, null);

                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "El elemento de código "+cod+" ha sido actualizado", Toast.LENGTH_LONG);

                    toast2.show();

                }
                    //Alternativa 1: metodo sqlExec()
                    //String sql = "UPDATE Usuarios SET nombre='" + nom + "' WHERE codigo=" + cod;
                    //db.execSQL(sql);

                }

		});

        //Se elimina un elemento de la tabla a partir de su código. Lo vamos a modificar para que tambien elimine un
        //elemento a partir de su nombre.
		btnEliminar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				//Recuperamos los valores de los campos de texto
				String cod = txtCodigo.getText().toString();

                if(cod.equals("")) {

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Para eliminar un elemento has de introducir un código", Toast.LENGTH_LONG);
                    toast1.show();
                } else {

                    //Alternativa 1: metodo sqlExec()
                    //String sql = "DELETE FROM Usuarios WHERE codigo=" + cod;
                    //db.execSQL(sql);

                    //Alternativa 2: metodo delete()
                    int affectedRows = db.delete("Usuarios", "codigo=" + cod, null);

                    if(affectedRows > 0) {

                        Toast toast2 =
                                Toast.makeText(getApplicationContext(),
                                        "El elemento de código " + cod + " ha sido eliminado de la BBDD", Toast.LENGTH_LONG);
                        toast2.show();

                    }else {

                        Toast toast3 =
                                Toast.makeText(getApplicationContext(),
                                        "No se ha encontrado el elemento con código " + cod , Toast.LENGTH_LONG);
                        toast3.show();

                    }

                }
			}
		});

        btnDrop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                String sql = "DELETE from Usuarios";
                db.execSQL(sql);

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Se ha vaciado la tabla Usuarios", Toast.LENGTH_LONG);
                toast1.show();

            }
        });


        //Actualmente el botón consulta imprime todos los elementos de la tabla. Lo vamos a modificar
        //para que consulte en función de su código y/o nombre.
		btnConsultar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				//Alternativa 1: metodo rawQuery()
				Cursor c = db.rawQuery("SELECT codigo, nombre, edad, ciudad FROM Usuarios", null);

				//Recorremos los resultados para mostrarlos en pantalla
				txtResultado.setText("");

				if (c.moveToFirst()) {
				     //Recorremos el cursor hasta que no haya más registros
				     do {
				          String cod = c.getString(0);
				          String nom = c.getString(1);
                          String age = c.getString(2);
                          String city = c.getString(3);
				          
				          txtResultado.append(" " + cod + " - " + nom + " - "+ age + " - "+ city + "\n");
				     } while(c.moveToNext());
				} else {

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "La BBDD no tiene elementos que mostrar", Toast.LENGTH_LONG);
                    toast1.show();

                }
			}
		});


        //para filtrar filas de la tabla.
        btnFiltrar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                String code = txtCodigo.getText().toString();
                String name = txtNombre.getText().toString();
                String age = txtAge.getText().toString();
                String city = txtCity.getText().toString();
                String search = txtSearch.getText().toString();


                Intent intent = new Intent(getApplicationContext(), UsuariosFiltrados.class);
                intent.putExtra("Name", name);
                intent.putExtra("Age", age);
                intent.putExtra("City", city);
                intent.putExtra("Search", search);

                Log.d("MainActivity", "Iniciando UsuariosFiltrados Activity");

                startActivity(intent);
            }
        });

        //para probar el parseo XML en Sax.
        btnXml.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), ShowXmlData.class);

                Log.d("MainActivity", "Iniciando ShowXmlData Activity");

                startActivity(intent);
            }
        });
    }


}
