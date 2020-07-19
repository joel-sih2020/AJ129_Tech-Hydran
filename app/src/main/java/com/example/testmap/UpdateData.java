package com.example.testmap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class UpdateData extends AppCompatActivity {

    TextView updt,txt0,txt1,txt2,txt3,qlty1,qnty1,cst1,pet;
    Button update;
    FirebaseDatabase rootnode;
    DatabaseReference referenceNode;



    @SuppressLint("SetTextI18n")
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

        txt0.setText("QUALITY:");
        txt1.setText("QUANTY:");
        txt2.setText("COST:");
        txt3.setText("BUNK NAME:");

        Random number = new Random();
        float l = number.nextFloat();
        int num = number.nextInt(50);
        final float qltyy = 100-l*num;
        final int qntyy = number.nextInt(10);
        final float cost = (float) (qntyy*76.3);
        qlty1.setText(String.valueOf(qltyy));
        qnty1.setText(String.valueOf(qntyy));
        cst1.setText(String.valueOf(cost));
        Intent intent = getIntent();
        final String bunkNam = intent.getStringExtra("BunkName");
        pet.setText(bunkNam);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode = FirebaseDatabase.getInstance();
                referenceNode = rootnode.getReference("updated Data");
                String quality=String.valueOf(qltyy);
                String quantity=String.valueOf(qntyy);
                String costt=String.valueOf(cost);
                Datas datas = new Datas(quality,quantity,costt);
                referenceNode.child(bunkNam).setValue(datas);
                getClear();
                Toast.makeText(UpdateData.this,"data updated",Toast.LENGTH_SHORT).show();
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