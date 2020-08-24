package com.guilherme.nasaviewer11.api;

import com.guilherme.nasaviewer11.model.Foto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AstropicApi{
    @GET("/planetary/apod") Call<Foto> buscaFoto(@Query("date")String date);
}