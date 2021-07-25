package com.roshan.hackspace;


import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class mydonations extends AppCompatActivity {
    RecyclerView recyclerView2;
    FirebaseDatabase db;
    Mybookadapter mybookadapter;
    ArrayList<Mybooks> booklist;
    ProgressDialog pro5;
    ImageView returnimage;
    DatabaseReference dbref;
    ValueEventListener eventListener;
    ArrayList<String> arrayList,newlist1;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydonations);
        returnimage=findViewById(R.id.imageback);
        dbref=FirebaseDatabase.getInstance().getReference("mybooks");
        returnimage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mydonations.this,home.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(mydonations.this).toBundle();
                startActivity(intent,b);

            }
        });
        pro5 =new ProgressDialog(mydonations.this);
        pro5.setCancelable(false);
        pro5.setMessage("processing");
        pro5.show();
        recyclerView2=findViewById(R.id.listview2);
        db= FirebaseDatabase.getInstance();
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(mydonations.this));
        recyclerView2.setVisibility(View.INVISIBLE);
        booklist=new ArrayList<>();
        mybookadapter=new Mybookadapter(mydonations.this,booklist);
        recyclerView2.setAdapter(mybookadapter);
        recyclerView2.setVisibility(View.VISIBLE);
        feedrecycle();
    }

    private void feedrecycle() {
        db.getReference("mybooks").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                booklist.clear();
                if(pro5.isShowing())
                    pro5.dismiss();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Mybooks mybooks=dataSnapshot.getValue(Mybooks.class);
                    booklist.add(mybooks);
                }
                mybookadapter.notifyDataSetChanged();
                if(pro5.isShowing())
                    pro5.dismiss();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
