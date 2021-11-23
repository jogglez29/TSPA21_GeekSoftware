package com.geeksoftware.vistas;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.PuntoRuta;
import com.geeksoftware.modelos.Ruta;
import com.google.android.gms.maps.model.LatLng;
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

    /**
     * Muestra las rutas que pueden dejar al usuario cerca de
     * su destino.
     * @param listaRutas Rutas óptimas.
     */
    void mostrarOpcionesDeRuta(List<Ruta> listaRutas);

    /**
     * Cambia el aspecto del marcador de la parada de subida.
     * @param parada Información de la parada a resaltar.
     */
    void resaltarParadaSubida(Parada parada);

    /**
     * Cambia el aspecto del marcador de la parada de bajda.
     * @param parada Información de la parada a resaltar.
     */
    void resaltarParadaBajada(Parada parada);

    /**
     * Resalta sobre el mapa el recorrido de una ruta.
     * @param puntos Secuencia de localizaciones que ayudan a trazar
     *               la ruta.
     * @param ruta Información de la ruta a mostrar su recorrido.
     */
    void mostrarRecorridoRuta(List<PuntoRuta> puntos, Ruta ruta);

    /**
     * Muestra todas las rutas existentes en la base de datos.
     * @param rutas Información de las rutas.
     */
    void mostrarRutas(List<Ruta> rutas);

    /**
     * Muestra un mensaje para indicar que se está
     * cerca de la parada de bajada.
     */
    void mostrarAlertaBajada();

    /**
     * Muestra un mensaje para indicar que se
     * active la ubicación del dispositivo.
     */
    void mostrarAlertaActivarUbicacion();
}
