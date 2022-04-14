package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Discover extends AppCompatActivity {
    BestSongCollection bestSongCollection = new BestSongCollection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover2);


    }

    public void sendDataToActivity(int index) {

        Intent intent = new Intent(this, PlaySongActivityTwo.class);
        intent.putExtra("index", index);
        startActivity(intent);


    }

    public void Selection(View view) {
        int assignedInterger = view.getId();
        String id = getResources().getResourceEntryName(assignedInterger);
        int currentArrayIndex = bestSongCollection.searchBestSongById(id);
        sendDataToActivity(currentArrayIndex);

    }

    //all the icons lead to different pages

    public void todiscover(View view) {
        Intent intent = new Intent(this, Discover.class);
        startActivity(intent);
    }

    public void tohome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void toprofile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {

    }


}