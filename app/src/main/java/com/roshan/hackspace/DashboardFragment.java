package com.roshan.hackspace;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;


public class DashboardFragment extends Fragment  {
    ImageView imageView,imageView1,imageView2,imageView3,imageView4,imageView5;


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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new HomeFragment()).commit();*/
                Intent intent= new Intent(getContext(),donbook.class);
                startActivity(intent);

            }
        })  ;

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new AboutFragment()).commit();*/
                Intent intent= new Intent(getContext(),forgot.class);
                startActivity(intent);


            }
        })  ;

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new ProfileFragment()).commit();


            }
        })  ;

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new SerErFragment()).commit();*/
                Intent intent=new Intent(getContext(),serdonbook.class);
                startActivity(intent);


            }
        })  ;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new UploadFragment()).commit();


            }
        })  ;
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DownloadFragment()).commit();


            }
        })  ;

    return root;
    }


}