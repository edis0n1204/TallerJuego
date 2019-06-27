package com.example.tallerjuego.olimpiada;

import com.example.tallerjuego.modelos.Olimpiada;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonOlimpiadaApi {

    @GET("obtenerOlimpiada.php")
    Call<List<Olimpiada>> getOlimpidas();
}
