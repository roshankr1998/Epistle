package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class signup extends AppCompatActivity {
    EditText password,repassword,name,emailid,username;
    Button  signup;
    ImageView imageView;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        username= findViewById(R.id.username);
        username.setEnabled(false);
        password=(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.repassword);
        emailid=findViewById(R.id.emailid);
        name=findViewById(R.id.name);
        CheckBox checkBox=(CheckBox) findViewById(R.id.checkBox);
        signup=(Button) findViewById(R.id.signup);
        signup.setEnabled(false);
       checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(checkBox.isChecked()){
                   signup.setEnabled(true);
               }else
               {signup.setEnabled(false);
                   Toast.makeText(signup.this,"please enable the checkbox",Toast.LENGTH_SHORT).show();
               }

           }
       });

        imageView=(ImageView) findViewById(R.id.imageView);
        username.setText(String.format("%s", getIntent().getStringExtra("mobile")));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(signup.this,MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String repass=repassword.getText().toString().trim();
                String name1=name.getText().toString().trim();
                String email=emailid.getText().toString().trim();


                if(name1.isEmpty()){
                    name.setError("fullname is required");
                    name.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    emailid.setError("Email is required");
                    emailid.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailid.setError("Enter a valid email ");
                    emailid.requestFocus();
                    return;
                }

                if(pass.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                if(repass.isEmpty()){
                    repassword.setError("Password is required");
                    repassword.requestFocus();
                    return;
                }

                if(pass.length()<6){
                    password.setError("Password of 6 characters is required");
                    password.requestFocus();
                    return;

                }
                if(repass.length()<6) {
                    repassword.setError("Password of 6 characters is required");
                    repassword.requestFocus();
                    return;
                }
                if(!pass.equals(repass)){
                    repassword.setError("passwords do not match");
                    repassword.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailid.setError("Enter a valid email ");
                    emailid.requestFocus();
                    return;
                }
                progressDialog3 =new ProgressDialog(signup.this);
                progressDialog3.setCancelable(false);
                progressDialog3.setMessage("Creating a Account");
                progressDialog3.show();
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user= new User(name1,mobile,email,pass);
                                FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(signup.this, "User registration Successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(signup.this,home.class);
                                        startActivity(intent);
                                    }else
                                    {
                                        Toast.makeText(signup.this, "User registration Failed", Toast.LENGTH_SHORT).show();
                                        if(progressDialog3.isShowing())
                                            progressDialog3.dismiss();
                                    }
                                }
                            });
                            }else{
                                Toast.makeText(signup.this, "User registration Failed", Toast.LENGTH_SHORT).show();
                                if(progressDialog3.isShowing())
                                    progressDialog3.dismiss();

                            }
                        }
                    });
        }});



    }
    }