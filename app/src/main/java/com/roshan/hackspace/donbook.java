package com.roshan.hackspace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class donbook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donbook);

        Button button6,button2;
        ImageButton imageButton;
        EditText bookname,author,publication,donorname,donoradress,donormob,subname;
        TextView textView;
        ImageView image,imageButton1;
        DatabaseReference mfire,data1;
        ProgressBar progdon;
        Users users;
        FirebaseAuth mAuth1;
        String uid;
        DatabaseReference fire= FirebaseDatabase.getInstance().getReference("bookinfo");
        final String[] subject = new String[1];




            mfire= FirebaseDatabase.getInstance().getReference().child("bookinfo");
            users=new Users();
            mAuth1 = FirebaseAuth.getInstance();
            uid=mAuth1.getUid();
            bookname =findViewById(R.id.bookname);
            author =findViewById(R.id.author);
            publication =findViewById(R.id.publication);
            imageButton=findViewById(R.id.imageButton3);
            imageButton1=findViewById(R.id.imageButton2);
            textView=findViewById(R.id.textView4);
            button2=findViewById(R.id.button2);
            image=findViewById(R.id.imageView2);
            subname=findViewById(R.id.subname);
            donoradress=findViewById(R.id.donoradress);
            donorname=findViewById(R.id.donorname);
            donormob=findViewById(R.id.donormob);
            button6=findViewById(R.id.button6);
            button6.setVisibility(View.INVISIBLE);
            donormob.setVisibility(View.INVISIBLE);
            donorname.setVisibility(View.INVISIBLE);
            donoradress.setVisibility(View.INVISIBLE);
            progdon=findViewById(R.id.progdon);
            bookname.setVisibility(View.INVISIBLE);
            author.setVisibility(View.INVISIBLE);
            textView.setText("Enter Subject Details");
            publication.setVisibility(View.INVISIBLE);
            imageButton.setVisibility(View.INVISIBLE);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subject[0] =subname.getText().toString().trim().toLowerCase().replaceAll(" ","");
                    if(subject[0].isEmpty()){
                        subname.setError("Subject is required");
                        subname.requestFocus();
                        return;
                    }
                    imageButton1.setVisibility(View.INVISIBLE);
                    subname.setVisibility(View.INVISIBLE);
                    bookname.setVisibility(View.VISIBLE);
                    author.setVisibility(View.VISIBLE);
                    publication.setVisibility(View.VISIBLE);
                    imageButton.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Enter Book Details");
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
                                                   textView.setText("Enter Donor Details");
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


                                                           fire.child(subject[0]).child(book).setValue(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                               @Override
                                                               public void onSuccess(Void unused) {
                                                                   Toast.makeText(donbook.this, "book donation successfull", Toast.LENGTH_SHORT).show();

                                                                   bookname.setVisibility(View.INVISIBLE);
                                                                   author.setVisibility(View.INVISIBLE);
                                                                   publication.setVisibility(View.INVISIBLE);
                                                                   imageButton.setVisibility(View.INVISIBLE);
                                                                   textView.setText("Enter Subject Details");
                                                                   button6.setVisibility(View.INVISIBLE);
                                                                   donormob.setVisibility(View.INVISIBLE);
                                                                   donorname.setVisibility(View.INVISIBLE);
                                                                   donoradress.setVisibility(View.INVISIBLE);
                                                                   progdon.setVisibility(View.INVISIBLE);
                                                                   image.setVisibility(View.VISIBLE);
                                                                   button2.setVisibility(View.VISIBLE);
                                                                   subname.setVisibility(View.VISIBLE);
                                                                   bookname.setText(null);
                                                                   author.setText(null);
                                                                   publication.setText(null);
                                                                   donoradress.setText(null);
                                                                   donorname.setText(null);
                                                                   donormob.setText(null);
                                                                   subname.setText(null);
                                                                   imageButton1.setVisibility(View.VISIBLE);

                                                               }
                                                           }).addOnFailureListener(new OnFailureListener() {
                                                               @Override
                                                               public void onFailure(@NonNull @NotNull Exception e) {

                                                                   String excep = e.getMessage();
                                                                   Toast.makeText(donbook.this, excep, Toast.LENGTH_SHORT).show();
                                                                   bookname.setVisibility(View.INVISIBLE);
                                                                   author.setVisibility(View.INVISIBLE);
                                                                   publication.setVisibility(View.INVISIBLE);
                                                                   imageButton.setVisibility(View.INVISIBLE);
                                                                   button6.setVisibility(View.INVISIBLE);
                                                                   donormob.setVisibility(View.INVISIBLE);
                                                                   donorname.setVisibility(View.INVISIBLE);
                                                                   donoradress.setVisibility(View.INVISIBLE);
                                                                   subname.setVisibility(View.VISIBLE);
                                                                   image.setVisibility(View.VISIBLE);
                                                                   button2.setVisibility(View.VISIBLE);
                                                                   imageButton1.setVisibility(View.VISIBLE);
                                                                   bookname.setText(null);
                                                                   author.setText(null);
                                                                   publication.setText(null);
                                                                   donoradress.setText(null);
                                                                   donorname.setText(null);
                                                                   donormob.setText(null);
                                                                   subname.setText(null);
                                                                   imageButton1.setVisibility(View.VISIBLE);
                                                                   textView.setText("Enter Subject Name");
                                                               }
                                                           });


                                                       } });



                                               }
                                           }


            );

            imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void onClick(View v) {
                   /* FragmentManager fr= getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fr.beginTransaction();
                    fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();*/
                    Intent intent= new Intent(donbook.this,home.class);
                    Bundle b= ActivityOptions.makeSceneTransitionAnimation(donbook.this).toBundle();
                    startActivity(intent,b);


                }
            });


    }
}