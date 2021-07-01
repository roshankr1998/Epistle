package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpver extends AppCompatActivity {
    private EditText ic1, ic2, ic3, ic4, ic5, ic6;
    private String verid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpver);
        TextView textMob = findViewById(R.id.textMob);
        TextView rotp = findViewById(R.id.rotp);
        final Button button = findViewById(R.id.button);
        final ProgressBar progbar1 = findViewById(R.id.progbar1);


        verid = getIntent().getStringExtra("Verid");

        rotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        otpver.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(otpver.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onCodeSent(@NonNull String NewVerid, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verid = NewVerid;
                                Toast.makeText(otpver.this, "OTP Sent", Toast.LENGTH_SHORT).show();

                            }
                        }
                );


            }


        });

        textMob.setText(String.format("%s", getIntent().getStringExtra("mobile")));

        ic1 = findViewById(R.id.ic1);
        ic2 = findViewById(R.id.ic2);
        ic3 = findViewById(R.id.ic3);
        ic4 = findViewById(R.id.ic4);
        ic5 = findViewById(R.id.ic5);
        ic6 = findViewById(R.id.ic6);

        setUpOtpInputs();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ic1.getText().toString().trim().isEmpty() || ic2.getText().toString().trim().isEmpty() || ic3.getText().toString().trim().isEmpty() || ic4.getText().toString().trim().isEmpty() || ic5.getText().toString().trim().isEmpty() || ic6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(otpver.this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = ic1.getText().toString() + ic2.getText().toString() + ic3.getText().toString() + ic4.getText().toString() + ic5.getText().toString() + ic6.getText().toString();
                if (verid != null) {
                    progbar1.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verid,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progbar1.setVisibility(View.VISIBLE);
                            button.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), signup.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("mobile", textMob.getText().toString());
                                Bundle b= ActivityOptions.makeSceneTransitionAnimation(otpver.this).toBundle();
                                startActivity(intent,b);
                            } else {
                                Toast.makeText(otpver.this, "the code entered is invalid", Toast.LENGTH_SHORT).show();
                                progbar1.setVisibility(View.INVISIBLE);
                                button.setVisibility(View.VISIBLE);

                            }
                        }
                    });
                }

            }
        });


    }

    private void setUpOtpInputs() {
        ic1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ic2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ic2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ic3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ic3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ic4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ic4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ic5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ic5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    ic6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}