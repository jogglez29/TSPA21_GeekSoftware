package com.geeksoftware.basedatos;

import com.geeksoftware.modelos.Destino;
import com.geeksoftware.modelos.DestinoRuta;
import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.ParadaRuta;
import com.geeksoftware.modelos.PuntoRuta;
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
     * Añade varios registros de Rutas a la base de datos.
     * @param rutas Lista con las rutas que se registraran.
     */
    void agregarRutas(List<Ruta> rutas);

    /**
     * Añade un registro de una Parada a la base de datos.
     * @param parada Información de la parada a registrar.
     * @return Regresa <b>true</b> en caso de haber registrado
     * el dato exitosamente, o <b>false</b> en caso contrario.
     */
    boolean agregarParada(Parada parada);

    /**
     * Añade un registro de un Destino a la base de datos.
     * @param destino Información del destino a registrar.
     * @return Regresa <b>true</b> en caso de haber registrado
     * el dato exitosamente, o <b>false</b> en caso contrario.
     */
    boolean agregarDestino(Destino destino);

    /**
     * Añade varios registros de Paradas a la base de datos.
     * @param paradas Lista de las paradas que se registraran.
     */
    void agregarParadas(List<Parada> paradas);

    /**
     * Añade varios registros de Destinos a la base de datos.
     * @param destinos Lista de los destinos que se registrarán.
     */
    void agregarDestinos(List<Destino> destinos);

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
     * Genera la relación entre varias paradas de autobus y varias rutas
     * en la base de datos.
     * @param paradaRutas Lista con las relaciones de las paradas con las rutas.
     */
    void agregarParadasRuta(List<ParadaRuta> paradaRutas);

    /**
     * Añade a la base de datos un registro de los puntos
     * por los que pasa una ruta.
     * @param punto Información del punto a registrar.
     * @return Regresa <b>true</b> en caso de haber registrado
     * el dato exitosamente, o <b>false</b> en caso contrario.
     */
    boolean agregarPuntoRuta(PuntoRuta punto);

    /**
     * Añade a la base de datos los registro de los puntos por los que pasa cada ruta.
     * por los que pasa una ruta.
     * @param puntos Lista de los puntos correspondientes a cada ruta.
     */
    void agregarPuntosRuta(List<PuntoRuta> puntos);

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
     * Consulta las paradas existentes en la base de datos correspondientes
     * a una ruta en especifico.
     * @param idRuta Identificador de la ruta de la cual se obtendran sus paradas.
     * @return Regresa una lista con la información de las paradas
     * obtenidas o null en caso de haber ocurrido un error.
     */
    List<Parada> obtenerParadasRuta(Integer idRuta);

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

    /**
     * Consulta la información de los puntos por los que pasa una ruta.
     * @param idRuta Identificador de la ruta.
     * @return Regresa una lista con la información de los puntos
     * obtenidas o null en caso de haber ocurrido un error.
     */
    List<PuntoRuta> obtenerPuntosRuta(Integer idRuta);

    /**
     * Genera una relación entre un destino de autobús y una ruta
     * en la base de datos.
     * @param destino Información del destino a relacionar.
     * @param ruta Información de la ruta a relacionar.
     * @return Regresa <b>true</b> en caso de haber registrado
     * el dato exitosamente, o <b>false</b> en caso contrario.
     */
    boolean agregarRutaDestino(Destino destino, Ruta ruta);

    /**
     * Genera la relación entre varios destinos  y varias rutas
     * en la base de datos.
     * @param destinoRutas Lista con las relaciones de los destinos con las rutas.
     */
    void agregarDestinosRuta(List<DestinoRuta> destinoRutas);

    /**
     * Consulta todos los destinos que existen en la base de datos.
     * @return Regresa una lista con los destinos obtenidos o null
     * en caso de haber ocurrido un error.
     */
    List<Destino> obtenerDestinos();

    /**
     * Consulta los destinos existentes en la base de datos correspondientes
     * a una ruta en especifico.
     * @param idRuta Identificador de la ruta de la cual se obtendran sus destinos.
     * @return Regresa una lista con la información de los destinos
     * obtenidas o null en caso de haber ocurrido un error.
     */
    List<Destino> obtenerDestinosRuta(Integer idRuta);

    /**
     * Consulta todas las relaciones entre destinos y rutas que existen
     * en la base de datos.
     * @return Regresa una lista con la información obtenida
     * o null en caso de haber ocurrido un error.
     */
    List<DestinoRuta> obtenerDestinosRutas();
}

