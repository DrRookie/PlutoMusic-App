package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Guide extends AppCompatActivity {

    //this is the tutorial of the app


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_one);


    }

    public void next(View view) {
        setContentView(R.layout.activity_guide_two);

    }


    public void nexttwo(View view) {
        setContentView(R.layout.activity_guide_three);
    }

    public void nextnext(View view) {
        setContentView(R.layout.activity_guide_four);
    }

    public void gotonext(View view) {
        setContentView(R.layout.activity_guide_six);
    }

    public void gotolastguide(View view) {
        setContentView(R.layout.guide_eight);
    }

    public void gotodiscover(View view) {
        Intent intent = new Intent(this, Discover.class);
        startActivity(intent);

    }

    public void skiptoDiscover(View view) {
        Intent intent = new Intent(this, Discover.class);
        startActivity(intent);
    }
}