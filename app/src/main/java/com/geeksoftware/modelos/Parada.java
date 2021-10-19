package com.geeksoftware.modelos;

/**
 * Define la información que debe contener una parada de autobus.
 */
public class Parada {

    /** Identificador de la parada. */
    private Integer id;
    /** Coordenada de latitud de la parada. */
    private Double latitud;
    /** Coordenada de longitud de la parada. */
    private Double longitud;
    /** Información extra sobre la parada. */
    private String descripcion;

    /**
     * Crea la instancia de la Parada sin nada de información.
     */
    public Parada() {
        this(null, null, null, null);
    }

    /**
     * Crea la instancia de la Parada sólo con su id como informaciónn.
     * @param id Identificador de la parada.
     */
    public Parada(Integer id) {
        this(id, null, null, null);
    }

    /**
     * Crea la instancia de la Parada con toda la información excepto el id.
     * @param latitud Latitud de de la parada.
     * @param longitud Longitud de la parada.
     * @param descripcion Información extra sobre la parada.
     */
    public Parada(Double latitud, Double longitud, String descripcion) {
        this(null, latitud, longitud, descripcion);
    }

    /**
     * Crea la instancia de la Parada con toda su información.
     * @param id Identificador de la parada.
     * @param latitud Latitud de la parada.
     * @param longitud Longitud de la parada.
     * @param descripcion Información extra sobre la parada.
     */
    public Parada(Integer id, Double latitud, Double longitud, String descripcion) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
    }

    /**
     * Extrae el identificador de la parada.
     * @return Identificador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Guarda el identificador de la parada.
     * @param id Identificador a guardarse.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Extrae la coordenada de latitud de la parada.
     * @return Coordenada de latitud.
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * Guarda la coordenada de longitud de la parada.
     * @param latitud Coordenada de latitud a guardarse.
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * Extrae la coordenada de longitud de la parada.
     * @return Coordenada de longitud.
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * Guarda la coordenada de longitud de la parada.
     * @param longitud Coordenada de longitud a guardarse.
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * Extrae la información extra que posee la parada.
     * @return Información extra.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Guarda información extra sobre la parada.
     * @param descripcion Información extra a guardarse.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
