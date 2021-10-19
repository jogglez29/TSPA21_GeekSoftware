package com.geeksoftware.modelos;


/**
 * Define la información que debe contener una relación entre una Parada y una Ruta.
 */
public class ParadaRuta {

    /** Identificador de la parada relacionada. */
    private Integer idParada;
    /** Identificador de la ruta relacionada. */
    private Integer idRuta;

    /**
     * Crea la instancia de la relación con toda la información requerida.
     * @param idParada Identificador de la parada.
     * @param idRuta Identificador de la ruta.
     */
    public ParadaRuta(Integer idParada, Integer idRuta) {
        this.idParada = idParada;
        this.idRuta = idRuta;
    }

    /**
     * Crea la instancia de la relación con toda la información requerida.
     * @param parada Información de la parada.
     * @param ruta Información de la ruta.
     */
    public ParadaRuta(Parada parada, Ruta ruta) {
        idParada = parada.getId();
        idRuta = ruta.getId();
    }

    /**
     * Extrae el identificador de la parada relacionada.
     * @return Identificador de la parada.
     */
    public Integer getIdParada() {
        return idParada;
    }

    /**
     * Guarda el identificador de la parada relacionada.
     * @param idParada Identificador a guardar.
     */
    public void setIdParada(Integer idParada) {
        this.idParada = idParada;
    }

    /**
     * Extrae el identificador de la ruta relacionada.
     * @return Identificador de la ruta.
     */
    public Integer getIdRuta() {
        return idRuta;
    }

    /**
     * Guarda el identificador de la ruta relacionada.
     * @param idRuta Identificador a guardar.
     */
    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }
}
