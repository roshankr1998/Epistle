package com.roshan.hackspace;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class DownloadFragment extends Fragment {

    RecyclerView recyclerView1;
    FirebaseDatabase db;
    MyAdapter myAdapter;
    ArrayList<Uploadpdf> list1;
    ProgressDialog progressDialog1;
    ImageView returnimage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_download, container, false);
        returnimage=root.findViewById(R.id.returnimage);
        EditText search2=root.findViewById(R.id.search2);
        search2.addTextChangedListener(new TextWatcher() {
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
            @Override
            public void onClick(View v) {
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();
            }
        });

        progressDialog1 =new ProgressDialog(getContext());
        progressDialog1.setCancelable(false);
        progressDialog1.setMessage("processing");
        progressDialog1.show();
        recyclerView1=root.findViewById(R.id.listview1);
        db= FirebaseDatabase.getInstance();
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        list1=new ArrayList<>();
        myAdapter=new MyAdapter(getContext(),list1);
        recyclerView1.setAdapter(myAdapter);

        db.getReference("pdfbook").addListenerForSingleValueEvent(new ValueEventListener() {
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



        return root;
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