package com.example.quranapp;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObj {

    private static Retrofit retrofit = null;

    public static void setRetrofit(Retrofit retrofit) {
        RetrofitObj.retrofit = retrofit;
    }

    public static Retrofit getRetrofitInstance(String baseUrl){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
