package com.roshan.hackspace;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class about extends AppCompatActivity {
    TextView textView6,textView8,textView9,textView10;
    ImageView imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textView6=findViewById(R.id.textView6);
        textView6.setMovementMethod(LinkMovementMethod.getInstance());
       // textView8=findViewById(R.id.textView8);
       // textView8.setMovementMethod(LinkMovementMethod.getInstance());
        textView9=findViewById(R.id.textView9);
        textView9.setMovementMethod(LinkMovementMethod.getInstance());
        textView10=findViewById(R.id.textView10);
        textView10.setMovementMethod(LinkMovementMethod.getInstance());
        imageButton2=findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();*/
                Intent intent= new Intent(about.this,home.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(about.this).toBundle();
                startActivity(intent,b);
            }
        });




    }


}