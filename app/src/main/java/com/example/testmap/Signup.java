package com.example.testmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;

public class Signup extends AppCompatActivity {

EditText name,phnumber,txtpassword,txtPassword1;
Button signUp,reset;
TextView txtcreate;
FirebaseDatabase rootNode;
DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        txtcreate=findViewById(R.id.txtcreate);
        name=findViewById(R.id.name);
        phnumber=findViewById(R.id.phnumber);
        txtpassword=findViewById(R.id.TxtPassword);
        txtPassword1=findViewById(R.id.TxtPassword1);
        signUp=findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("User");
                if (!ValidateUserName() | !ValidateUserphoneNumber()){
                    return;
                }
                String userName=name.getText().toString();
                String phone_number=phnumber.getText().toString();
                UsrPass usrPass = new UsrPass(userName,phone_number);
                reference.child(userName).setValue(usrPass);
                if(userName!=null && phone_number!=null ) {
                    Intent intent = new Intent(Signup.this, VerificationAct.class);
                    intent.putExtra("Name",userName);
                    intent.putExtra("phoneNumber", phone_number);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Signup.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    private Boolean ValidateUserName(){
        String val = name.getText().toString();
        if(val.isEmpty()){
            name.setError("field cannot be empty");
            return false;
        }
        else{
            name.setError(null);
            return true;
        }
    }

    private Boolean ValidateUserphoneNumber(){
        String val = phnumber.getText().toString();
        if(val.isEmpty()){
            phnumber.setError("field cannot be empty");
            return false;
        }
        else if (val.length()>10){
            phnumber.setError("wrong number");
            return false;
        }
        else{
            phnumber.setError(null);
            return true;
        }
    }

}