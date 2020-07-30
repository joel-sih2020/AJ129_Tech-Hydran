package com.example.testmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetpasswordAct extends AppCompatActivity {
EditText txtPassword,txtPassword1;
Button setpassword;
FirebaseDatabase rootNode1;
DatabaseReference reference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        txtPassword=findViewById(R.id.TxtPassword);
        txtPassword1=findViewById(R.id.TxtPassword1);
        setpassword=findViewById(R.id.set);

        setpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=txtPassword.getText().toString();
                String confirmPassword=txtPassword1.getText().toString();
                String UsersName = getIntent().getStringExtra("Uname");
                if(!ValidateUserPassword() | !ValidateUserConfirmPass()){
                    return;
                }
                if(password.equals(confirmPassword)){
                    reference1= FirebaseDatabase.getInstance().getReference("User").child(UsersName).child("password");
                    reference1.setValue(password);
                    //Toast.makeText(SetpasswordAct.this,password,Toast.LENGTH_SHORT).show();
                    Intent login=new Intent(SetpasswordAct.this,LoginAct.class);
                    startActivity(login);
                }
            }
        });

    }
    private Boolean ValidateUserPassword(){
        String val = txtPassword.getText().toString();
        if(val.isEmpty()){
            txtPassword.setError("field cannot be empty");
            return false;
        }
        else{
            txtPassword.setError(null);
            return true;
        }
    }
    private Boolean ValidateUserConfirmPass(){
        String val = txtPassword1.getText().toString();
        if(val.isEmpty()){
            txtPassword1.setError("field cannot be empty");
            return false;
        }
        else{
            txtPassword1.setError(null);
            return true;
        }
    }
}