package com.geeksoftware.modelos;

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

    /**
     * Crea la instancia de la ruta sin nada de información.
     */
    public Ruta() {
        this(null, null, null);
    }

    /**
     * Crea la instancia de la ruta con el id como información.
     * @param id Identificador de la ruta.
     */
    public Ruta(Integer id) {
        this(id, null, null);
    }

    /**
     * Crea la instancia de la ruta con toda su información excepto el id.
     * @param nombre Nombre de la ruta.
     * @param color Color asignado a la ruta.
     */
    public Ruta(String nombre, String color) {
        this(null, nombre, color);
    }

    /**
     * Crea la instancia de la ruta con toda la información requerida.
     * @param id Identificador de la ruta.
     * @param nombre Nombre de la ruta.
     * @param color Color asignado a la ruta.
     */
    public Ruta(Integer id, String nombre, String color) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
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
}
