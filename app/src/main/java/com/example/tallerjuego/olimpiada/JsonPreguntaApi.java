package com.example.tallerjuego.olimpiada;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPreguntaApi {

    @GET("obtenerPreg.php")
    Call<List<Preguntas>> getPreguntas();
}
