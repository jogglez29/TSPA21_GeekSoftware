package com.geeksoftware.modelos;

import androidx.annotation.Nullable;

/**
 * Define la información que debe contener una ruta.
 */
public class Ruta {

    /** Identificador de la ruta. */
    private Integer id;
    /** Nombre de la ruta */
    private String nombre;
    /** Color asignado a la ruta. */
    private String color;
    /** Nombre de la imagen de la ruta. */
    private String imagen;

    /**
     * Crea la instancia de la ruta sin nada de información.
     */
    public Ruta() {
        this(null, null, null, null);
    }

    /**
     * Crea la instancia de la ruta con el id como información.
     * @param id Identificador de la ruta.
     */
    public Ruta(Integer id) {
        this(id, null, null, null);
    }

    /**
     * Crea la instancia de la ruta con toda su información excepto el id.
     * @param nombre Nombre de la ruta.
     * @param color Color asignado a la ruta.
     * @param imagen Nombre de la imagen de la ruta.
     */
    public Ruta(String nombre, String color, String imagen) {
        this(null, nombre, color, imagen);
    }

    /**
     * Crea la instancia de la ruta con toda la información requerida.
     * @param id Identificador de la ruta.
     * @param nombre Nombre de la ruta.
     * @param color Color asignado a la ruta.
     * @param imagen Nombre de la imagen de la ruta.
     */
    public Ruta(Integer id, String nombre, String color, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
        this.imagen = imagen;
    }

    /**
     * Extrae el identificador de la ruta.
     * @return Identificador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Guarda el identificador de la ruta.
     * @param id Identificador a guardarse
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Extrae el color asignado a la ruta.
     * @return Color asignado.
     */
    public String getColor() {
        return color;
    }

    /**
     * Guarda el color asignado a la ruta.
     * @param color Colo a guardar.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Extrae el nombre de la ruta.
     * @return Nombre de ruta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Guarda un nombre de ruta.
     * @param nombre Nombre a guardar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Extrae el nombre de la imagen de la ruta.
     * @return Nombre de la imagen de la ruta.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Guarda el nombre de la imagen de la ruta.
     * @param imagen Nombre de la imagen de la ruta.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Ruta ruta = (Ruta) obj;
        if(this.id.equals(ruta.getId())) {
            return true;
        } else {
            return false;
        }
    }
}
