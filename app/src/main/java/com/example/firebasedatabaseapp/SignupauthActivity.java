package com.example.firebasedatabaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupauthActivity extends AppCompatActivity {

    EditText editText1,editText2,editText3,editText4;
    Button button,button1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupauth);
        editText1 = findViewById(R.id.afn);
        editText2 = findViewById(R.id.aln);
        editText3 = findViewById(R.id.aemail);
        editText4 = findViewById(R.id.apass);
        button = findViewById(R.id.asignup);
        button1 = findViewById(R.id.alogin);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-databaseapp-bb154-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Username");
        progressDialog = new ProgressDialog(SignupauthActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = editText1.getText().toString();
                String ln = editText2.getText().toString();
                String email = editText3.getText().toString();
                String pass = editText4.getText().toString();


                progressDialog.setMessage("Loading...");
                progressDialog.show();
                firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignupauthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            String id = firebaseAuth.getUid();
                            AuthUserModel authUserModel = new AuthUserModel();
                            authUserModel.setFirstname(fn);
                            authUserModel.setLastname(ln);
                            authUserModel.setEmail(email);
                            authUserModel.setPassword(pass);
                            authUserModel.setId(id);
                            databaseReference.child(id).setValue(authUserModel);
                            Intent i = new Intent(SignupauthActivity.this,LoginauthActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });

                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupauthActivity.this,LoginauthActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}