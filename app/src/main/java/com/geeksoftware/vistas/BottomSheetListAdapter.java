package com.geeksoftware.vistas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geeksoftware.modelos.Ruta;

import java.util.List;

/**
 * Define la forma de almacenamiento y extracción de datos que tendrán
 * una lista de rutas.
 */
public class BottomSheetListAdapter extends BaseAdapter {

    /** Guarda el contexto que hace uso del adaptador. */
    private Context context;
    /** Guarda los elementos de la lista. */
    private List<?> items;
    /** Guarda el layout a utilizar cuando la lista se vaya a mostrar. */
    private int layout;

    /**
     * Define el constructor del adaptador de lista.
     * @param context Contexto que lo manda llamar.
     * @param items Elementos a guardar.
     * @param layout Layout a utilizar.
     */
    public BottomSheetListAdapter(Context context, List<?> items, int layout) {
        this.context = context;
        this.items = items;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(layout, parent, false);
        }

        // get current item to be displayed
        Ruta ruta = (Ruta) getItem(position);

        // get the TextView for item name and item description
        TextView txtViewNombreRuta = convertView.findViewById(R.id.text_view_nombre_ruta);
        txtViewNombreRuta.setText(ruta.getNombre());
        View viewColorRuta = convertView.findViewById(R.id.view_color_ruta);
        int color = Color.parseColor(ruta.getColor());
        viewColorRuta.setBackgroundColor(color);

        // returns the view for the current row
        return convertView;
    }
}

