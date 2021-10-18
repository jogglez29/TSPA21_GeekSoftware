package com.geeksoftware.modelos;


public class ParadaRuta {

    private Integer idParada;
    private Integer idRuta;

    public ParadaRuta(Integer idParada, Integer idRuta) {
        this.idParada = idParada;
        this.idRuta = idRuta;
    }

    public ParadaRuta(Parada parada, Ruta ruta) {
        idParada = parada.getId();
        idRuta = ruta.getId();
    }

    public Integer getIdParada() {
        return idParada;
    }

    public void setIdParada(Integer idParada) {
        this.idParada = idParada;
    }

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }
}
