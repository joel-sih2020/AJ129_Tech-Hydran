package com.example.testmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

public class DashBoard extends AppCompatActivity {
TextView title;
Button currentStatus,logOut,history,map;
SharedPreferences preferences;
    private static final String LOGIN="userIN";
    private static final String KEY="name";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        title=findViewById(R.id.title);
        currentStatus=findViewById(R.id.currentstatus);
        history=findViewById(R.id.history);
        logOut=findViewById(R.id.logout);
        preferences=getSharedPreferences(LOGIN,MODE_PRIVATE);
        String key=preferences.getString(KEY,null);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.commit();
                Intent back = new Intent(DashBoard.this,LoginAct.class);
                startActivity(back);
                finish();
            }
        });
        currentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this,SensorAct.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent his = new Intent(DashBoard.this,History.class);
                startActivity(his);
            }
        });
        map=findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapss=new Intent(DashBoard.this,MapActivity.class);
                startActivity(mapss);
            }
        });

    }
}