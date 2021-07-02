package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class downloadebook extends AppCompatActivity {

    RecyclerView recyclerView1;
    FirebaseDatabase db;
    MyAdapter myAdapter;
    ArrayList<Uploadpdf> list1;
    ProgressDialog progressDialog1;
    ImageView returnimage,gif;
    Spinner spin_sub;
    Button btn_search;
    DatabaseReference dbref;
    ValueEventListener eventListener;
    ArrayList<String> arrayList,newlist1;
    ArrayAdapter<String> arrayAdapter;
    TextView ser_head;
    EditText sear;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadebook);
        returnimage=findViewById(R.id.returnimage);
        sear=findViewById(R.id.search2);
        spin_sub=findViewById(R.id.spinnerr);
        btn_search=findViewById(R.id.select_subj);
        //recyclerView1.setVisibility(View.INVISIBLE);
        // search2.setVisibility(View.INVISIBLE);
        ser_head=findViewById(R.id.ser_head);
        gif=findViewById(R.id.image12);
        gif.setVisibility(View.VISIBLE);
        ser_head.setText("Select Subject");
        dbref=FirebaseDatabase.getInstance().getReference("spinnerdata1");
        sear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter1(s.toString());

            }
        });
        returnimage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               /* FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();*/
                Intent intent= new Intent(downloadebook.this,home.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(downloadebook.this).toBundle();
                startActivity(intent,b);
            }
        });

        arrayList=new ArrayList<String>();
        newlist1=new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<>(downloadebook.this, android.R.layout.simple_spinner_dropdown_item,newlist1);
        spin_sub.setAdapter(arrayAdapter);
        fetchdata();


        progressDialog1 =new ProgressDialog(downloadebook.this);
        progressDialog1.setCancelable(false);
        progressDialog1.setMessage("processing");
        progressDialog1.show();
        recyclerView1=findViewById(R.id.listview1);
        db= FirebaseDatabase.getInstance();
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(downloadebook.this));
        recyclerView1.setVisibility(View.INVISIBLE);
        sear.setVisibility(View.INVISIBLE);
        list1=new ArrayList<>();
        myAdapter=new MyAdapter(downloadebook.this,list1);
        recyclerView1.setAdapter(myAdapter);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=spin_sub.getSelectedItem().toString();
                Toast.makeText(downloadebook.this, state, Toast.LENGTH_SHORT).show();
                btn_search.setVisibility(View.INVISIBLE);
                ser_head.setText("Search your book");
                spin_sub.setVisibility(View.INVISIBLE);
                recyclerView1.setVisibility(View.VISIBLE);
                gif.setVisibility(View.INVISIBLE);
                sear.setVisibility(View.VISIBLE);
                progressDialog1.show();
                feed_Recycler();
            }
        });






    }

    private void feed_Recycler() {
        db.getReference("pdfbook").child(state).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list1.clear();
                if(progressDialog1.isShowing())
                    progressDialog1.dismiss();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Uploadpdf uploadpdf=dataSnapshot.getValue(Uploadpdf.class);
                    list1.add(uploadpdf);
                }
                myAdapter.notifyDataSetChanged();
                if(progressDialog1.isShowing())
                    progressDialog1.dismiss();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void fetchdata() {
        eventListener= dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(progressDialog1.isShowing())
                    progressDialog1.dismiss();
                for(DataSnapshot mydata: snapshot.getChildren()){
                    arrayList.add(mydata.getValue().toString());
                    for(String element:arrayList){
                        if(!newlist1.contains(element)){
                            newlist1.add(element);
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void filter1(String text) {
        ArrayList<Uploadpdf> filterlist1=new ArrayList<>();
        for(Uploadpdf uploadpdf:list1){
            if(uploadpdf.getPdfbookname().toLowerCase().contains(text.toLowerCase())){
                filterlist1.add(uploadpdf);
            }
        }
        myAdapter.filterList1(filterlist1);


    }
}
