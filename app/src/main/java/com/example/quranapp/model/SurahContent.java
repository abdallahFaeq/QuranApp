package com.example.quranapp.model;

public class SurahContent {
    private SurahDetailsContent [] result;

    public SurahContent(SurahDetailsContent[] result) {
        this.result = result;
    }

    public SurahDetailsContent[] getResult() {
        return result;
    }

    public void setResult(SurahDetailsContent[] result) {
        this.result = result;
    }
}
