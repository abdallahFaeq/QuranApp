package com.example.quranapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.databinding.ItemSurahBinding;
import com.example.quranapp.model.SuraDetails;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.SurahHolder>{
    Context mContext;
    List<SuraDetails> mSurahDetailsList;
    public HomeAdapter(Context context, List<SuraDetails> suraDetailsList){
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(holder.getAdapterPosition(), mSurah);
                }
            }
        });
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
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position, SuraDetails suraDetails);
    }
}
