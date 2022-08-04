package com.example.quranapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quranapp.R;
import com.example.quranapp.adapter.SurahAdapter;
import com.example.quranapp.apiInerface.SurahApi;
import com.example.quranapp.databinding.FragmentHomeScreenBinding;
import com.example.quranapp.model.SuraDetails;
import com.example.quranapp.model.Surah;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeScreen extends Fragment {
    ArrayList<SuraDetails> surahs;
    private FragmentHomeScreenBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surahs = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.alquran.cloud/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SurahApi surahApi = retrofit.create(SurahApi.class);

        Call<Surah> surahResponse = surahApi.getSurahDetails();

        putDataInRecyclerView(view, surahs);
        surahResponse.enqueue(new Callback<Surah>() {
            @Override
            public void onResponse(Call<Surah> call, Response<Surah> response) {
                if (response.code() != 200){
                    Toast.makeText(getActivity(), "please check network connection", Toast.LENGTH_SHORT).show();
                }
                Surah surah = response.body();
                surahs.addAll(Arrays.asList(surah.getData()));

                putDataInRecyclerView(view, surahs);
            }

            @Override
            public void onFailure(Call<Surah> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "on failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void putDataInRecyclerView(View view ,ArrayList<SuraDetails> surahs){
        binding.recyclerViewQuran.setLayoutManager(new LinearLayoutManager(view.getContext()));
        SurahAdapter surahAdapter = new SurahAdapter(view.getContext(),surahs);
        binding.recyclerViewQuran.setAdapter(surahAdapter);
        binding.recyclerViewQuran.setHasFixedSize(true);
    }
}