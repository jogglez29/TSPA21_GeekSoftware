package com.geeksoftware.vistas;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.Ruta;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Define las acciones que debe realizar la vista de la aplicación que contiene
 * el mapa.
 */
public interface MapActivityView {

    /**
     * Muestra todas las paradas de autobuses existentes sobre el mapa.
     * @param listaParadas Datos sobre cada parada de autobus.
     */
    void mostrarParadas(List<Parada> listaParadas);

    /**
     * Muestra las rutas que se detienen en una parada específica.
     * @param listaRutas Rutas que se detienen en la parada escogida.
     * @param marcador Parada seleccionada.
     */
    void mostrarInfoParada(List<Ruta> listaRutas, Marker marcador);

    /**
     * Muestra un mensaje de error cuando existe un inconveniente al cargar
     * las paradas de autobuses.
     */
    void mostrarErrorParadas();

    /**
     * Muestra un mensaje de error cuando existe un inconveniente al cargar
     * las rutas que se detienen en una parada.
     */
    void mostrarErrorInfoParada();
}
