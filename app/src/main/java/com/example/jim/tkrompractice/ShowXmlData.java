package com.example.jim.tkrompractice;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class ShowXmlData extends Activity {

    TextView txtShow;
    private List<Noticia> noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_xml_data);

        txtShow = (TextView)findViewById(R.id.txtShow);

        //Carga del XML de forma asíncrona.
        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute("http://www.europapress.es/rss/rss.aspx");
    }

    //Tarea Asíncrona para cargar un XML en segundo plano
    public class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            XmlParserSax saxparser =
                    new XmlParserSax(params[0]);

            noticias = saxparser.parse();

            return true;
        }

        //Aquí meteríamos en la BBDD la lista noticias para que cuando pulsemos en
        //buscar ya podamos filtrar.
        protected void onPostExecute(Boolean result) {

            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            txtShow.setText("");
            for(int i=0; i<noticias.size(); i++)
            {
                txtShow.setText(
                        txtShow.getText().toString() +
                                System.getProperty("line.separator") +
                                noticias.get(i).getTitulo());
            }
        }
    }

}
