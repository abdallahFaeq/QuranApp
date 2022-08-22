package com.example.quranapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.quranapp.adapter.HomeAdapter;
import com.example.quranapp.fragment.HomeScreen;
import com.example.quranapp.fragment.SplashScreen;
import com.example.quranapp.fragment.SurahDetails;
import com.example.quranapp.model.SuraDetails;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements HomeScreen.OnListenerToSendData{

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

    // launch surahDetails fragment
    public void launchSurahDetailsFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.your_container, fragment)
                .commit();
    }
    // send data from this activity to surahDetails fragment when click item of home's recycler
    public void sendDataToSurahDetailsFragment(SuraDetails suraDetails, int position){
        SurahDetails surahDetails = new SurahDetails();

        launchSurahDetailsFragment(surahDetails);

        Bundle bundle = new Bundle();
        bundle.putSerializable("surahDetails",suraDetails);
        bundle.putInt("position", position);
        surahDetails.setArguments(bundle);
    }

    // when click item of home recycler will recieve data from home fragment in this activity
    // recieve data from home fragment
    // implementation for click on item in recycler view that show suraDetails
    @Override
    public void onListener(SuraDetails suraDetails, int positon) {
        sendDataToSurahDetailsFragment(suraDetails, positon);
    }


}