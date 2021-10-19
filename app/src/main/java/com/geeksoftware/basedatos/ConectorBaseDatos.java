package com.geeksoftware.basedatos;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.ParadaRuta;
import com.geeksoftware.modelos.Ruta;

import java.util.List;

/**
 * Define las acciones CRUD que debe realizar la base de datos.
 */
public interface ConectorBaseDatos {

    /**
     * Añade un registro de una Ruta a la base de datos.
     * @param ruta Información de la ruta a registrar.
     * @return Regresa <b>true</b> en caso de haber registrado
     * el dato exitosamente, o <b>false</b> en caso contrario.
     */
    boolean agregarRuta(Ruta ruta);

    /**
     * Añade un registro de una Parada a la base de datos.
     * @param parada Información de la parada a registrar.
     * @return Regresa <b>true</b> en caso de haber registrado
     * el dato exitosamente, o <b>false</b> en caso contrario.
     */
    boolean agregarParada(Parada parada);

    /**
     * Genera una relación entre una parada de autobus y una ruta
     * en la base de datos.
     * @param parada Información de la parada a relacionar.
     * @param ruta Información de la ruta a relacionar.
     * @return Regresa <b>true</b> en caso de haber registrado
     * el dato exitosamente, o <b>false</b> en caso contrario.
     */
    boolean agregarParadaRuta(Parada parada, Ruta ruta);

    /**
     * Consulta todas las rutas que existen en la base de datos.
     * @return Regresa una lista con las rutas obtenidas o null
     * en caso de haber ocurrido un error.
     */
    List<Ruta> obtenerRutas();

    /**
     * Consulta todas las paradas que existen en la base de datos.
     * @return Regresa una lista con las paradas obtenidas o null
     * en caso de haber ocurrido un error.
     */
    List<Parada> obtenerParadas();

    /**
     * Consulta la información de las rutas que se detienen en una
     * determinada parada.
     * @param idParada Identificador de la parada a consultar.
     * @return Regresa una lista con la información de las rutas
     * obtenidas o null en caso de haber ocurrido un error.
     */
    List<Ruta> obtenerRutasPorParada(Integer idParada);

    /**
     * Consulta todas las relaciones entre paradas y rutas que existen
     * en la base de datos.
     * @return Regresa una lista con la información obtenida
     * o null en caso de haber ocurrido un error.
     */
    List<ParadaRuta> obtenerParadasRutas();
}
