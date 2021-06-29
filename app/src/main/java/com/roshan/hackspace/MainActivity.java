package com.roshan.hackspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button loginbutton;
    Button loginbutton1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbutton= findViewById(R.id.lgnbtn);
        loginbutton1= findViewById(R.id.lgnbtn1);





        loginbutton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               Intent intent= new Intent(MainActivity.this,login.class);
                                               startActivity(intent);
                                           }
                                       }
        );
        loginbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,otp.class);
                startActivity(intent);
            }
        });
    }
}