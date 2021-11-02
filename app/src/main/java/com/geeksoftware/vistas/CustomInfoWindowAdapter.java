package com.geeksoftware.vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geeksoftware.modelos.Ruta;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private final Context mContext;
    private List<Ruta> listaRutas;

    public CustomInfoWindowAdapter(Context context, List<Ruta> listaRutas) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
        this.listaRutas = listaRutas;
    }

    private void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView tvTitle = view.findViewById(R.id.title);

        if(!title.equals("")) {
            tvTitle.setText(title);
        }

        ListView listSnippet = view.findViewById(R.id.list_view_snippet);
        listSnippet.setAdapter(new BottomSheetListAdapter(
                mContext, listaRutas, R.layout.list_snippet));
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }
}
