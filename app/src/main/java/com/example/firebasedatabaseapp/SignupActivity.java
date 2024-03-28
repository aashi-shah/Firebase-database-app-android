package com.example.firebasedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText editText1,editText2,editText3,editText4;
    Button button;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editText1 = findViewById(R.id.sfn);
        editText2 = findViewById(R.id.sln);
        editText3 = findViewById(R.id.semail);
        editText4 = findViewById(R.id.spass);
        button = findViewById(R.id.signup);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-databaseapp-bb154-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Username");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = editText1.getText().toString();
                String ln = editText2.getText().toString();
                String email = editText3.getText().toString();
                String pass = editText4.getText().toString();
                String id = databaseReference.push().getKey();
                UserModel userModel = new UserModel();
                userModel.setFirstname(fn);
                userModel.setLastname(ln);
                userModel.setId(id);
                userModel.setEmail(email);
                userModel.setPassword(pass);
                databaseReference.child(id).setValue(userModel);
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
            }
        });

    }
}