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


public class BottomSheetListAdapter extends BaseAdapter {

    private Context context;
    private List<Ruta> items;

    //public constructor
    public BottomSheetListAdapter(Context context, List<Ruta> items) {
        this.context = context;
        this.items = items;
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
                    inflate(R.layout.layout_list_view_row_items, parent, false);
        }

        // get current item to be displayed
        Ruta ruta = (Ruta) getItem(position);

        // get the TextView for item name and item description
        TextView txtViewNombreRuta = convertView.findViewById(R.id.text_view_nombre_ruta);
        txtViewNombreRuta.setText(ruta.getNombre());
        View viewColorRuta = (View) convertView.findViewById(R.id.view_color_ruta);
        int color = Color.parseColor(ruta.getColor());
        viewColorRuta.setBackgroundColor(color);

        // returns the view for the current row
        return convertView;
    }
}

