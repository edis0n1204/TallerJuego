package com.example.tallerjuego.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallerjuego.OnItemClickListener;
import com.example.tallerjuego.R;
import com.example.tallerjuego.modelos.Olimpiada;

import java.util.ArrayList;
import java.util.List;

public class OlimpiadaAdapter extends RecyclerView.Adapter<OlimpiadaAdapter.ViewHolder> {

    private ArrayList<Olimpiada> datos;
    private final OnItemClickListener listener;

    public OlimpiadaAdapter(ArrayList<Olimpiada> datos, OnItemClickListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OlimpiadaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_olimpiada,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Olimpiada ol = datos.get(i);
        viewHolder.titulo.setText(datos.get(i).getNombre());
        viewHolder.descripcion.setText(datos.get(i).getDescripcion());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(ol);
            }
        });
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
