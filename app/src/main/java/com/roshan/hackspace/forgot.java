package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class forgot extends AppCompatActivity {
    EditText emailres;
    ImageView imageView;
    Button respass;
    FirebaseAuth auth;
    ProgressBar progbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        emailres=findViewById(R.id.emailres);
        respass=findViewById(R.id.respass);
        imageView=findViewById(R.id.imageView9);
        auth=FirebaseAuth.getInstance();
        progbar2=findViewById(R.id.progbar2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgot.this,login.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(forgot.this).toBundle();
                startActivity(intent,b);
            }
        });
        respass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid=emailres.getText().toString().trim();

                if(emailid.isEmpty()){
                    emailres.setError("Email is required");
                    emailres.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
                    emailres.setError("Enter a valid email ");
                    emailres.requestFocus();
                    return;
                }
                progbar2.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(emailid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgot.this, "An email has been sent to your registered emailid", Toast.LENGTH_SHORT).show();
                            progbar2.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(forgot.this, "Something does not look good! Try again later!!!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
}