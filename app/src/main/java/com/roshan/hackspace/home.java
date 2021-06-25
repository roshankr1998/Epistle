package com.roshan.hackspace;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView imageView4;
    int counter=0;
    Fragment fragment=null;
    FirebaseAuth auth;
    FirebaseUser user;
    String n,e,p,no;



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
                        fragment=new SearchFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.nav_home:
                        fragment=new HomeFragment();
                        loadFragment(fragment);
                        break;

                    case R.id.nav_dash:
                        fragment=new DashboardFragment();
                        loadFragment(fragment);
                        break;


                    case R.id.nav_profile:
                        fragment=new ProfileFragment();
                        loadFragment(fragment);
                        break;


                    case R.id.nav_aboutus:
                        fragment=new AboutFragment();
                        loadFragment(fragment);
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
        counter++;
        if(counter==1)
            Toast.makeText(this, "Press once more to exit", Toast.LENGTH_SHORT).show();
        if(counter==2)
            super.onBackPressed();
    }

    public void updateusernav(){
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View headerview=navigationView.getHeaderView(0);
        TextView email=headerview.findViewById(R.id.email);
        email.setText(user.getEmail());
    }




}