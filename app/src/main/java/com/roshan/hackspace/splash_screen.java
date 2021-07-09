package com.roshan.hackspace;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    private static int Splash=5000;
    Animation topanim,bottomanim;
    ImageView imageView14;
    TextView textView11,textView7;
    ImageView gif1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
    bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
    gif1=findViewById(R.id.gif1);
    imageView14=findViewById(R.id.imageView14);
    textView7=findViewById(R.id.textView7);
    textView11=findViewById(R.id.textView11);
    imageView14.setAnimation(topanim);
    textView7.setAnimation(bottomanim);
    textView11.setAnimation(topanim);
    gif1.setAnimation(bottomanim);

    new Handler().postDelayed(new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            Intent intent=new Intent(splash_screen.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
    },Splash);






    }
}