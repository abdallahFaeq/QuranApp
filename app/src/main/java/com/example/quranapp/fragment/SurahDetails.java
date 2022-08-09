package com.example.quranapp.fragment;

import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quranapp.R;
import com.example.quranapp.RetrofitObj;
import com.example.quranapp.adapter.SurahDetailsAdapter;
import com.example.quranapp.apiInerface.SurahApi;
import com.example.quranapp.databinding.FragmentSurahDetailsBinding;
import com.example.quranapp.model.SuraDetails;
import com.example.quranapp.model.SurahContent;
import com.example.quranapp.model.SurahDetailsContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    RadioGroup radioGroup;
    int selectedId;
    RadioButton radioButton;
    int positionClickedOnHomeRecycler;
    boolean selectedItem;
    private String translationKey = "english_saheeh";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
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

        selectedItem = false;

        Bundle bundle = getArguments();
        if (bundle != null){
            SuraDetails suraDetails = (SuraDetails) bundle.getSerializable("surahDetails");
            binding.surahName.setText(suraDetails.getName());
            binding.translation.setText(suraDetails.getEnglishNameTranslation());
            binding.type.setText(suraDetails.getRevelationType());
            binding.ayatNumber.setText(suraDetails.getNumberOfAyahs()+"");
            positionClickedOnHomeRecycler = bundle.getInt("position");
        }
        surahs = new ArrayList<>();

        // get data by retrofit
        getDataByRetrofit(translationKey);

        // instantiate shared prefernces to save state of checked radio button
        sharedPreferences = getActivity().getSharedPreferences("pref",0);
        editor = sharedPreferences.edit();



        // click setting image button to show list of translation
        binding.settingIamgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialogTheme);

                // create view that contain bottom sheet dialog
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view1 = inflater.inflate(R.layout.bottom_sheet_layout, view.findViewById(R.id.sheet_container));

                // selected id stored in shared preferences
                // retrieved data from shared preferences
                 int selectedIdSP = sharedPreferences.getInt("selectedIdSP", 4);

                if (selectedItem){
                    radioButton = view1.findViewById(selectedIdSP);
                    radioButton.setChecked(true);
                }

                view1.findViewById(R.id.save_setting_button).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        radioGroup = view1.findViewById(R.id.translation_rg);
                        selectedId = radioGroup.getCheckedRadioButtonId();

                        // store data in shared preferences
                        editor.putInt("selectedIdSP",selectedId);
                        editor.commit();

                        radioButton = view1.findViewById(selectedId);
                        radioButton.setChecked(true);

                        switch (selectedId){
                            case -1 :
                                Toast.makeText(getContext(), "nothing selected", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.english_rb:
                                translationKey = "english_saheeh";
                                break;
                            case R.id.french_rb:
                                translationKey = "french_montada";
                                break;
                            case R.id.spanish_rb:
                                translationKey = "spanish_garcia";
                                break;
                            case R.id.turkish_rb:
                                translationKey = "turkish_shaban";
                                break;
                        }
                        selectedItem = true;
                        bottomSheetDialog.dismiss();
                        getDataByRetrofit(translationKey);
                    }
                });
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
            }
        });

        // create custom search view by edit text and text change listener for it
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
    // retrive data from api and load it in recycler view
    private void getDataByRetrofit(String translationKey) {
        Retrofit retrofit = RetrofitObj.getRetrofitInstance("https://quranenc.com/api/v1/translation/sura/");

        SurahApi surahApi = retrofit.create(SurahApi.class);
        Call<SurahContent> surahContentCall = surahApi.getSurahContent(translationKey,positionClickedOnHomeRecycler+1);
        surahs.clear();
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
    }

    public void putDataInRecyclerView(ArrayList<SurahDetailsContent> surahs){
        adapter = new SurahDetailsAdapter(surahs,getContext());
        binding.surahDetailsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.surahDetailsRv.setHasFixedSize(true);
        binding.surahDetailsRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    // filter method is used to search view
    public void filter(String id){
        ArrayList<SurahDetailsContent> surahsList = new ArrayList<>();
        for (SurahDetailsContent surahDetailsContent:surahs) {
            if (String.valueOf(surahDetailsContent.getId()).contains(id)){
                surahsList.add(surahDetailsContent);
            }
            adapter.filter(surahsList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataByRetrofit(translationKey);
    }
}