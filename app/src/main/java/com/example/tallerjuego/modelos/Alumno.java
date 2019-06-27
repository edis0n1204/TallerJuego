package com.example.tallerjuego.modelos;


import com.orm.SugarRecord;

public class Alumno extends SugarRecord {
    public String nombre;
    public String apellido;
    public String user;
    public String pass;
    public String colegio;
    public int puntaje;
    public int tiempo_promedio;
    public int mejor_puntaje;
    public String imagen;

    public Alumno() {
    }

    public Alumno(String nombre, String apellido, String user,String pass,String colegio, int puntaje, int tiempo_promedio, int mejor_puntaje, String imagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.colegio = colegio;
        this.puntaje = puntaje;
        this.user = user;
        this.pass = pass;
        this.tiempo_promedio = tiempo_promedio;
        this.mejor_puntaje = mejor_puntaje;
        this.imagen = imagen;
    }

}
