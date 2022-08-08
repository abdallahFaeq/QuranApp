package com.example.quranapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quranapp.R;
import com.example.quranapp.RetrofitObj;
import com.example.quranapp.adapter.SurahDetailsAdapter;
import com.example.quranapp.apiInerface.SurahApi;
import com.example.quranapp.databinding.FragmentSurahDetailsBinding;
import com.example.quranapp.model.SuraDetails;
import com.example.quranapp.model.SurahContent;
import com.example.quranapp.model.SurahDetailsContent;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SurahDetails extends Fragment {
    FragmentSurahDetailsBinding binding;
    ArrayList<SurahDetailsContent> surahs;
    SurahDetailsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSurahDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            SuraDetails suraDetails = (SuraDetails) bundle.getSerializable("surahDetails");
            binding.surahName.setText(suraDetails.getName());
            binding.translation.setText(suraDetails.getEnglishNameTranslation());
            binding.type.setText(suraDetails.getRevelationType());
            binding.ayatNumber.setText(suraDetails.getNumberOfAyahs()+"");
        }
        surahs = new ArrayList<>();
        Retrofit retrofit = RetrofitObj.getRetrofitInstance("https://quranenc.com/api/v1/translation/sura/");

        SurahApi surahApi = retrofit.create(SurahApi.class);
        Call<SurahContent> surahContentCall = surahApi.getSurahContent();

        surahContentCall.enqueue(new Callback<SurahContent>() {
            @Override
            public void onResponse(Call<SurahContent> call, Response<SurahContent> response) {
                if (response.isSuccessful()){
                    SurahContent surahContent = response.body();
                    surahs.addAll(Arrays.asList(surahContent.getResult()));
                    putDataInRecyclerView(surahs);
                }
            }

            @Override
            public void onFailure(Call<SurahContent> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    public void putDataInRecyclerView(ArrayList<SurahDetailsContent> surahs){
        adapter = new SurahDetailsAdapter(surahs,getContext());
        binding.surahDetailsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.surahDetailsRv.setHasFixedSize(true);
        binding.surahDetailsRv.setAdapter(adapter);
    }
    public void filter(String id){
        ArrayList<SurahDetailsContent> surahsList = new ArrayList<>();
        for (SurahDetailsContent surahDetailsContent:surahs) {
            if (String.valueOf(surahDetailsContent.getId()).contains(id)){
                surahsList.add(surahDetailsContent);
            }
            adapter.filter(surahsList);
        }
    }
}