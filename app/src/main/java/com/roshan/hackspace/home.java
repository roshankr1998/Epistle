package com.roshan.hackspace;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

import static java.security.AccessController.getContext;

public class home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView user_image;
    int counter=0;
    FirebaseAuth auth;
    FirebaseUser user;
    String n,e,p,no;
    String profile,postname,postnum,postemail;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference post=firebaseDatabase.getReference().child("Users");
    DatabaseReference post1=firebaseDatabase.getReference().child("profile");

    Button imageView,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolBar);

        ProgressDialog pro1=new ProgressDialog(home.this);
        pro1.setTitle("Loading..");


        setSupportActionBar(toolbar);
        n=getIntent().getStringExtra("name");
        e=getIntent().getStringExtra("email");
        p=getIntent().getStringExtra("pass");
        no=getIntent().getStringExtra("mobile");
        user_image=findViewById(R.id.user_image);
        imageView=findViewById(R.id.btndon);
        imageView1=findViewById(R.id.btnser);
        imageView2=findViewById(R.id.btnupload);
        imageView3=findViewById(R.id.btndown);
        imageView4=findViewById(R.id.nearbtn);
        imageView5=findViewById(R.id.btn_profile);
        imageView6=findViewById(R.id.btnsupport);
        imageView7=findViewById(R.id.btnlog);

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(home.this,donbook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);

            }
        })  ;

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent= new Intent(home.this,serdonbook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);


            }
        })  ;

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent= new Intent(home.this,uploadebook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);
            }
        })  ;

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent=new Intent(home.this,downloadebook.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);

            }
        })  ;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent=new Intent(home.this,map.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);


            }
        })  ;
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent= new Intent(home.this,editprofile.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);

            }
        })  ;
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {

                Intent intent= new Intent(home.this,about.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
                startActivity(intent,b);

            }
        })  ;
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(home.this);
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
        });


        post.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                postname = snapshot.child("fullname").getValue(String.class);
                postemail = snapshot.child("email").getValue(String.class);
                postnum = snapshot.child("mobile").getValue(String.class);


            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {



            }
        });


        post1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                profile = snapshot.child("url").getValue(String.class);
                Glide.with(getApplicationContext()).load(snapshot.child("url").getValue(String.class)).into(user_image);
                pro1.isShowing();
                pro1.dismiss();




            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(home.this,R.style.BottomSheetDialogTheme);
            View bottomsheet=LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottomshow,(LinearLayout)findViewById(R.id.nav_host_fragment_container));
            bottomsheet.findViewById(R.id.messageButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });
                bottomsheet.findViewById(R.id.messageButton1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(getApplicationContext(),editprofile.class);
                        startActivity(intent);
                    }
                });
                TextView txtname=bottomsheet.findViewById(R.id.name);
                TextView txtnum=bottomsheet.findViewById(R.id.number);
                TextView txtemail=bottomsheet.findViewById(R.id.emailid);
                ImageView img=bottomsheet.findViewById(R.id.popuppic);
                txtname.setText(postname);
                txtnum.setText(postnum);
                txtemail.setText(postemail);
                Glide.with(getApplicationContext()).load(profile).into(img);

            bottomSheetDialog.setContentView(bottomsheet);
            bottomSheetDialog.show();
            }
        });



        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        toggle=new ActionBarDrawerToggle(home.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        updateusernav();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {




                switch(item.getItemId()){
                    case R.id.nav_search:
                        Intent intent1=new Intent(getApplicationContext(),serdonbook.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent1);

                        break;

                    case R.id.nav_home:
                        Intent intent=new Intent(getApplicationContext(),donbook.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                        break;

                    case R.id.nav_dash:
                        break;


                    case R.id.nav_profile:

                        Intent intent6= new Intent(getApplicationContext(),editprofile.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent6);
                        break;


                    case R.id.nav_aboutus:
                        Intent intent5= new Intent(getApplicationContext(),about.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent5);
                        break;



                    case R.id.nav_upload:
                        Intent intent3= new Intent(getApplicationContext(),uploadebook.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent3);
                        break;

                    case R.id.nav_download:
                        Intent intent4= new Intent(getApplicationContext(),downloadebook.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent4);
                        break;
                    case R.id.nav_map:
                        Intent intent9= new Intent(getApplicationContext(),map.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent9);
                        break;

                    case R.id.nav_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        logout(this);
                        break;


                    case R.id.nav_exit:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        exit(this);
                        break;

                    default:
                        return true;

                }
                drawerLayout.closeDrawers();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        counter++;
        if(counter==1)
            Toast.makeText(this, "Press once more to exit", Toast.LENGTH_SHORT).show();
        if(counter==2) {
            Intent intent = new Intent(home.this, MainActivity.class);
            Bundle b= ActivityOptions.makeSceneTransitionAnimation(home.this).toBundle();
            startActivity(intent,b);


    }}

    public void updateusernav(){
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View headerview=navigationView.getHeaderView(0);
        TextView email=headerview.findViewById(R.id.email);
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
                Glide.with(getApplicationContext()).load(profile).into(pro_image);


            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});



    }





}