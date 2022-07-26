package com.example.quranapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quranapp.fragment.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // lauch splash screen fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.your_container, new SplashScreen())
                .commit();



    }
}