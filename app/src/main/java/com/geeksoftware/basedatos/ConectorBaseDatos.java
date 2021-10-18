package com.geeksoftware.basedatos;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.ParadaRuta;
import com.geeksoftware.modelos.Ruta;

import java.util.List;

public interface ConectorBaseDatos {

    boolean agregarRuta(Ruta ruta);
    boolean agregarParada(Parada parada);
    boolean agregarParadaRuta(Parada parada, Ruta ruta);
    List<Ruta> obtenerRutas();
    List<Parada> obtenerParadas();
    List<Ruta> obtenerRutasPorParada(Integer idParada);
    List<ParadaRuta> obtenerParadasRutas();
}
