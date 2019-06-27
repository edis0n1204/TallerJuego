package com.example.tallerjuego.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallerjuego.OnItemClickListener;
import com.example.tallerjuego.R;
import com.example.tallerjuego.modelos.Alumno;
import com.example.tallerjuego.modelos.Olimpiada;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private ArrayList<Alumno> datos;
    private Context context;

    public RankingAdapter(ArrayList<Alumno> datos, Context context) {
        this.datos = datos;
        this.context = context;
    }

    @NonNull
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_olimpiada,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Alumno ol = datos.get(i);
        viewHolder.titulo.setText(ol.nombre);
        viewHolder.descripcion.setText(String.valueOf(ol.mejor_puntaje));
        if (ol.user.equals("ceva123"))
            viewHolder.imagen.setImageDrawable(context.getResources().getDrawable(R.drawable.ceva));
        else
            viewHolder.imagen.setImageDrawable(context.getResources().getDrawable(R.drawable.edison));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView titulo;
        private TextView descripcion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imv_olimpiada);
            titulo = itemView.findViewById(R.id.txt_titulo);
            descripcion = itemView.findViewById(R.id.txt_descripcion);
        }
    }
}
