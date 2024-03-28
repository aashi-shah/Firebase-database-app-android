package com.example.firebasedatabaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText editText1,editText2;
    Button button;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText1 = findViewById(R.id.lemail);
        editText2 = findViewById(R.id.lpass);
        button = findViewById(R.id.login);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-databaseapp-bb154-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Username");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText1.getText().toString();
                String pass = editText2.getText().toString();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            UserModel userModel = dataSnapshot1.getValue(UserModel.class);
                            if(email.equals(userModel.getEmail()) && pass.equals(userModel.getPassword())){
                                Intent i = new Intent(LoginActivity.this,UserActivity.class);
                                i.putExtra("KEY_LOGINEMAIL",email);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}
