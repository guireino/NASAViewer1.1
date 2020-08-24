package com.guilherme.nasaviewer11.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Foto implements Serializable{

    @SerializedName("date")
    private String data;

    @SerializedName("title")
    private String titulo;

    @SerializedName("explanation")
    private String explicacao;
    private String url;

    @SerializedName("media_type")
    private String tipoMedia;

    public Foto(){

    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getExplicacao(){
        return explicacao;
    }

    public void setExplicacao(String explicacao){
        this.explicacao = explicacao;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getTipoMedia(){
        return tipoMedia;
    }

}
