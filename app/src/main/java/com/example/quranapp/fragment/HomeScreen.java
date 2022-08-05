package com.example.quranapp.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.quranapp.MainActivity;
import com.example.quranapp.R;
import com.example.quranapp.RetrofitObj;
import com.example.quranapp.adapter.HomeAdapter;
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

public class HomeScreen extends Fragment{
    ArrayList<SuraDetails> surahs;
    private FragmentHomeScreenBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListenerToSendData){
            onListenerToSendData = (OnListenerToSendData) context;
        }else{
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }

    }


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

        Retrofit retrofit =  RetrofitObj.getRetrofitInstance("http://api.alquran.cloud/v1/");

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
        HomeAdapter homeAdapter = new HomeAdapter(view.getContext(),surahs);
        binding.recyclerViewQuran.setAdapter(homeAdapter);
        binding.recyclerViewQuran.setHasFixedSize(true);

        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, SuraDetails suraDetails) {
                if (onListenerToSendData != null){
                    onListenerToSendData.onListener(suraDetails);
                }
            }
        });
    }
    OnListenerToSendData onListenerToSendData;

    public void setOnListenerToSendData(OnListenerToSendData onListenerToSendData) {
        this.onListenerToSendData = onListenerToSendData;
    }

    public interface OnListenerToSendData{
        void onListener(SuraDetails suraDetails);
    }

    @Override
    public void onStop() {
        super.onStop();
        RetrofitObj.setRetrofit(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onListenerToSendData = null;
    }
}