package com.example.testmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BunkDetails extends AppCompatActivity {
TextView bunkname,quality,quantity,petcost,date,density1,temperature1,percentage;
Button cancell;
DatabaseReference reference2;
ImageView circle1,img0,img1,img2,img3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunk_details);

        String Bname = getIntent().getStringExtra("BName");
        bunkname=findViewById(R.id.bunkName);
        quality=findViewById(R.id.quality);
        quantity=findViewById(R.id.quantity);
        petcost=findViewById(R.id.petcost);
        cancell=findViewById(R.id.cancell);

        density1=findViewById(R.id.density);
        temperature1=findViewById(R.id.temperature);
        circle1=findViewById(R.id.circle);
        percentage=findViewById(R.id.pertentage);
        img0=findViewById(R.id.img00);
        img1=findViewById(R.id.img11);
        img2=findViewById(R.id.img22);
        img3=findViewById(R.id.img33);



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
                Float qltyy = Float.parseFloat(quality1);
                Log.d("wrking","0");
                bunkname.setText(petName);
                quality.setText(String.format("%.2f",qltyy));
                quantity.setText(quantity1);
                petcost.setText(petcost1);
                Log.d("wrking","1");
                density1.setText("730");
                temperature1.setText("28");
                Log.d("wrking","2");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BunkDetails.this,"DATA not found",Toast.LENGTH_SHORT).show();
            }
        });
        reference2=FirebaseDatabase.getInstance().getReference("Data");
       /* reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Density=snapshot.child("Density").getValue().toString();
                Log.d("wrking","2");
                String Temperature=snapshot.child("Temperature").getValue().toString();
                density1.setText(Density);
                Log.d("wrking","3");
                temperature1.setText(Temperature);
                Log.d("wrking","4");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}