package com.guilherme.nasaviewer11.model;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.nasaviewer11.R;
import com.squareup.picasso.Picasso;

public class Fotos_Activity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // criando um botao de volta quando diver selecionado

        Foto foto = (Foto)getIntent().getSerializableExtra("Foto");

        ImageView imageView = findViewById(R.id.fotoImageView);
        TextView descView = findViewById(R.id.descricaoFoto);
        Picasso.with(this).load(foto.getUrl()).into(imageView);
        descView.setText(foto.getExplicacao());
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
