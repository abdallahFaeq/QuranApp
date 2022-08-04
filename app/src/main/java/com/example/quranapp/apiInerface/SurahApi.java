package com.example.quranapp.apiInerface;

import com.example.quranapp.model.Surah;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SurahApi {
    // fixed url : http://api.alquran.cloud/v1/
    // variable or relative url : surah
    @GET("surah")
    Call<Surah> getSurahDetails();

}
