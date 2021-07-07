package com.roshan.hackspace;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DashboardFragment extends Fragment  {
    Button imageView,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference post=firebaseDatabase.getReference().child("Users");
    DatabaseReference post1=firebaseDatabase.getReference().child("profile");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        imageView=root.findViewById(R.id.btndon);
        imageView1=root.findViewById(R.id.btnser);
        imageView2=root.findViewById(R.id.btnupload);
        imageView3=root.findViewById(R.id.btndown);
        imageView4=root.findViewById(R.id.nearbtn);
        imageView5=root.findViewById(R.id.btn_profile);
        imageView6=root.findViewById(R.id.btnsupport);
        imageView7=root.findViewById(R.id.btnlog);



        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),donbook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);

            }
        })  ;

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent= new Intent(getContext(),serdonbook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);


            }
        })  ;

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

Intent intent= new Intent(getContext(),uploadebook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);
            }
        })  ;

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),downloadebook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);

            }
        })  ;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),map.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);


            }
        })  ;
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent= new Intent(getContext(),editprofile.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);

            }
        })  ;
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent= new Intent(getContext(),about.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation((Activity) getContext()).toBundle();
                startActivity(intent,b);

            }
        })  ;
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent= new Intent(getContext(),login.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    return root;
    }



}