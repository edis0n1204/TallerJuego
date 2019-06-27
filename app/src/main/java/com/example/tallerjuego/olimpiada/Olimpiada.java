package com.example.tallerjuego.olimpiada;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import com.example.tallerjuego.R;
import com.example.tallerjuego.modelos.Alumno;
import com.example.tallerjuego.modelos.Pregunta;
import com.example.tallerjuego.modelos.Respuesta;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Olimpiada extends AppCompatActivity {
    public static final String CORRECT_ANSWER = "correct_answer";
    public static final String CURRENT_QUESTION = "current_question";
    public static final String ANSWER_IS_CORRECT = "answer_is_correct";
    public static final String ANSWER = "answer";
    public static final ArrayList<String> result = new ArrayList<String>();
    private int nota=10;
    private String tiempo;
    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };
    private String[] lista;
    private ArrayList<Pregunta> preguntas;
    private TextView text_question,cronometro1;
    private RadioGroup group;
    private Button btn_next, btn_prev;
    private Cronometro cronometro;
    private ArrayList<Respuesta> respuestas;

    private int correct_answer;
    private int current_question;
    private boolean[] answer_is_correct;
    private int[] answer;
    private String user;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("lifecycle", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putInt(CORRECT_ANSWER, correct_answer);
        outState.putInt(CURRENT_QUESTION, current_question);
        outState.putBooleanArray(ANSWER_IS_CORRECT, answer_is_correct);
        outState.putIntArray(ANSWER, answer);

    }


    @Override
    protected void onStop() {
        Log.i("lifecycle", "onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.i("lifecycle", "onStart");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.i("lifecycle", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("lifecycle", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olimpiada);


//        lista = getIntent().getStringArrayExtra("Preg");
        Long olimpiada_id = getIntent().getLongExtra("olimpiada_id",1);
        user = getIntent().getStringExtra("user");
        preguntas = (ArrayList<Pregunta>) Pregunta.find(Pregunta.class,"olimpiada = ?",olimpiada_id.toString());
        text_question = (TextView) findViewById(R.id.text_question);
        group = (RadioGroup) findViewById(R.id.answer_group);
        btn_next = (Button) findViewById(R.id.btn_check);
        btn_prev = (Button) findViewById(R.id.btn_prev);
        cronometro1 = (TextView) findViewById(R.id.cronometro);

        cronometro = new Cronometro("Nombre del cronómetro", cronometro1 );
        new Thread(cronometro).start();




    //    all_questions= getResources().getStringArray(R.array.all_questions);

        if (savedInstanceState == null) {
            startOver();
        } else {
            Bundle state = savedInstanceState;
            correct_answer = state.getInt(CORRECT_ANSWER);
            current_question = state.getInt(CURRENT_QUESTION);
            answer_is_correct = state.getBooleanArray(ANSWER_IS_CORRECT);
            answer = state.getIntArray(ANSWER);
            mostrarPregunta();
        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                //lista.lenght()
                if (current_question < preguntas.size()-1) {
                    current_question++;
                    mostrarPregunta();
//                    showQuestion();
                } else {
                    checkResults();
//                    cronometro.pause();


                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (current_question > 0) {
                    current_question--;
                    mostrarPregunta();
//                    showQuestion();
                }
            }
        });
    }

    private void startOver() {
        answer_is_correct = new boolean[preguntas.size()];
        answer = new int[preguntas.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = -1;
        }
        current_question = 0;
        mostrarPregunta();
//        showQuestion();
    }



    private void checkResults() {
        int correctas = 0, incorrectas = 0, nocontestadas = 0;
        Alumno a = Alumno.find(Alumno.class,"user = ?",user).get(0);
        for (int i = 0; i < preguntas.size(); i++) {
            if (answer_is_correct[i]) correctas++;
            else if (answer[i] == -1) nocontestadas++;
            else incorrectas++;
        }
        a.puntaje = correctas*10;
        if (a.mejor_puntaje<correctas*10)
            a.mejor_puntaje=correctas*10;
        tiempo= cronometro.toString();
        a.save();


        // TODO: Permitir traducción de este texto:
        String message =
                String.format("Correctas: %d\nIncorrectas: %d\nNo contestadas: %d\nNota: %d\n",
                        correctas, incorrectas, nocontestadas, nota= nota*correctas);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.results);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.finish, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
      /*  builder.setNegativeButton(R.string.start_over, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startOver();
            }
        });*/
        builder.create().show();
    }

    private void checkAnswer() {
        int id = group.getCheckedRadioButtonId();
        int ans = -1;
        for (int i = 0; i < ids_answers.length; i++) {
            if (ids_answers[i] == id) {
                ans = i;
            }
        }
        answer_is_correct[current_question] = (ans == correct_answer);
        answer[current_question] = ans;
    }
    private void mostrarPregunta(){
        String pregunt = preguntas.get(current_question).pregunta;
        group.clearCheck();
        text_question.setText(pregunt);
        respuestas = (ArrayList<Respuesta>) Respuesta.find(Respuesta.class,"pregunta = ?", preguntas.get(current_question).getId().toString());
        for(int i = 0; i < respuestas.size(); i++){
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String res = respuestas.get(i).respuesta;
            if (respuestas.get(i).correcta){
                correct_answer = i;
            }
            rb.setText(res);
            if (answer[current_question]==i){
                rb.setChecked(true);
            }
        }
        if (current_question == 0) {
            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }
        if (current_question == preguntas.size()-1) {
            btn_next.setText(R.string.finish);

        } else {
            btn_next.setText(R.string.next);
        }


    }

    private void showQuestion() {
        String q = preguntas.get(current_question).pregunta;
        String[] parts = q.split(";");

        group.clearCheck();

        text_question.setText(parts[0]);
        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String ans = parts[i+1];
            if (ans.charAt(0) == '*') {
                correct_answer = i;
                ans = ans.substring(1);
            }
            rb.setText(ans);
            if (answer[current_question] == i) {
                rb.setChecked(true);
            }
        }
        if (current_question == 0) {
            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }
        if (current_question == preguntas.size()-1) {
            btn_next.setText(R.string.finish);

        } else {
            btn_next.setText(R.string.next);
        }
    }

}
