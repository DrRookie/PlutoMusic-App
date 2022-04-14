package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    //creating a homecollection variable and initializing it.
    HomeCollection homeCollection = new HomeCollection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public void sendDataToActivity(int index) {

        Intent intent = new Intent(this, PlaySongActivityThree.class);
        intent.putExtra("index", index);
        startActivity(intent);


    }

    public void click(View view) {
        // this method take the onClick and gets the id, transforms
        //to string and the string id is then used to find the song

        int assignedInterger = view.getId();
        String id = getResources().getResourceEntryName(assignedInterger);
        int currentArrayIndex = homeCollection.searchHomeSongById(id);
        sendDataToActivity(currentArrayIndex);

    }

    //icon button leads to different pages

    public void godiscover(View view) {
        Intent intent = new Intent(this, Discover.class);
        startActivity(intent);
    }

    public void gohome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void goprofile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void gotosamplesongs(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotofavpage(View view) {

        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {

    }


}