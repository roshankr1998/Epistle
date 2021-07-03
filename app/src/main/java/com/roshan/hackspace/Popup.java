package com.roshan.hackspace;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static androidx.core.content.ContextCompat.getSystemService;

public class Popup {
    String profile;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference post=firebaseDatabase.getReference().child("Users");

    public void showPopupWindow(final View view) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.drop_down, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ImageView pop=popupView.findViewById(R.id.popuppic);
        //Initialize the elements of our window, install the handler

        FirebaseDatabase.getInstance().getReference().child("profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                profile = snapshot.child("url").getValue(String.class);
                Glide.with(popupView.getContext()).load(snapshot.child("url").getValue(String.class)).into(pop);


            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(popupView.getContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});

        TextView number=popupView.findViewById(R.id.number);
        TextView name=popupView.findViewById(R.id.name);
        TextView emailid=popupView.findViewById(R.id.emailid);

        post.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                String post = snapshot.child("fullname").getValue(String.class);
                String post1 = snapshot.child("email").getValue(String.class);
                String post2 = snapshot.child("mobile").getValue(String.class);
                name.setText(post);
                emailid.setText(post1);
                number.setText(post2);

            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                popupWindow.dismiss();


            }
        });

        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //As an example, display the message
                popupWindow.dismiss();

            }
        });



        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked

                return true;
            }
        });
    }

}

