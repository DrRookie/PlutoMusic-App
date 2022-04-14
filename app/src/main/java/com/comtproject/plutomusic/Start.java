package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }


    public void nextPage(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {


    }

}