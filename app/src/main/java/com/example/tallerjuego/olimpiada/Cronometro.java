package com.example.tallerjuego.olimpiada;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class Cronometro implements Runnable {
    private TextView etiq; // Etiqueta para mostrar la información
    private String nombrecronometro; // Nombre del cronómetro
    private int segundos, minutos, horas; // Segundos, minutos y horas que lleva activo el cronómetro
    private Handler escribirenUI; // Necesario para modificar la UI
    private Boolean pausado; // Para pausar el cronómetro
    private String salida;

    public Cronometro(String nombre, TextView etiqueta)
    {
        etiq = etiqueta;
        salida = "";
        segundos = 0;
        minutos = 0;
        horas = 0;
        nombrecronometro = nombre;
        escribirenUI = new Handler();
        pausado = Boolean.FALSE;
    }

    @Override
    public void run() {
        try
        {
            while(Boolean.TRUE)
            {
                Thread.sleep(1000);
                salida = "";
                if( !pausado )
                {
                    segundos++;
                    if(segundos == 60)
                    {
                        segundos = 0;
                        minutos++;
                    }
                  /*  if(minutos == 60)
                    {
                        minutos = 0;
                        horas++;
                    }*/
                    // Formateo la salida
                   // salida += "0";
                 //   salida += horas;
                   // salida += ":";
                    if( minutos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += minutos;
                    salida += ":";
                    if( segundos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += segundos;
                    // Modifico la UI
                    try
                    {
                        escribirenUI.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                etiq.setText(salida);
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + " al escribir en la UI: " + e.toString());
                    }
                }
            }
        }
        catch (InterruptedException e)
        {
            Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + ": " + e.toString());
        }

    }
    public void pause()
    {
        pausado = !pausado;
    }
    public String toString(){
        return minutos+":"+segundos;
    }
}
