package com.example.jim.tkrompractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jim on 26/05/15.
 */
public class AdapterUsuarios extends ArrayAdapter<Usuario> {

    ArrayList<Usuario> mdatos;

    public AdapterUsuarios(Context context, ArrayList<Usuario> datos) {


        super(context, R.layout.listitem_usuario, datos);
        mdatos = datos;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listitem_usuario, null);

        TextView lblCode = (TextView)item.findViewById(R.id.LabelCode);
        lblCode.setText(mdatos.get(position).getCode());

        TextView lblName = (TextView)item.findViewById(R.id.LabelName);
        lblName.setText(mdatos.get(position).getName());

        return(item);
    }
}
