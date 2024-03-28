package com.example.firebasedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        textView = findViewById(R.id.email);

        Intent i = getIntent();
        String email = i.getStringExtra("KEY_LOGINEMAIL");
        textView.setText(email);
    }
}