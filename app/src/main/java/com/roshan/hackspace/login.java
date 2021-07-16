package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
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

import static android.text.InputType.TYPE_NULL;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;


public class login extends AppCompatActivity {
    EditText username1,password1;
    Button signin1;
   // DBHelper DB;
    ImageView imageView,gif1;
    TextView forgotpass,newuser;
    int counter=0;
    private FirebaseAuth mAuth;ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(login.this);
        gif1=findViewById(R.id.gif1);
        forgotpass=findViewById(R.id.forgotpass);
        newuser=findViewById(R.id.newuser);
        username1=(EditText) findViewById(R.id.username1);
        password1=(EditText) findViewById(R.id.password1);
        signin1=(Button) findViewById(R.id.signin1);
        //DB =new DBHelper(this);
        imageView=(ImageView) findViewById(R.id.imageView);
        mAuth=FirebaseAuth.getInstance();
        gif1.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(login.this,MainActivity.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle();
                startActivity(intent,b);

            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                progressDialog.setTitle("Hang on...");
                progressDialog.setMessage("Redirecting it to signup page.");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent intent = new Intent(login.this,otp.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle();
                startActivity(intent,b);
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                progressDialog.setTitle("Hang on...");
                progressDialog.setMessage("Redirecting it to forgot password page.");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent intent = new Intent(login.this,forgot.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle();
                startActivity(intent,b);

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
                gif1.setVisibility(View.VISIBLE);
                signin1.setVisibility(View.INVISIBLE);
                forgotpass.setVisibility(View.INVISIBLE);
                newuser.setVisibility(View.INVISIBLE);
               //username1.setEnabled(false);
               username1.clearFocus();
               username1.setInputType(TYPE_NULL);
               //password1.setInputType(TYPE_NULL&TYPE_TEXT_VARIATION_PASSWORD);

               //password1.setEnabled(false);
               password1.clearFocus();
                mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Intent intent=new Intent(login.this,home.class);
                            Bundle b= ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle();
                            startActivity(intent,b);

                        }else{
                            gif1.setVisibility(View.INVISIBLE);
                            signin1.setVisibility(View.VISIBLE);
                            forgotpass.setVisibility(View.VISIBLE);
                            newuser.setVisibility(View.VISIBLE);
                            Toast.makeText(login.this, "Failed to login! Please check yor credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                }





            }
        );

        }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        counter++;
        if(counter==1)
            Toast.makeText(this, "Press once more to exit", Toast.LENGTH_SHORT).show();
        if(counter==2) {
            Intent intent = new Intent(login.this, MainActivity.class);
            Bundle b= ActivityOptions.makeSceneTransitionAnimation(login.this).toBundle();
            startActivity(intent,b);
        }



    }
}