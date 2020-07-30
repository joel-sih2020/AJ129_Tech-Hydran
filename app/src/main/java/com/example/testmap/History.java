package com.example.testmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History extends AppCompatActivity {
TextView hisTitle;
ListView listView;
ArrayList<String> list = new ArrayList<>();
ArrayAdapter<String> adapter;
DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //String bunk_name="Barat";

        listView=findViewById(R.id.lists);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1);
        listView.setAdapter(adapter);
        reference=FirebaseDatabase.getInstance().getReference("updated Data");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value =snapshot.child("bunkName").getValue().toString();
                adapter.add(value);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String value =snapshot.child("bunkName").getValue().toString();
                adapter.remove(value);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String names = parent.getItemAtPosition(position).toString();
                Intent details= new Intent(History.this,BunkDetails.class);
                details.putExtra("BName",names);
                startActivity(details);
            }
        });

    }
}