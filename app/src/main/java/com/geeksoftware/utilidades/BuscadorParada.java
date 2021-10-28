package com.geeksoftware.utilidades;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.Ruta;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import com.google.maps.android.SphericalUtil;

/**
 * Realiza búsquedas de paradas de autobuses basado
 * en diferentes criterios.
 */
public class BuscadorParada {

    /**
     * Encuentra la o las paradas de autobuses más cercanas a la localización dada.
     * @param localizacion Coordenadas del lugar a buscar
     *                     paradas cercanas.
     * @param paradas Paradas existentes en el mapa.
     * @return Regresa una lista de paradas cercanas.
     */
    public static List<Parada> buscarParadasCercanas(LatLng localizacion, List<Parada> paradas) {
        // Guarda las paradas de autobuses más cercanas al destino.
        List<Parada> paradasCercanas = new ArrayList<>();
        // Guarda la parada de autobus más cercana al destino.
        Parada paradaMasCercana = null;
        // Guarda la menor distancia que hay entre el destino y alguna parada de autobus.
        Double menorDistancia = Double.MAX_VALUE;

        for(Parada parada : paradas) {
            // Cálculo de distancia en metros de una parada con el destino.
            Double distancia = SphericalUtil.computeDistanceBetween(localizacion,
                    new LatLng(parada.getLatitud(), parada.getLongitud()));

            // Una parada que está 500 o menos metros del destino se considera como cercana.
            if(distancia <= 800) {
                paradasCercanas.add(parada);
            }

            // Evaluación de la parada más cercana, de entre todas, al destino.
            if(distancia < menorDistancia) {
                paradaMasCercana = parada;
                menorDistancia = distancia;
            }
        }

        // Se encontró más de una parada cercana.
        if(paradasCercanas.size() > 0) {
            return paradasCercanas;
        } else {
            // No se encontrón ninguna parada a 800 o menos metros del destino,
            // se retorna la más cercana.
            paradasCercanas.add(paradaMasCercana);
            return paradasCercanas;
        }
    }

    /**
     * Encuentra la parada de autobús más cercana a una ubicación en formato LatLng
     * @param paradas Lista de paradas donde se buscará la más cercana al destino.
     * @param localizacion Ubicación a partir de la cual se buscará la parada más cercana.
     * **/
    public static Parada buscarParadaCercana(LatLng localizacion, List<Parada> paradas){
        // Guarda la parada más cercana al destino
        Parada paradaCercana = new Parada();
        Double distanciaMinima = Double.MAX_VALUE;

        // Si la lista sólo contiene una parada, se regresa esta
        if (paradas.size() == 1){
            paradaCercana = paradas.get(0);
            return paradaCercana;
        }else{ // Si la lista contiene más de una parada se busca la más cercana por donde pase la ruta seleccionada
            for (Parada parada : paradas){
                // Cálculo de distancia en metros de una parada con el destino.
                Double distancia = SphericalUtil.computeDistanceBetween(localizacion,
                        new LatLng(parada.getLatitud(), parada.getLongitud()));
                // Si la distancia es menor que la mínima registrada, ésta última se sustituye por la primera.
                if (distancia < distanciaMinima){
                    paradaCercana = parada;
                    distanciaMinima = distancia;
                }
            }
            return paradaCercana;
        }

    }
}
