package com.example.firebasedatabaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {
    Button button1,button2;
    EditText editText1,editText2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        editText1 = findViewById(R.id.get_fn);
        editText2 = findViewById(R.id.get_ln);

        button1 = findViewById(R.id.update);
        button2 = findViewById(R.id.delete);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-databaseapp-bb154-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Name");
        
        Intent in2 = getIntent();
        String fn = in2.getStringExtra("KEY_FN");
        String ln = in2.getStringExtra("KEY_LN");
        final String id = in2.getStringExtra("KEY_ID");
        editText1.setText(fn);
        editText2.setText(ln);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn1 = editText1.getText().toString();
                String ln1 = editText2.getText().toString();

                DriverModel driverModel = new DriverModel();
                driverModel.setId(id);
                driverModel.setFirstname(fn1);
                driverModel.setLastname(ln1);
                databaseReference.child(id).setValue(driverModel);

                Intent i = new Intent(UpdateActivity.this,DisplayActivity.class);
                startActivity(i);
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverModel driverModel = new DriverModel();

                databaseReference.child(id).removeValue();
                Intent i = new Intent(UpdateActivity.this,DisplayActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}