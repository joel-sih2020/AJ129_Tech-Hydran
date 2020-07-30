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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SensorAct extends AppCompatActivity {
    TextView txt0,txt1,txt2,qlty,qnty,cst;
    Button btn;
    FirebaseDatabase database;
    DatabaseReference reference;
    SharedPreferences preferences;
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

        txt0=findViewById(R.id.txt0);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        qlty=findViewById(R.id.qlty);
        qnty=findViewById(R.id.qnty);
        cst=findViewById(R.id.cst);

        btn=findViewById(R.id.nxt);
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

        reference= database.getInstance().getReference().child("Data").child("1");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Quantity = Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString();
                String Quality = dataSnapshot.child("Quality").getValue().toString();
                qnty.setText(Quantity+"ltrs");
                qlty.setText(Quality);
                cst.setText("100");

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

}