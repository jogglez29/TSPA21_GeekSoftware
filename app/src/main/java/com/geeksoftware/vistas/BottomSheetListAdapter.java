package com.geeksoftware.vistas;

import android.content.Context;
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
        Ruta currentItem = (Ruta) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = convertView.findViewById(R.id.text_view_item_name);
//        TextView textViewItemDescription = (TextView)
//                convertView.findViewById(R.id.text_view_item_description);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getNombre());

        // returns the view for the current row
        return convertView;
    }
}

