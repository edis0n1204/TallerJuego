package com.example.tallerjuego;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tallerjuego.Adapter.OlimpiadaAdapter;
import com.example.tallerjuego.modelos.Alumno;
import com.example.tallerjuego.modelos.Olimpiada;
import com.example.tallerjuego.modelos.Pregunta;
import com.example.tallerjuego.modelos.Respuesta;
import com.example.tallerjuego.olimpiada.Principal;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PrincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button iniciar;
    private RecyclerView rcvOlimpiada;
    private LinearLayoutManager layoutManager;
    private OlimpiadaAdapter olimpiadaAdapter;
    private ArrayList<Olimpiada> datos;
    private String user;
    private Alumno alumno;


    public PrincipalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrincipalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrincipalFragment newInstance(String param1, String param2) {
        PrincipalFragment fragment = new PrincipalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            user = getArguments().getString("user");
            alumno = Alumno.find(Alumno.class,"user = ?", user).get(0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniciar = view.findViewById(R.id.btnIniciarOlimpiada);
        rcvOlimpiada = view.findViewById(R.id.rcv_olimpiada);
        rcvOlimpiada.setHasFixedSize(true);
        rcvOlimpiada.setLayoutManager(new LinearLayoutManager(getActivity()));
        datos = new ArrayList<>();
        cargarOlimpiadas();

        olimpiadaAdapter = new OlimpiadaAdapter(datos, new OnItemClickListener() {
            @Override
            public void onItemClick(Olimpiada item) {
                iniciar(item.getId(),item.getNombre());
            }
        });
        rcvOlimpiada.setAdapter(olimpiadaAdapter);
        iniciar.setOnClickListener(this);

    }

    private void cargarOlimpiadas() {

        datos = (ArrayList<Olimpiada>) Olimpiada.listAll(Olimpiada.class);
    }

    public void iniciar(Long id, String nombre) {
        Intent i= new Intent(getActivity(), Principal.class);
        i.putExtra("olimpiada",id);
        i.putExtra("nombre",nombre);
        i.putExtra("user",user);
        startActivity(i);
    }
    public void iniciar() {
        Intent i= new Intent(getActivity(), Principal.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIniciarOlimpiada:
                iniciar();
                break;
        }
    }
}
