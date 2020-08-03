package com.example.testmap;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GenerateQuality {
    float tempo,tempo1,den;
    DatabaseReference reff=FirebaseDatabase.getInstance().getReference("InputData");
    int appQlt;
    public int Calculate(){
        getdata();
        appQlt=stDensity(tempo,tempo1,den);
        return appQlt;
    }

    private int stDensity(float tempo, float tempo1, Float den) {
        float stden= (float) (den/(((tempo-tempo1)*0.01)+1));
        if(stden>718 && stden<722){
            appQlt=100;
        }
        else if (stden>730 && stden<735){
            appQlt=90;
        }
        else if(stden>738 && stden<742){
            appQlt=80;
        }
        else {
            appQlt=70;
        }
        return appQlt;
    }
    public void getdata(){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ipdensity=snapshot.child("inpoden").getValue().toString();
                String temperature1=snapshot.child("temp1").getValue().toString();
                String temperature2=snapshot.child("temp2").getValue().toString();
                tempo=Float.parseFloat(temperature1);
                tempo1=Float.parseFloat(temperature2);
                den=Float.parseFloat(ipdensity);
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
