package com.geeksoftware.modelos;

public class Ruta {

    private Integer id;
    private String nombre;
    private String color;

    public Ruta() {
        this(null, null, null);
    }

    public Ruta(Integer id) {
        this(id, null, null);
    }

    public Ruta(Integer id, String nombre, String color) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
    }

    public Ruta(String nombre, String color) {
        this(null, nombre, color);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
