package com.comtproject.plutomusic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;


public class Signup extends AppCompatActivity {

    // IF SIGNUP IS NOT OPENING UP,
    //cheange to classpath 'com.google.gms:google-services:4.3.0' in bulid.gradle project

    EditText name;
    EditText username;
    EditText password;
    Button signup;
    EditText email;
    ProgressBar progressBar;
    FirebaseAuth fAuth;




    //easy sign in
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = fAuth.getCurrentUser();
        if(user!= null) {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        signup = findViewById(R.id.signup);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);
        // IF SIGNUP IS NOT OPENING UP,
        //cheange to classpath 'com.google.gms:google-services:4.3.0' in bulid.gradle project
        fAuth = FirebaseAuth.getInstance();



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString().trim();
                String pa = password.getText().toString().trim();
                String na = name.getText().toString().trim();
                String un = username.getText().toString().trim();

                if (TextUtils.isEmpty(na)) {
                    name.setError("Name is Required !");
                    return;
                }
                if (TextUtils.isEmpty(em)) {
                    email.setError("Email is Required !");
                    return;
                }
                if (TextUtils.isEmpty(un)) {
                    username.setError("Username is Required !");
                    return;
                }
                if (TextUtils.isEmpty(pa)) {
                    password.setError("Password is Required !");
                    return;
                }
                if (password.length() > 0 && password.length() < 6) {
                    password.setError("Password must be >= 6 characters");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                // register the user in Firebase

                fAuth.createUserWithEmailAndPassword(em, pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "Welcome " + un, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Guide.class));


                        } else {
                            Toast.makeText(Signup.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);


                        }

                    }
                });


            }
        });



    }












    public void gotologin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {


    }



}