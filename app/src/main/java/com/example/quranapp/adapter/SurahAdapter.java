package com.example.quranapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.databinding.ItemSurahBinding;
import com.example.quranapp.model.SuraDetails;

import java.util.ArrayList;
import java.util.List;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahHolder>{
    Context mContext;
    List<SuraDetails> mSurahDetailsList;
    public SurahAdapter(Context context, List<SuraDetails> suraDetailsList){
        this.mContext = context;
        this.mSurahDetailsList = suraDetailsList;
    }
    @NonNull
    @Override
    public SurahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSurahBinding binding = ItemSurahBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SurahHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahHolder holder, int position) {
        SuraDetails mSurah = mSurahDetailsList.get(position);

        holder.mSurahNameEnglish.setText(mSurah.getEnglishName());
        holder.mSurahNameArabic.setText(mSurah.getName());
        holder.mNumberOfSurah.setText(""+mSurah.getNumber());
        holder.mSurahType.setText(mSurah.getRevelationType());
        holder.mNumberOfAyat.setText(""+mSurah.getNumberOfAyahs());
    }

    @Override
    public int getItemCount() {
        return mSurahDetailsList.size();
    }

     class SurahHolder extends RecyclerView.ViewHolder{
        TextView mSurahNameArabic, mSurahNameEnglish, mSurahType, mNumberOfAyat, mNumberOfSurah;
        public SurahHolder(@NonNull ItemSurahBinding binding) {
            super(binding.getRoot());
            mSurahNameArabic = binding.surahNameArabicLabel;
            mSurahNameEnglish = binding.surahNameLabel;
            mSurahType = binding.surahTypeLabel;
            mNumberOfAyat = binding.numberOfAyatLabel;
            mNumberOfSurah = binding.surahNumerLabel;
        }
    }
}
