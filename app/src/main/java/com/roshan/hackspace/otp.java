package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {
    ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ImageView imageView=findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(otp.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final EditText password1= findViewById(R.id.password1);
        Button signin1=findViewById(R.id.signin1);




        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password1.getText().toString().isEmpty())
                    Toast.makeText(otp.this,"Enter mobile number",Toast.LENGTH_SHORT).show();

                else if(password1.getText().toString().length()<10)
                    Toast.makeText(otp.this,"Enter valid mobile number",Toast.LENGTH_SHORT).show();
                else
                {
                    progressDialog1 =new ProgressDialog(otp.this);
                    progressDialog1.setCancelable(false);
                    progressDialog1.setMessage("Sending One Time Password");
                    progressDialog1.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+password1.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            otp.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential) {
                                    if(progressDialog1.isShowing())
                                        progressDialog1.dismiss();

                                }

                                @Override
                                public void onVerificationFailed(@NonNull  FirebaseException e) {
                                    if(progressDialog1.isShowing())
                                        progressDialog1.dismiss();
                                    Toast.makeText(otp.this,e.getMessage(),Toast.LENGTH_SHORT).show();



                                }

                                @Override
                                public void onCodeSent(@NonNull String Verid, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    if(progressDialog1.isShowing())
                                        progressDialog1.dismiss();
                                    Intent intent= new Intent(getApplicationContext(),otpver.class);
                                    intent.putExtra("mobile",password1.getText().toString());
                                    intent.putExtra("Verid",Verid);
                                    startActivity(intent);
                                }
                            }
                    );




                }


            }
        });
    }
}