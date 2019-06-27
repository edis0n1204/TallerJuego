package com.example.tallerjuego.modelos;

import com.orm.SugarRecord;

public class Pregunta extends SugarRecord {
    public int idPregunta;
    public String pregunta;
    public int puntos;
    public int id_olimpiada;

    public Olimpiada olimpiada;
    public Pregunta() {
    }

    public Pregunta(int idPregunta, String pregunta, int puntos, int id_olimpiada) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.puntos = puntos;
        this.id_olimpiada = id_olimpiada;
    }
}
