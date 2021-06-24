package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class login extends AppCompatActivity {
    EditText username1,password1;
    Button signin1;
   // DBHelper DB;
    ImageView imageView;
    TextView forgotpass,newuser;
    int counter=0;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgotpass=findViewById(R.id.forgotpass);
        newuser=findViewById(R.id.newuser);
        username1=(EditText) findViewById(R.id.username1);
        password1=(EditText) findViewById(R.id.password1);
        signin1=(Button) findViewById(R.id.signin1);
        //DB =new DBHelper(this);
        imageView=(ImageView) findViewById(R.id.imageView);
        mAuth=FirebaseAuth.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(login.this,MainActivity.class);
                startActivity(intent);
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,otp.class);
                startActivity(intent);
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,forgot.class);
                startActivity(intent);

            }
        });

        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username1.getText().toString().trim();
                String pass=password1.getText().toString().trim();



                if(user.isEmpty()){
                    username1.setError("Please enter your email");
                    username1.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    username1.setError("Enter a valid email ");
                    username1.requestFocus();
                    return;
                }
                if(pass.isEmpty()) {
                    password1.setError("Password is required");
                    password1.requestFocus();
                    return;
                }
                if(pass.length()<6){
                    password1.setError("Password of 6 characters is required");
                    password1.requestFocus();
                    return;

                }
                progressDialog2 =new ProgressDialog(login.this);
                progressDialog2.setCancelable(false);
                progressDialog2.setMessage("Logging in");
                progressDialog2.show();
                mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(progressDialog2.isShowing())
                                progressDialog2.dismiss();
                            Intent intent=new Intent(login.this,home.class);
                            startActivity(intent);
                        }else{
                            if(progressDialog2.isShowing())
                                progressDialog2.dismiss();
                            Toast.makeText(login.this, "Failed to login! Please check yor credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                }





            }
        );

        }
    @Override
    public void onBackPressed() {
        counter++;
        if(counter==1)
            Toast.makeText(this, "Press once more to exit", Toast.LENGTH_SHORT).show();
        if(counter==2) {
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
        }



    }
}