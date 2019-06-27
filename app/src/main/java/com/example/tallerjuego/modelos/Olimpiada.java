package com.example.tallerjuego.modelos;

import com.orm.SugarRecord;

public class Olimpiada extends SugarRecord {
    public int idOlimpiada;
    public String nombre;
    public String descripcion;
    public String imagen;

    public Olimpiada() {
    }

    public Olimpiada(int idOlimpiada, String nombre, String descripcion, String imagen) {
        this.idOlimpiada = idOlimpiada;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getIdOlimpiada() {
        return idOlimpiada;
    }

    public void setIdOlimpiada(int idOlimpiada) {
        this.idOlimpiada = idOlimpiada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
