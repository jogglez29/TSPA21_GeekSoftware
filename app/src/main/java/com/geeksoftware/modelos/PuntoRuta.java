package com.geeksoftware.modelos;

/**
 * Define la información que debe contener un Punto que ayuda a definir
 * el trayecto de una Ruta.
 */
public class PuntoRuta {

    /** Identificador */
    private Integer id;
    /** Identificador de la ruta relacionada. */
    private Integer idRuta;
    /** Coordenada de latitud de la parada. */
    private Double latitud;
    /** Coordenada de longitud de la parada. */
    private Double longitud;

    /**
     * Crea la instancia de la Parada sin nada de información.
     * @param id
     * @param idRuta
     * @param latitud
     * @param longitud
     */
    public PuntoRuta(Integer id, Integer idRuta, Double latitud, Double longitud) {
        this(null, (Ruta) null, null, null);
    }

    /**
     * Crea la instancia de la Parada sólo con su id como informaciónn.
     * @param id Identificador de la parada.
     */
    public PuntoRuta(Integer id) {
        this(id, (Ruta) null, null, null);
    }

    /**
     * Crea la instancia de la Parada con toda la información excepto el id.
     * @param latitud Latitud de de la parada.
     * @param longitud Longitud de la parada.
     */
    public PuntoRuta(Integer idRuta, Double latitud, Double longitud) {
        this(null, idRuta, latitud, longitud);
    }

    /**
     * Crea la instancia de la Punto con toda su información.
     * @param id Identificador del punto.
     * @param ruta Información de la ruta.
     * @param latitud Latitud de la parada.
     * @param longitud Longitud de la parada.
     */
    public PuntoRuta(Integer id, Ruta ruta, Double latitud, Double longitud) {
        this.id = id;
        idRuta = ruta.getId();
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Extrae el identificador del Punto.
     * @return Identificador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Guarda el identificador del Punto.
     * @param id Identificador a guardarse.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Extrae la coordenada de latitud del punto.
     * @return Coordenada de latitud.
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * Guarda la coordenada de latitud del punto.
     * @param latitud Coordenada de latitud a guardarse.
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * Extrae la coordenada de longitud del punto.
     * @return Coordenada de longitud.
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * Guarda el id de la ruta asociada al punto.
     * @param  idRuta Identificador de la ruta asociada.
     */
    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    /**
     * Extrae el id de la ruta asociada al punto.
     * @return Identificador de la ruta.
     */
    public Integer getIdRuta() {
        return idRuta;
    }

    /**
     * Guarda la coordenada de longitud del punto.
     * @param longitud Coordenada de longitud a guardarse.
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}

