package com.example.testmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UpdateData extends AppCompatActivity {

    TextView updt,txt0,txt1,txt2,txt3,qlty1,qnty1,cst1,pet;
    Button update;
    FirebaseDatabase rootnode;
    DatabaseReference referenceNode,referenceNode1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        updt=findViewById(R.id.updt);
        txt0=findViewById(R.id.txt0);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        qlty1=findViewById(R.id.qlty1);
        qnty1=findViewById(R.id.qnty1);
        cst1=findViewById(R.id.cst1);
        pet=findViewById(R.id.pet);
        update=findViewById(R.id.save);

        updt.setText("UPDATED DATA");
        txt3.setText("QUALITY:");
        txt2.setText("QUANTITY:");
        txt1.setText("COST:");
        txt0.setText("BUNK NAME:");

        Random number = new Random();
        float l = number.nextFloat();
        int num = number.nextInt(50);
        final float qltyy = (100-l*num);
        final int qntyy = number.nextInt(10);
        final float cost = (float) (qntyy*86.3);
        String qltyyy=String.format("%.2f",qltyy);
        qlty1.setText(qltyyy+"%");
        qnty1.setText(String.valueOf(qntyy));
        cst1.setText(String.valueOf(cost));

        Intent intent = getIntent();
        final String bunkName = intent.getStringExtra("BunkName");
        pet.setText(bunkName);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode = FirebaseDatabase.getInstance();
                referenceNode = rootnode.getReference("updated Data");
                referenceNode1=rootnode.getReference("Data").child("current status");
                SimpleDateFormat sp = new SimpleDateFormat("dd/MM/YYYY");
                Date dates = new Date();
                String date=sp.format(dates);
                String quality=String.valueOf(qltyy);
                String quantity=String.valueOf(qntyy);
                String costt=String.valueOf(cost);
                Datas datas = new Datas(bunkName,date,quality,quantity,costt);
                referenceNode.child(bunkName).setValue(datas);
                referenceNode1.child("Quality").setValue(quality);
                referenceNode1.child("Quantity").setValue(quantity);
                Intent historyy=new Intent(UpdateData.this,DashBoard.class);
                historyy.putExtra("bunk_name",bunkName);
                startActivity(historyy);
                getClear();
                Toast.makeText(UpdateData.this,"data updated",Toast.LENGTH_SHORT).show();
                finish();
            }

            private void getClear() {
                qlty1.setText("");
                qnty1.setText("");
                cst1.setText("");
                pet.setText("");
            }
        });

    }



}