package com.example.tallerjuego.olimpiada;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tallerjuego.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Principal extends AppCompatActivity {
    private TextView text,bienvenido;
    private Button btn_next;
    private String[]milista;
    SharedPreferences myPreferences;
    private Long olimpiada_id;
    private String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        text= (TextView) findViewById(R.id.text_question1);
        btn_next= (Button) findViewById(R.id.btn_next1);
        bienvenido=(TextView) findViewById(R.id.WelcomeTextView) ;
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        olimpiada_id = getIntent().getLongExtra("olimpiada",1);
        user= getIntent().getStringExtra("user");
        bienvenido.setText("Bienvenido a las olimpiadas de "+getIntent().getStringExtra("nombre"));

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://edisito1204.000webhostapp.com/prueba/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonOlimpiadaApi jsonOlimpiadaApi = retrofit.create(JsonOlimpiadaApi.class);
            Call<List<com.example.tallerjuego.modelos.Olimpiada>> call = jsonOlimpiadaApi.getOlimpidas();
        call.enqueue(new Callback<List<com.example.tallerjuego.modelos.Olimpiada>>() {
                @Override
                public void onResponse(Call<List<com.example.tallerjuego.modelos.Olimpiada>> call, Response<List<com.example.tallerjuego.modelos.Olimpiada>> response) {
                    if (!response.isSuccessful()){
                        text.setText("preg;"+response.code());
                        return;
                    }
                    List<com.example.tallerjuego.modelos.Olimpiada> preguntas= response.body();


                    //String content="";
                    //content+= pregunta.getPregunta();
                    // text.append(content);

                    String[] arr = new String[preguntas.size()];
                    for (int i = 0; i < preguntas.size(); i++){
                        arr[i] = preguntas.get(i).getNombre();
                        milista=arr;
                    }

                }

            @Override
            public void onFailure(Call<List<com.example.tallerjuego.modelos.Olimpiada>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),Olimpiada.class);
                i.putExtra("Preg", milista);
                i.putExtra("olimpiada_id",olimpiada_id);
                i.putExtra("user",user);
                startActivity(i);

            }
        });
    }


}
