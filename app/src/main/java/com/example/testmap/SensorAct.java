package com.example.testmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SensorAct extends AppCompatActivity {
    TextView qlt00,quality,pertentage,qnt00,cst00,petcost,temperature,density,quantity,den,temp;
    Button btn;
    ImageView circle,img0,img1,img2,img3;
    FirebaseDatabase database;
    DatabaseReference reference,reference1;
    SharedPreferences preferences;
    float sensorTemp;
    private static final String LOGIN="userIN";
    private static final String KEY="name";
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My chennal","My chennal",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        preferences=getSharedPreferences(LOGIN,MODE_PRIVATE);
        Log.d("wrk","Wrk1");
        circle=findViewById(R.id.circle);
        img0=findViewById(R.id.img00);
        img1=findViewById(R.id.img11);
        img2=findViewById(R.id.img22);
        img3=findViewById(R.id.img33);

        qlt00=findViewById(R.id.qlt00);
        quality=findViewById(R.id.quality);
        pertentage=findViewById(R.id.pertentage);
        qnt00=findViewById(R.id.qnt00);
        cst00=findViewById(R.id.cst00);
        petcost=findViewById(R.id.petcost);
        temperature=findViewById(R.id.temperature);
        density=findViewById(R.id.density);
        quantity=findViewById(R.id.quantity);
        den=findViewById(R.id.den);
        temp=findViewById(R.id.temp);
        Log.d("wrk","Wrk2");
        btn=findViewById(R.id.cancell);
        getData();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent map = new Intent(SensorAct.this,MapActivity.class);
                startActivity(map);
               /* NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this
                        ,"My chennal");
                builder.setContentTitle("Notification from Sensor");
                builder.setContentText("your Petrol Level was low");
                builder.setSmallIcon(R.drawable.notify);
                builder.setAutoCancel(true);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
                notificationManagerCompat.notify(1,builder.build());*/
            }
        });

    }
    public void getData(){

        reference= database.getInstance().getReference("Data").child("current status");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Quantity = dataSnapshot.child("Quantity").getValue().toString();
                String Quality = dataSnapshot.child("Quality").getValue().toString();
                String Density= dataSnapshot.child("Density").getValue().toString();
                String Temperature=dataSnapshot.child("Temperature").getValue().toString();
                String Cost=dataSnapshot.child("Cost").getValue().toString();
                String phontemp=dataSnapshot.child("phoneTemp").getValue().toString();
                quality.setText(Quality);
                quantity.setText(Quantity+"L");
                petcost.setText("₹"+Cost);
                density.setText(Density+"kg/m3");
                temperature.setText(Temperature+"°C");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SensorAct.this,"data not found",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        createNotification();
    }

    private void createNotification() {
        reference=database.getInstance().getReference("notification").child("quantity");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                showNote();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void showNote() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(SensorAct.this
                , "My chennal");
        builder.setContentTitle("Notification from Sensor");
        builder.setContentText("your Petrol Level was low");
        builder.setSmallIcon(R.drawable.notify);
        builder.setAutoCancel(true);
        Intent intent = new Intent(SensorAct.this, SensorAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(SensorAct.this);
        notificationManagerCompat.notify(1, builder.build());
    }
    /*public String calculate(float temp0, float temp, float den){
        //reference1=FirebaseDatabase.getInstance().getReference("Data").child("current status");
        float temp1=0,temp2=0,ipDensity=0,k=(float) 0.01;
        String percent;
        float result=ipDensity/(((temp1-temp2)*k)+1);
        if(result>718 && result<725){
            return percent="100";
        }
        else if (result>726 && result<740){
            return percent="90";
        }
        else{
            return percent="80";
        }

    }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        createNotification();
    }
}