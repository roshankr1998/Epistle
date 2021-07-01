package com.roshan.hackspace;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class DashboardFragment extends Fragment  {
    ImageView imageView,imageView1,imageView2,imageView3,imageView4,imageView5,im1,im2,im3,im4,im5,im6;
    TextView tx1,tx2,tx3,tx4,tx5,tx6;
    Animation dashanim,dashanim1,dashanim2,dashanim3,dashanim4,dashanim5;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        imageView=root.findViewById(R.id.donimage);
        imageView1=root.findViewById(R.id.abimage);
        imageView2=root.findViewById(R.id.proimage);
        imageView3=root.findViewById(R.id.seimage);
        imageView4=root.findViewById(R.id.uploadpdf);
        imageView5=root.findViewById(R.id.downpdf);
        im1=root.findViewById(R.id.im1);
        im2=root.findViewById(R.id.im2);
        im3=root.findViewById(R.id.im3);
        im4=root.findViewById(R.id.im4);
        im5=root.findViewById(R.id.im5);
        im6=root.findViewById(R.id.im6);
        tx1=root.findViewById(R.id.tx1);
        tx2=root.findViewById(R.id.tx2);
        tx3=root.findViewById(R.id.tx3);
        tx4=root.findViewById(R.id.tx4);
        tx5=root.findViewById(R.id.tx5);
        tx6=root.findViewById(R.id.tx6);
        dashanim= AnimationUtils.loadAnimation(getContext(),R.anim.dash_anim1);
        dashanim1= AnimationUtils.loadAnimation(getContext(),R.anim.dash_anim2);
        dashanim2= AnimationUtils.loadAnimation(getContext(),R.anim.dash_anim3);
        dashanim3= AnimationUtils.loadAnimation(getContext(),R.anim.dash_anim4);
        dashanim4= AnimationUtils.loadAnimation(getContext(),R.anim.dash_anim5);
        dashanim5= AnimationUtils.loadAnimation(getContext(),R.anim.dash_anim6);
        imageView.setAnimation(dashanim);
        imageView1.setAnimation(dashanim4);
        imageView2.setAnimation(dashanim5);
        imageView3.setAnimation(dashanim1);
        imageView4.setAnimation(dashanim2);
        imageView5.setAnimation(dashanim3);
        im1.setAnimation(dashanim);
        im2.setAnimation(dashanim1);
        im3.setAnimation(dashanim2);
        im4.setAnimation(dashanim3);
        im5.setAnimation(dashanim4);
        im6.setAnimation(dashanim5);
        tx1.setAnimation(dashanim);
        tx2.setAnimation(dashanim1);
        tx3.setAnimation(dashanim2);
        tx4.setAnimation(dashanim3);
        tx5.setAnimation(dashanim4);
        tx6.setAnimation(dashanim5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new HomeFragment()).commit();*/
                Intent intent= new Intent(getContext(),donbook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);

            }
        })  ;

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new AboutFragment()).commit();*/
                Intent intent= new Intent(getContext(),about.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);


            }
        })  ;

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new ProfileFragment()).commit();
*/Intent intent= new Intent(getContext(),editprofile.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);
            }
        })  ;

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new SerErFragment()).commit();*/
                Intent intent=new Intent(getContext(),serdonbook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);

            }
        })  ;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new UploadFragment()).commit();*/
                Intent intent=new Intent(getContext(),uploadebook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);


            }
        })  ;
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DownloadFragment()).commit();*/
                Intent intent= new Intent(getContext(),downloadebook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);

            }
        })  ;

    return root;
    }


}