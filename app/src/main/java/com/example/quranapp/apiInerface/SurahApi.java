package com.example.quranapp.apiInerface;

import com.example.quranapp.model.Surah;
import com.example.quranapp.model.SurahContent;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface SurahApi {
    // fixed url : http://api.alquran.cloud/v1/
    // variable or relative url : surah
    @GET("surah")
    Call<Surah> getSurahDetails();

    // https://quranenc.com/api/v1/translation/sura/english_saheeh/1
    // fixed url : https://quranenc.com/api/v1/translation/sura/
    // variable or relative url : english_saheeh/1
    @GET("english_saheeh/1")
    Call<SurahContent> getSurahContent();
}
