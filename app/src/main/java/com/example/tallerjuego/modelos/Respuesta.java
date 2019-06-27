package com.example.tallerjuego.modelos;

import com.orm.SugarRecord;

public class Respuesta extends SugarRecord {
    public int idRespuesta;
    public String respuesta;
    public boolean correcta;
    public int id_pregunta;

    public Pregunta pregunta;
    public Respuesta() {
    }

    public Respuesta(int idRespuesta, String respuesta, boolean correcta, int id_pregunta) {
        this.idRespuesta = idRespuesta;
        this.respuesta = respuesta;
        this.correcta = correcta;
        this.id_pregunta = id_pregunta;
    }
}
