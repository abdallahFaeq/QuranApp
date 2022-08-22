package com.example.quranapp.model;

public class Surah {
    SuraDetails []data;

    public Surah(SuraDetails[] data) {
        this.data = data;
    }

    public SuraDetails[] getData() {
        return data;
    }

    public void setData(SuraDetails[] data) {
        this.data = data;
    }
}
