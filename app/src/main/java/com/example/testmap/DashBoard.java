package com.example.testmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashBoard extends AppCompatActivity {
    TextView title;
    Button currentStatus, logOut, history, map;
    SharedPreferences preferences;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private boolean checksensor;
    float value;
    private static final String LOGIN = "userIN";
    private static final String KEY = "name";
    DatabaseReference reference;
    ImageView image11,img12,img13,img14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        title = findViewById(R.id.title);
        currentStatus = findViewById(R.id.currentstatus);
        history = findViewById(R.id.history);
        logOut = findViewById(R.id.logout);

        image11=findViewById(R.id.txt080);
        img12=findViewById(R.id.img12);
        img13=findViewById(R.id.img13);
        img14=findViewById(R.id.img14);

        preferences = getSharedPreferences(LOGIN, MODE_PRIVATE);
        String key = preferences.getString(KEY, null);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent back = new Intent(DashBoard.this, LoginAct.class);
                startActivity(back);
                finish();
            }
        });
        currentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("current status");
                reference.child("phoneTemp").setValue(value);
                Intent intent = new Intent(DashBoard.this, SensorAct.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent his = new Intent(DashBoard.this, History.class);
                startActivity(his);
            }
        });
        map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapss = new Intent(DashBoard.this, MapActivity.class);
                startActivity(mapss);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}