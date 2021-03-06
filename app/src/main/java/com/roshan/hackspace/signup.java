package com.roshan.hackspace;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class signup extends AppCompatActivity {
    EditText password,repassword,name,emailid,username;
    Button  signup,browse;
    ImageView imageView,proimage,gif;
    private FirebaseAuth mAuth;
    int count=0;
    Uri pdfuri=null;
    String filepathname;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String mobile,name1,pass,repass,email;
CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        gif=findViewById(R.id.gif1);
        username= findViewById(R.id.username);

        password=(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.repassword);
        emailid=findViewById(R.id.emailid);
        name=findViewById(R.id.name);
         checkBox=(CheckBox) findViewById(R.id.checkBox);
        signup=(Button) findViewById(R.id.signup);
        browse=(Button) findViewById(R.id.browse);
        proimage=findViewById(R.id.proimage);
    signup.setEnabled(false);
gif.setVisibility(View.INVISIBLE);

                browse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGetContent.launch("image/*");
                    }
                });
       checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(checkBox.isChecked()){
                   if(count==1){
                   signup.setEnabled(true);}
                   else{
                       Toast.makeText(signup.this, "Please select a profile photo", Toast.LENGTH_SHORT).show();
                   }
               }else
               {signup.setEnabled(false);

               }

           }
       });

        imageView=(ImageView) findViewById(R.id.imageView);
        username.setText(String.format("%s", getIntent().getStringExtra("mobile")));

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(signup.this,MainActivity.class);
                Bundle b= ActivityOptions.makeSceneTransitionAnimation(signup.this).toBundle();
                startActivity(intent,b);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile=username.getText().toString().trim();
                pass=password.getText().toString().trim();
                 repass=repassword.getText().toString().trim();
                name1=name.getText().toString().trim();
                email=emailid.getText().toString().trim();


                if(name1.isEmpty()){
                    name.setError("fullname is required");
                    name.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    emailid.setError("Email is required");
                    emailid.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailid.setError("Enter a valid email ");
                    emailid.requestFocus();
                    return;
                }

                if(pass.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                if(repass.isEmpty()){
                    repassword.setError("Password is required");
                    repassword.requestFocus();
                    return;
                }

                if(pass.length()<6){
                    password.setError("Password of 6 characters is required");
                    password.requestFocus();
                    return;

                }
                if(repass.length()<6) {
                    repassword.setError("Password of 6 characters is required");
                    repassword.requestFocus();
                    return;
                }
                if(!pass.equals(repass)){
                    repassword.setError("passwords do not match");
                    repassword.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailid.setError("Enter a valid email ");
                    emailid.requestFocus();
                    return;
                }
                gif.setVisibility(View.VISIBLE);
                signup.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                password.setEnabled(false);
                repassword.setEnabled(false);
                name.setEnabled(false);
                emailid.setEnabled(false);
                username.setEnabled(false);

                    mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            uploadpdf();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            gif.setVisibility(View.INVISIBLE);
                            signup.setVisibility(View.VISIBLE);
                            checkBox.setVisibility(View.VISIBLE);
                            password.setEnabled(true);
                            repassword.setEnabled(true);
                            name.setEnabled(true);
                            emailid.setEnabled(true);
                            username.setEnabled(true);
                        }
                    });




    }});}

    private void uploaduser() {
        User user= new User(name1,mobile,email,pass);
        FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(signup.this, "User registration Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(signup.this,home.class);
                        Bundle b= ActivityOptions.makeSceneTransitionAnimation(signup.this).toBundle();
                        startActivity(intent,b);}
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                gif.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.VISIBLE);
                password.setEnabled(true);
                repassword.setEnabled(true);
                name.setEnabled(true);
                emailid.setEnabled(true);
                username.setEnabled(true);
            }
        });



    }

    private void uploadpdf() {
        long timestamp=System.currentTimeMillis();
        filepathname="uploads/"+timestamp;
        storageReference = FirebaseStorage.getInstance().getReference(filepathname);

        storageReference.putFile(pdfuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(signup.this, "pic upload Successfull ", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                String uploadedpdfUrl=""+uriTask.getResult();
                uploadpdftodb(uploadedpdfUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(signup.this, "Pic upload failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                gif.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.VISIBLE);
                password.setEnabled(true);
                repassword.setEnabled(true);
                name.setEnabled(true);
                emailid.setEnabled(true);
                username.setEnabled(true);
            }
        });

    }

    private void uploadpdftodb(String uploadedpdfUrl) {

        Userprofile user= new Userprofile(uploadedpdfUrl);
        databaseReference=FirebaseDatabase.getInstance().getReference("profile");
        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            uploaduser();
                            Toast.makeText(signup.this, "Image Upload Successfull", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(signup.this, "Image upload Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    ActivityResultLauncher<String> mGetContent= registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result!=null){
                        pdfuri=result;
                        proimage.setImageURI(result);
                        Toast.makeText(signup.this, "Image Selected Succesfully", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(false);
                        count=1;

                    }else{
                        Toast.makeText(signup.this, "Please select a profile photo to proceed", Toast.LENGTH_SHORT).show();
                    }
                }
            });}