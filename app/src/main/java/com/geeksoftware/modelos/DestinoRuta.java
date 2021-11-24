package com.geeksoftware.modelos;


/**
 * Define la información que debe contener una relación entre un Destino y una Ruta.
 */
public class DestinoRuta {

    /** Identificador del destino relacionado. */
    private Integer idDestino;
    /** Identificador de la ruta relacionada. */
    private Integer idRuta;

    /**
     * Crea la instancia de la relación con toda la información requerida.
     * @param idDestino Identificador del destino.
     * @param idRuta Identificador de la ruta.
     */
    public DestinoRuta(Integer idDestino, Integer idRuta) {
        this.idDestino = idDestino;
        this.idRuta = idRuta;
    }

    /**
     * Crea la instancia de la relación con toda la información requerida.
     * @param destino Información del destino.
     * @param ruta Información de la ruta.
     */
    public DestinoRuta(Destino destino, Ruta ruta) {
        idDestino = destino.getId();
        idRuta = ruta.getId();
    }

    /**
     * Extrae el identificador del destino relacionado.
     * @return Identificador del destino.
     */
    public Integer getIdDestino() {
        return idDestino;
    }

    /**
     * Guarda el identificador del destino relacionado.
     * @param idDestino Identificador a guardar.
     */
    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
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
