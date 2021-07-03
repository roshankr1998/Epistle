package com.roshan.hackspace;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class BlankFragment extends Fragment {
    String profile;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference post=firebaseDatabase.getReference().child("Users");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView number=root.findViewById(R.id.number);
        TextView name=root.findViewById(R.id.name);
        TextView emailid=root.findViewById(R.id.emailid);
        ImageView pop=root.findViewById(R.id.popuppic);
        //Initialize the elements of our window, install the handler

        FirebaseDatabase.getInstance().getReference().child("profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                profile = snapshot.child("url").getValue(String.class);
                Glide.with(getContext()).load(snapshot.child("url").getValue(String.class)).into(pop);


            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});
        post.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                String post = snapshot.child("fullname").getValue(String.class);
                String post1 = snapshot.child("email").getValue(String.class);
                String post2 = snapshot.child("mobile").getValue(String.class);
                name.setText(post);
                emailid.setText(post1);
                number.setText(post2);

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {



            }
        });

        Button buttonEdit = root.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();

            }
        }); Button buttonEdit1 = root.findViewById(R.id.messageButton1);
        buttonEdit1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), editprofile.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
                startActivity(intent,b);

            }
        });




        return root;
    }}