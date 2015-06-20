package com.example.jim.tkrompractice;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;


public class FichaUsuario extends Activity {

    private TextView labelCode;
    private TextView labelName;
    private TextView labelAge;
    private TextView labelCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ficha_usuario);

        labelCode = (TextView)findViewById(R.id.labelCodeFicha);
        labelName = (TextView)findViewById(R.id.labelNameFicha);
        labelAge = (TextView)findViewById(R.id.labelAgeFicha);
        labelCity = (TextView)findViewById(R.id.labelCityFicha);

        Usuario usuario = getIntent().getParcelableExtra("usuario");

        //Priimero rellenamos los contenidos de cada pestaña.
        labelCode.setText(usuario.getCode());
        labelName.setText(usuario.getName());
        labelAge.setText(usuario.getAge());
        labelCity.setText(usuario.getCity());


        //Implementamos el funcionamiento de las pestañas.
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Nombre");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Edad");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Localidad");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pesta�a: " + tabId);
            }
        });


    }




}
