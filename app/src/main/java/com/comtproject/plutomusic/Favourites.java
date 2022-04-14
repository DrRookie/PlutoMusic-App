package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Favourites extends AppCompatActivity {

    RecyclerView favlist;
    SongAdapter songAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favlist = findViewById(R.id.recyclerview);

        songAdapter = new SongAdapter(PlaySongActivity.favlist);
        favlist.setAdapter(songAdapter);
        favlist.setLayoutManager(new LinearLayoutManager(this));


    }


    public void gobacktoprevpage(View view) {
        startActivity(new Intent(this, Home.class));


    }




}









