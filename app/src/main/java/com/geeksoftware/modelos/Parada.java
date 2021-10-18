package com.geeksoftware.modelos;

public class Parada {

    private Integer id;
    private Double latitud;
    private Double longitud;
    private String descripcion;

    public Parada() {
        this(null, null, null, null);
    }

    public Parada(Integer id) {
        this(id, null, null, null);
    }

    public Parada(Integer id, Double latitud, Double longitud, String descripcion) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
    }

    public Parada(Double latitud, Double longitud, String descripcion) {
        this(null, latitud, longitud, descripcion);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
