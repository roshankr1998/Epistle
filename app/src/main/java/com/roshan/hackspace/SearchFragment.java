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
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SearchFragment extends Fragment {


    //ListView listView;
    RecyclerView recyclerView;
    FirebaseFirestore databaseReference;
    MyRecycleAdapter myRecycleAdapter;
    ArrayList<Hero> list;
    ImageButton imageButton2;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ser_er, container, false);
        imageButton2=root.findViewById(R.id.imageButton2);
        EditText search1=root.findViewById(R.id.search1);
        progressDialog =new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("processing");
        progressDialog.show();
        search1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        recyclerView=root.findViewById(R.id.listview);
        //databaseReference= FirebaseDatabase.getInstance().getReference("bookinfo");
        databaseReference= FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<>();
        myRecycleAdapter=new MyRecycleAdapter(getContext(),list);
        recyclerView.setAdapter(myRecycleAdapter);

        databaseReference.collection("bookinfo")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        if(error!=null){
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        list.clear();
                        for(DocumentChange dc: value.getDocumentChanges()){

                            list.add(dc.getDocument().toObject(Hero.class));

                        }
                        myRecycleAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                });




       /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot  dataSnapshot: snapshot.getChildren()){
                   // Object key= dataSnapshot.getid();
                    // myKey = key.toString();


                    Hero hero=dataSnapshot.getValue(Hero.class);
                    list.add(hero);
                }
                myRecycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();


            }
        });



        return root;
    }

    private void filter(String text) {
        ArrayList<Hero> filterlist=new ArrayList<>();
        for(Hero hero:list){
            if(hero.getBookname().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(hero);
            }
        }
        myRecycleAdapter.filterList(filterlist);


    }
}