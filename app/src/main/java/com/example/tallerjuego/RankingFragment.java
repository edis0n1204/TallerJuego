package com.example.tallerjuego;

import android.content.Context;
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

import com.example.tallerjuego.Adapter.RankingAdapter;
import com.example.tallerjuego.modelos.Alumno;
import com.orm.query.Select;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RankingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String user;
    private ArrayList<Alumno> alumnos;
    private RecyclerView rcvRanking;
    private RankingAdapter rankingAdapter;



    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
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
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvRanking = view.findViewById(R.id.rcv_ranking);
        rcvRanking.setHasFixedSize(true);
        rcvRanking.setLayoutManager(new LinearLayoutManager(getActivity()));
        alumnos = new ArrayList<>();
        cargarRanking();
        rankingAdapter = new RankingAdapter(alumnos,getContext());
        rcvRanking.setAdapter(rankingAdapter);
    }

    private void cargarRanking() {
        alumnos = (ArrayList<Alumno>) Select.from(Alumno.class).orderBy("mejorpuntaje desc").list();
//        alumnos = (ArrayList<Alumno>) Alumno.listAll(Alumno.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

}
