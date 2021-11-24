package com.geeksoftware.modelos;

/**
 * Define la información que debe contener un destino.
 */
public class Destino {

    /** Identificador del destino. */
    private Integer id;
    /** Coordenada de latitud del destino. */
    private Double latitud;
    /** Coordenada de longitud del destino. */
    private Double longitud;
    /** Información extra sobre el destino. */
    private String descripcion;

    /**
     * Crea la instancia del destino sin nada de información.
     */
    public Destino() {
        this(null, null, null, null);
    }

    /**
     * Crea la instancia del destino sólo con su id como informaciónn.
     * @param id Identificador del destino.
     */
    public Destino(Integer id) {
        this(id, null, null, null);
    }

    /**
     * Crea la instancia del destino con toda la información excepto el id.
     * @param latitud Latitud del destino.
     * @param longitud Longitud del destino.
     * @param descripcion Información extra sobre el destino.
     */
    public Destino(Double latitud, Double longitud, String descripcion) {
        this(null, latitud, longitud, descripcion);
    }

    /**
     * Crea la instancia del destino con toda su información.
     * @param id Identificador del destino.
     * @param latitud Latitud del destino.
     * @param longitud Longitud del destino.
     * @param descripcion Información extra sobre el destino.
     */
    public Destino(Integer id, Double latitud, Double longitud, String descripcion) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
    }

    /**
     * Extrae el identificador del destino.
     * @return Identificador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Guarda el identificador del destino.
     * @param id Identificador a guardarse.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Extrae la coordenada de latitud del destino.
     * @return Coordenada de latitud.
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * Guarda la coordenada de longitud del destino.
     * @param latitud Coordenada de latitud a guardarse.
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * Extrae la coordenada de longitud del destino.
     * @return Coordenada de longitud.
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * Guarda la coordenada de longitud del destino.
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
