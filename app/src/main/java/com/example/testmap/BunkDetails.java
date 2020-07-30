package com.example.testmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BunkDetails extends AppCompatActivity {
FirebaseDatabase rootnode2;
DatabaseReference reference2;
TextView bunkname,quality,quantity,petcost,date;
Button cancell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunk_details);
        String Bname = getIntent().getStringExtra("BName");
        bunkname=findViewById(R.id.bunkName);
        quality=findViewById(R.id.quality);
        quantity=findViewById(R.id.quantity);
        petcost=findViewById(R.id.petcost);
        date=findViewById(R.id.date);
        cancell=findViewById(R.id.cancell);

        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reference2=FirebaseDatabase.getInstance().getReference("updated Data").child(Bname);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String petName=snapshot.child("bunkName").getValue().toString();
                String petcost1=snapshot.child("cost").getValue().toString();
                String quality1=snapshot.child("quality").getValue().toString();
                String quantity1=snapshot.child("quantity").getValue().toString();
                String date1=snapshot.child("date").getValue().toString();

                bunkname.setText(petName);
                quality.setText("QUANTIY:"+quality1);
                quantity.setText("QUALITY:"+quantity1);
                petcost.setText("COST:"+petcost1);
                date.setText("DATE:"+date1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BunkDetails.this,"DATA not found",Toast.LENGTH_SHORT).show();
            }
        });
    }
}