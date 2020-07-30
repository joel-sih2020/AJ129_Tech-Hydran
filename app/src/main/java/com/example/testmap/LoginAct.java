package com.example.testmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginAct extends AppCompatActivity {
    EditText Uname,Upass;
    TextView create,forgotPass,phno;
    Button login;
    String usrName,usrPassword,phoneNumber;
    private NotificationManager notificationManager;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    SharedPreferences preferences;
    private static final String LOGIN="userIN";
    private static final String KEY="name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Uname=findViewById(R.id.Uname);
        Upass=findViewById(R.id.Upass);
        login=findViewById(R.id.login);
        create=findViewById(R.id.create);
        forgotPass=findViewById(R.id.forgotPass);

        preferences=getSharedPreferences(LOGIN,MODE_PRIVATE);

        String key=preferences.getString(KEY,null);
        if(key!=null){
            Intent sensoract = new Intent(LoginAct.this, DashBoard.class);
            startActivity(sensoract);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkName() | !checkPass()){
                    return;
                }
                checkUser();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAct.this,Signup.class);
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBox();
            }
        });
    }

    private void showBox() {
        Dialog dialog = new Dialog(LoginAct.this);
        dialog.setContentView(R.layout.forgot_password);
        dialog.setTitle("Forgot Password");
        phno=dialog.findViewById(R.id.getphone);
        Button nxt=dialog.findViewById(R.id.verify);
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usrName=Uname.getText().toString();
                phoneNumber=phno.getText().toString();
                if(phoneNumber.isEmpty()){
                    phno.setError("enter the phone number");
                    Toast.makeText(LoginAct.this,phoneNumber,Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    //Toast.makeText(LoginAct.this,usrName+phoneNumber,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginAct.this,SetpasswordAct.class);
                    intent.putExtra("Name",usrName);
                    intent.putExtra("phoneNumber",phoneNumber);
                    startActivity(intent);
                    finish();
                }
            }
        });
        dialog.show();
    }

    private void checkUser() {
        usrName=Uname.getText().toString();
        usrPassword=Upass.getText().toString();
        reference= FirebaseDatabase.getInstance().getReference("User");
        Query check = reference.orderByChild("userName").equalTo(usrName);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passDB = snapshot.child(usrName).child("password").getValue(String.class);
                    assert passDB != null;
                    if(passDB.equals(usrPassword)){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(KEY,usrName);
                        editor.apply();
                        Intent sensor = new Intent(LoginAct.this,DashBoard.class);
                        startActivity(sensor);

                    }
                    else {
                        Upass.setError("wrong password");
                    }
                }
                else {
                    Toast.makeText(LoginAct.this,"User not found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginAct.this,"user name or password in wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Boolean checkName(){
        String chck = Uname.getText().toString();
        if (chck.isEmpty()){
            Uname.setError("enter the user name");
            return false;
        }
        else {
            Uname.setError(null);
            return true;
        }
    }
    private Boolean checkPass(){
        String chck = Upass.getText().toString();
        if (chck.isEmpty()){
            Upass.setError("enter the password");
            return false;
        }
        else {
            Upass.setError(null);
            return true;
        }
    }


}
