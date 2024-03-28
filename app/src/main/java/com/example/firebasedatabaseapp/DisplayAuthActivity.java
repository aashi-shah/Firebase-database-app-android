package com.example.firebasedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayAuthActivity extends AppCompatActivity {
    TextView textView1,textView2,textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_auth);
        textView1 = findViewById(R.id.Authfn);
        textView2 = findViewById(R.id.Authln);
        textView3 = findViewById(R.id.Authemail);

        Intent i = getIntent();
        String email = i.getStringExtra("A_EMAIL");
        String fn = i.getStringExtra("A_FN");
        String ln = i.getStringExtra("A_LN");

        textView1.setText(fn);
        textView2.setText(ln);
        textView3.setText(email);
    }
}