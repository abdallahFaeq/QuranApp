package com.example.quranapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.databinding.ItemContentOfSurahBinding;
import com.example.quranapp.model.SurahContent;
import com.example.quranapp.model.SurahDetailsContent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SurahDetailsAdapter extends RecyclerView.Adapter<SurahDetailsAdapter.SurahDetailsHolder> {
    private ArrayList<SurahDetailsContent> surahs;
    private Context mcontext;

    public SurahDetailsAdapter(ArrayList<SurahDetailsContent> surahs, Context mcontext) {
        this.surahs = surahs;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public SurahDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContentOfSurahBinding binding = ItemContentOfSurahBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SurahDetailsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahDetailsHolder holder, int position) {
        SurahDetailsContent surahDetailsContent = surahs.get(position);
        holder.arabicText.setText(surahDetailsContent.getArabic_text());
        holder.translation.setText(surahDetailsContent.getTranslation());
        holder.ayaNO.setText(surahDetailsContent.getAya()+"");
    }

    @Override
    public int getItemCount() {
        return surahs.size();
    }

    class SurahDetailsHolder extends RecyclerView.ViewHolder{
        TextView ayaNO, arabicText, translation;
        public SurahDetailsHolder(@NonNull ItemContentOfSurahBinding binding) {
            super(binding.getRoot());
            ayaNO = binding.ayaNo;
            arabicText = binding.arabicText;
            translation = binding.translation;
        }
    }
    public void filter(ArrayList<SurahDetailsContent> surahs){
        this.surahs = surahs;
        notifyDataSetChanged();
    }
}
