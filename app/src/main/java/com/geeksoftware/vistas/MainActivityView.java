package com.geeksoftware.vistas;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.Ruta;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public interface MainActivityView {

    void mostrarParadas(List<Parada> listaParadas);
    void mostrarInfoParada(List<Ruta> listaRutas, Marker marcador);
    void mostrarErrorParadas();
    void mostrarErrorInfoParada();
}
