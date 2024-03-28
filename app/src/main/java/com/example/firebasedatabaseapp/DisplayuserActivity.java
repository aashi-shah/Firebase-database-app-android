package com.example.firebasedatabaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DisplayuserActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Button button;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayuser);

        imageView = findViewById(R.id.imgemail);
        textView = findViewById(R.id.setemail);
        button = findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Glide.with(this).load(user.getPhotoUrl()).into(imageView);
        textView.setText(user.getEmail());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("498196212694-r06kdn910jngjlvm5add0piduprphc07.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //token generate using google console and set client id

        googleSignInClient = GoogleSignIn.getClient(this,gso);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                googleSignInClient.signOut().addOnCompleteListener(DisplayuserActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(DisplayuserActivity.this,SigninGoogleActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        });
    }
}