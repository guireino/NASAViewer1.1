package com.guilherme.nasaviewer11.api;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.guilherme.nasaviewer11.model.Foto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequisicaoFoto {

    private boolean loadingDado;
    private Context context;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private AstropicApi astropicApi;
    private Respostalmagem respostalmagem;

    private final String API_KEY_PARAM = "api_key";
    private final String API_KEY = "0e0r37W1LCv9tawfzYcdrgXDmiAhlT3rblr25yhx";

    public RequisicaoFoto(Activity listener){
        Log.d("req","Requisicao foto");

        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        respostalmagem = (Respostalmagem) listener;
        context = listener.getApplicationContext();

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                HttpUrl urlOriginal = original.url();
                HttpUrl novaUrl = urlOriginal.newBuilder().addQueryParameter(API_KEY_PARAM,API_KEY).build();

                Request.Builder requestBuilder = original.newBuilder().url(novaUrl);

                Request novoRequest = requestBuilder.build();

                return chain.proceed(novoRequest);

            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.nasa.gov/").client(client.build()).addConverterFactory(GsonConverterFactory.create()).build();

        astropicApi = retrofit.create(AstropicApi.class);
        loadingDado = false;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getFoto(){

        String date = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Call<Foto> call = astropicApi.buscaFoto(date);
        loadingDado = true;
        call.enqueue(new Callback<Foto>(){

            private retrofit2.Call<Foto> call;
            private retrofit2.Response<Foto> response;

            public void onResponse(retrofit2.Call<Foto> call, retrofit2.Response<Foto> response){

                this.call = call;
                this.response = response;
                Foto foto = response.body();

                Log.d("teste",response.code() + "");

                if (!foto.getTipoMedia().equals("video")){
                    respostalmagem.receberNovaFoto(foto);
                    loadingDado = false;
                }else{
                    getFoto();
                }
            }

            public void onFailure(retrofit2.Call<Foto> call, Throwable t){
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                loadingDado = false;
            }

        });

    }

    public boolean isLoadingDado(){
        return loadingDado;
    }

}
