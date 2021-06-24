package com.roshan.hackspace;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {
    Button button6,button2;
    ImageButton imageButton,imageButton1;
    EditText bookname,author,publication,donorname,donoradress,donormob;
    TextView textView;
    ImageView image;
    DatabaseReference mfire,data1;
    ProgressBar progdon;
    Users users;
    FirebaseAuth mAuth1;
    String uid;
    FirebaseDatabase fire=FirebaseDatabase.getInstance() ;



@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root =inflater.inflate(R.layout.fragment_home,container,false);
        mfire= FirebaseDatabase.getInstance().getReference().child("bookinfo");
        users=new Users();
    mAuth1 = FirebaseAuth.getInstance();
    uid=mAuth1.getUid();
    bookname =root.findViewById(R.id.bookname);
    author =root.findViewById(R.id.author);
    publication =root.findViewById(R.id.publication);
    imageButton=root.findViewById(R.id.imageButton3);
    imageButton1=root.findViewById(R.id.imageButton2);
    textView=root.findViewById(R.id.textView4);
    button2=root.findViewById(R.id.button2);
    image=root.findViewById(R.id.imageView2);
    donoradress=root.findViewById(R.id.donoradress);
    donorname=root.findViewById(R.id.donorname);
    donormob=root.findViewById(R.id.donormob);
    button6=root.findViewById(R.id.button6);
    button6.setVisibility(View.INVISIBLE);
    donormob.setVisibility(View.INVISIBLE);
    donorname.setVisibility(View.INVISIBLE);
    donoradress.setVisibility(View.INVISIBLE);
    progdon=root.findViewById(R.id.progdon);
    bookname.setVisibility(View.INVISIBLE);
    author.setVisibility(View.INVISIBLE);
    publication.setVisibility(View.INVISIBLE);
    imageButton.setVisibility(View.INVISIBLE);
    textView.setVisibility(View.INVISIBLE);
    button2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bookname.setVisibility(View.VISIBLE);
            author.setVisibility(View.VISIBLE);
            publication.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.VISIBLE);
            button2.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            image.setVisibility(View.INVISIBLE);
        }
    });




    imageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

                    String book= bookname.getText().toString().trim().toLowerCase();
                    String auth=author.getText().toString().trim().toLowerCase();
                    String publ=publication.getText().toString().trim().toLowerCase();

            if(book.isEmpty()) {
                bookname.setError("Bookname is required");
                bookname.requestFocus();
                return;
            }
            if(auth.isEmpty()) {
                author.setError("Author is required");
                author.requestFocus();
                return;
            }
            if(publ.isEmpty()) {
                publication.setError("Publication is required");
                publication.requestFocus();
                return;
            }

                    bookname.setVisibility(View.INVISIBLE);
                    author.setVisibility(View.INVISIBLE);
                    publication.setVisibility(View.INVISIBLE);
                    imageButton.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    button6.setVisibility(View.VISIBLE);
                    donormob.setVisibility(View.VISIBLE);
                    donorname.setVisibility(View.VISIBLE);
                    donoradress.setVisibility(View.VISIBLE);
                    button6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String dname = donorname.getText().toString().trim().toLowerCase();
                            String daddress = donoradress.getText().toString().trim().toLowerCase();
                            String dmob = donormob.getText().toString().trim().toLowerCase();

                            if (dname.isEmpty()) {
                                donorname.setError("Donor name is required");
                                donorname.requestFocus();
                                return;
                            }
                            if (daddress.isEmpty()) {
                                donoradress.setError("Donor adress is required");
                                donoradress.requestFocus();
                                return;
                            }
                            if (dmob.isEmpty()) {
                                donormob.setError("Donor mobile number is required");
                                donormob.requestFocus();
                                return;
                            }
                            progdon.setVisibility(View.VISIBLE);
                            button6.setVisibility(View.INVISIBLE);

                            Map<String,String> usermap= new HashMap<>();
                            usermap.put("bookname",book);
                            usermap.put("author",auth);
                            usermap.put("publication",publ);
                            usermap.put("donorname",dname);
                            usermap.put("donoradress",daddress);
                            usermap.put("donormobile",dmob);


                            fire.getReference("bookinfo").child(book).setValue(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "book donation successfull", Toast.LENGTH_SHORT).show();
                                    bookname.setVisibility(View.INVISIBLE);
                                    author.setVisibility(View.INVISIBLE);
                                    publication.setVisibility(View.INVISIBLE);
                                    imageButton.setVisibility(View.INVISIBLE);
                                    textView.setVisibility(View.INVISIBLE);
                                    button6.setVisibility(View.INVISIBLE);
                                    donormob.setVisibility(View.INVISIBLE);
                                    donorname.setVisibility(View.INVISIBLE);
                                    donoradress.setVisibility(View.INVISIBLE);
                                    progdon.setVisibility(View.INVISIBLE);
                                    image.setVisibility(View.VISIBLE);
                                    button2.setVisibility(View.VISIBLE);
                                    bookname.setText(null);
                                    author.setText(null);
                                    publication.setText(null);
                                    donoradress.setText(null);
                                    donorname.setText(null);
                                    donormob.setText(null);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {

                                    String excep = e.getMessage();
                                    Toast.makeText(getActivity(), excep, Toast.LENGTH_SHORT).show();
                                    bookname.setVisibility(View.INVISIBLE);
                                    author.setVisibility(View.INVISIBLE);
                                    publication.setVisibility(View.INVISIBLE);
                                    imageButton.setVisibility(View.INVISIBLE);
                                    textView.setVisibility(View.INVISIBLE);
                                    button6.setVisibility(View.INVISIBLE);
                                    donormob.setVisibility(View.INVISIBLE);
                                    donorname.setVisibility(View.INVISIBLE);
                                    donoradress.setVisibility(View.INVISIBLE);
                                    image.setVisibility(View.VISIBLE);
                                    button2.setVisibility(View.VISIBLE);
                                    bookname.setText(null);
                                    author.setText(null);
                                    publication.setText(null);
                                    donoradress.setText(null);
                                    donorname.setText(null);
                                    donormob.setText(null);

                                }
                            });


                        } });



            }
        }


    );

    imageButton1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fr= getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fr.beginTransaction();
            fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();


        }
    });

    return root;
}}