package com.roshan.hackspace;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView imageView4,user_image;
    int counter=0;
    Fragment fragment=null;
    FirebaseAuth auth;
    FirebaseUser user;
    String n,e,p,no;
    String profile;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference post=firebaseDatabase.getReference().child("Users");
    DatabaseReference post1=firebaseDatabase.getReference().child("profile");
    TextView userdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        n=getIntent().getStringExtra("name");
        e=getIntent().getStringExtra("email");
        p=getIntent().getStringExtra("pass");
        no=getIntent().getStringExtra("mobile");
        userdata=findViewById(R.id.userdata);
        user_image=findViewById(R.id.user_image);

        post.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String username1 = snapshot.child("fullname").getValue(String.class);
                userdata.setText(username1);
            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});

        post1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                profile = snapshot.child("url").getValue(String.class);
                Glide.with(getApplicationContext()).load(snapshot.child("url").getValue(String.class)).into(user_image);


            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});
        user_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),editprofile.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);
            }
        });

        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        updateusernav();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new DashboardFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {


                int id=item.getItemId();

                switch(id){
                    case R.id.nav_search:
                        /*fragment=new SerErFragment();
                        loadFragment(fragment);
                       //toolbar.setTitle("Search and get book");*/
                        Intent intent1=new Intent(getApplicationContext(),serdonbook.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_home:
                        Intent intent=new Intent(getApplicationContext(),donbook.class);
                        startActivity(intent);
                      //  toolbar.setTitle("Donate Book");
                        break;

                    case R.id.nav_dash:
                        fragment=new DashboardFragment();
                        loadFragment(fragment);
                      //  toolbar.setTitle("Dashboard");
                        break;


                    case R.id.nav_profile:
                        /*fragment=new ProfileFragment();
                        loadFragment(fragment);
                      //  toolbar.setTitle("Update Profile");*/
                        Intent intent6= new Intent(getApplicationContext(),editprofile.class);
                        startActivity(intent6);
                        break;


                    case R.id.nav_aboutus:
                       /* fragment=new AboutFragment();
                        loadFragment(fragment);
                       // toolbar.setTitle("About Us");*/
                        Intent intent5= new Intent(getApplicationContext(),about.class);
                        startActivity(intent5);
                        break;

                    case R.id.nav_upload:
                        /*fragment=new UploadFragment();
                        loadFragment(fragment);
                      // toolbar.setTitle("Upload PDF");*/
                        Intent intent3= new Intent(getApplicationContext(),uploadebook.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_download:
                        /*fragment=new DownloadFragment();
                        loadFragment(fragment);
                       // toolbar.setTitle("Download PDF");*/
                        Intent intent4= new Intent(getApplicationContext(),downloadebook.class);
                        startActivity(intent4);
                        break;



                    case R.id.nav_logout:
                        logout(this);
                        break;


                    case R.id.nav_exit:
                        exit(this);
                        break;

                    default:
                        return true;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }



    private void exit(NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener) {

        exit(this);
    }

    private void exit(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
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


    private void logout(NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener) {

        log(this);
    }

    private void log(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(home.this,login.class);
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



    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);

    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }else{
        counter++;
        if(counter==1)
            Toast.makeText(this, "Press once more to exit", Toast.LENGTH_SHORT).show();
        if(counter==2)
            super.onBackPressed();
    }}

    public void updateusernav(){
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View headerview=navigationView.getHeaderView(0);
        TextView email=headerview.findViewById(R.id.email);
        TextView userdata=headerview.findViewById(R.id.userdata);
        TextView name=headerview.findViewById(R.id.name);
        ImageView pro_image=headerview.findViewById(R.id.user_image);
        email.setText(user.getEmail());
        post.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String username = snapshot.child("fullname").getValue(String.class);
                name.setText(username);
            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});
        post1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                 profile = snapshot.child("url").getValue(String.class);
                Glide.with(getApplicationContext()).load(snapshot.child("url").getValue(String.class)).into(pro_image);


            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});

    }




}