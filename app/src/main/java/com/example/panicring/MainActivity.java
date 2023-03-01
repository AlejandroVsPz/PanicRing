package com.example.panicring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
    public void goLogIn(View view){
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
    }

    public void goRegister(View view){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}