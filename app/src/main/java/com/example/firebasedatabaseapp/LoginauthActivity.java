package com.example.firebasedatabaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginauthActivity extends AppCompatActivity {

    EditText editText1,editText2;
    Button button;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginauth);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-databaseapp-bb154-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Username");

        editText1 = findViewById(R.id.alemail);
        editText2 = findViewById(R.id.alpass);
        button = findViewById(R.id.alogin);
        progressDialog = new ProgressDialog(LoginauthActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText1.getText().toString();
                String pass = editText2.getText().toString();
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginauthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            String uid = firebaseAuth.getUid();
                            System.out.println(uid);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                        AuthUserModel authUserModel = dataSnapshot1.getValue(AuthUserModel.class);

                                        String id = authUserModel.getId();
                                        if(uid.equals(id)){
                                            String fn = authUserModel.getFirstname();
                                            String ln = authUserModel.getLastname();
                                            String email1 = authUserModel.getEmail();
                                            Intent i = new Intent(LoginauthActivity.this,DisplayAuthActivity.class);
                                            i.putExtra("A_EMAIL",email1);
                                            i.putExtra("A_FN",fn);
                                            i.putExtra("A_LN",ln);
                                            startActivity(i);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                });

            }
        });

    }
}