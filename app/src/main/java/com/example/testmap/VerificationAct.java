package com.example.testmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerificationAct extends AppCompatActivity {
EditText otp;
TextView txtOtp;
Button verify;
ProgressBar progressBar;
String verificationbySystem,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verification);
        otp=findViewById(R.id.otp);
        verify=findViewById(R.id.verify);
        progressBar=findViewById(R.id.progressBar);
        txtOtp=findViewById(R.id.txtOpt);
        final String Number = getIntent().getStringExtra("phoneNumber");
        userName=getIntent().getStringExtra("Name");
        Toast.makeText(VerificationAct.this,Number,Toast.LENGTH_SHORT).show();
        SendVerificationCode(Number);
        txtOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendVerificationCode(Number);
            }
        });
    }
    public void SendVerificationCode(String phoneNo){
       PhoneAuthProvider.getInstance().verifyPhoneNumber(

               "+91 "+ phoneNo,
               60,
               TimeUnit.SECONDS,
               TaskExecutors.MAIN_THREAD,
               mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationbySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        progressBar.setVisibility(View.VISIBLE);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(VerificationAct.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            };
    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationbySystem,codeByUser);
        signInTheuserByCredentials(credential);
    }
    private void signInTheuserByCredentials(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerificationAct.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent next = new Intent(VerificationAct.this,SetpasswordAct.class);
                            next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            next.putExtra("Uname",userName);
                            startActivity(next);
                        }
                        else {
                            Toast.makeText(VerificationAct.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}