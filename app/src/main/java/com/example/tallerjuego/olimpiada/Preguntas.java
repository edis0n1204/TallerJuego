package com.example.tallerjuego.olimpiada;

import com.google.gson.annotations.SerializedName;

public class Preguntas {
    private int id;

    @SerializedName("preg")
    private String pregunta;

    public int getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }
}
