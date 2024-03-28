package com.example.firebasedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {
    EditText editText1,editText2;
    Button button1,button2;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editText1 = findViewById(R.id.fn);
        editText2 = findViewById(R.id.ln);

        button1 = findViewById(R.id.add);
        button2 = findViewById(R.id.display);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-databaseapp-bb154-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Name");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = editText1.getText().toString();
                String ln = editText2.getText().toString();
                String id = databaseReference.push().getKey();
                DriverModel driverModel = new DriverModel();
                driverModel.setFirstname(fn);
                driverModel.setLastname(ln);
                driverModel.setId(id);
                databaseReference.child(id).setValue(driverModel);
                editText1.setText("");
                editText2.setText("");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddActivity.this,DisplayActivity.class);
                startActivity(i);
            }
        });
    }
}