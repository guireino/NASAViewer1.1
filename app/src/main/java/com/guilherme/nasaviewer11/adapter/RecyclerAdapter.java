package com.guilherme.nasaviewer11.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guilherme.nasaviewer11.R;
import com.guilherme.nasaviewer11.model.Foto;
import com.guilherme.nasaviewer11.model.Fotos_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.FotoHolder>{

    private ArrayList<Foto> fotos;

    public RecyclerAdapter(ArrayList<Foto> fotos){
        Log.d("Adapter","Adapter Construtor");

        this.fotos = fotos;
    }

    @Override
    public FotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Adapter","Adapter onCreateViewHolder");
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview,parent,false);
        return new FotoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(FotoHolder holder, int position) {
        Foto itemFoto = fotos.get(position);
        holder.bindFoto(itemFoto);
    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    public static class FotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView itemData;
        private TextView itemTitulo;
        private TextView itemDescricao;
        private Foto foto;

        public FotoHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.item_image);
            itemData = itemView.findViewById(R.id.item_data);
            itemTitulo = itemView.findViewById(R.id.item_titulo);
            itemDescricao = itemView.findViewById(R.id.item_descricao);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            Context context = view.getContext();
            Intent mostrerFoto = new Intent(context, Fotos_Activity.class);
            mostrerFoto.putExtra("Foto", foto);
            context.startActivity(mostrerFoto);
        }

        public void bindFoto(Foto foto) {

            Log.d("bind", "BindFoto:" + foto.getUrl());

            this.foto = foto;

            Picasso.with(imageView.getContext()).load(foto.getUrl()).into(imageView);

            itemData.setText(foto.getData());
            itemTitulo.setText(foto.getTitulo());
            itemDescricao.setText(foto.getExplicacao());
        }
    }
}
