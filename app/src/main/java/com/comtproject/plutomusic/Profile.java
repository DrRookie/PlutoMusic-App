package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    TextView name, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.nameuser);
        mail = findViewById(R.id.mail);



        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null) {
           name.setText(signInAccount.getDisplayName());
           mail.setText(signInAccount.getEmail());

        } else {
            name.setText("...");
            mail.setText("...");

        }
    }

    public void gotobackpage(View view) {
        super.onBackPressed();


    }



    //using firebase to let user log out
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Start.class));
        finish();

    }

}