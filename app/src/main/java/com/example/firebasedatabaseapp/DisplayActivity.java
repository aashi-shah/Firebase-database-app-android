package com.example.firebasedatabaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    ListView listView;
    Button button;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fir-databaseapp-bb154-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Name");
        listView = findViewById(R.id.listview);
        button = findViewById(R.id.add_driver);
        ArrayList<DriverModel> drivernames = new ArrayList<DriverModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DriverModel driverModel = dataSnapshot1.getValue(DriverModel.class);
                    drivernames.add(driverModel);
                }
                MybaseAdapter mybaseAdapter = new MybaseAdapter(DisplayActivity.this,drivernames);
                listView.setAdapter(mybaseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
//                String fn = drivernames.get(i).getFirstname();
//                String ln = drivernames.get(i).getLastname();
//                String id = drivernames.get(i).getId();

                Intent in2 = new Intent(DisplayActivity.this,UpdateActivity.class);

//                in2.putExtra("KEY_FN",fn);
//                in2.putExtra("KEY_LN",ln);
//                in2.putExtra("KEY_ID",id);
                startActivity(in2);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DisplayActivity.this,AddActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}