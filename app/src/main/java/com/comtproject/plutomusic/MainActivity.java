package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SongCollection songCollection = new SongCollection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void sendDataToActivity(int index) {

        Intent intent = new Intent(this, PlaySongActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);


    }

    public void handleSelection(View view) {
        int assignedInterger = view.getId();
        String id = getResources().getResourceEntryName(assignedInterger);
        Log.d("poly", "handleSelection: " + id);
        int currentArrayIndex = songCollection.searchSongById(id);
        Log.d("poly", "aaray index is : " + currentArrayIndex);
        sendDataToActivity(currentArrayIndex);


    }



    public void goback(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


}