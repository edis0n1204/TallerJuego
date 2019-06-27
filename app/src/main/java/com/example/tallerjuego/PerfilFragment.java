package com.example.tallerjuego;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.tallerjuego.modelos.Alumno;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences myPreferences;
    private Switch swtHabilitarBiometria;
    private String user;
    private Alumno alumno;
    private TextView txtNombre;
    private TextView txtColegio;
    private TextView txtPuntaje;
    private TextView txtTiempo;
    private TextView txtMejorPuntaje;
    private ImageView imvFoto;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        swtHabilitarBiometria = view.findViewById(R.id.swt_habilitar_biometria);
        txtNombre = view.findViewById(R.id.txt_nombre);
        txtColegio = view.findViewById(R.id.txt_colegio);
        txtPuntaje = view.findViewById(R.id.txt_puntaje);
        txtTiempo = view.findViewById(R.id.txt_tiempo);
        txtMejorPuntaje = view.findViewById(R.id.txt_mejor_puntaje);
        imvFoto = view.findViewById(R.id.imageView);
        if (user.equals("ceva123"))
            imvFoto.setImageDrawable(getResources().getDrawable(R.drawable.ceva));
        txtNombre.setText(alumno.nombre+" "+alumno.apellido);
        txtColegio.setText(alumno.colegio);
        txtPuntaje.setText(String.valueOf(alumno.puntaje));
        txtTiempo.setText(String.valueOf(alumno.tiempo_promedio));
        txtMejorPuntaje.setText(String.valueOf(alumno.mejor_puntaje));
        swtHabilitarBiometria.setChecked(myPreferences.getBoolean("biometria",false));
        swtHabilitarBiometria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putBoolean("biometria",b);
                editor.commit();
            }
        });
        return view;
    }

}
