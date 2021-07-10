package com.roshan.hackspace;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class editprofile extends AppCompatActivity {
    EditText number,password,repassword,name,emailid;
    Button signup1,bro;
    TextView textView;
    ImageView imageButton1;
    ProgressBar progress;
    ProgressDialog progressDialog;
    ImageView proup;
    String filepathname;
    StorageReference storageReference;
    String pathname;
    Uri pdfuri=null;
    int counter=0;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference post=firebaseDatabase.getReference().child("Users");
    DatabaseReference post1=firebaseDatabase.getReference().child("profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        progressDialog =new ProgressDialog(editprofile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("processing");
        progressDialog.show();

        number=findViewById(R.id.number);
        imageButton1=findViewById(R.id.imageButton2);
        password=findViewById(R.id.pass);
        repassword=findViewById(R.id.repass);
        emailid=findViewById(R.id.emailid);
        name=findViewById(R.id.name);
        textView=findViewById(R.id.textView);
        signup1=findViewById(R.id.signup1);
        progress=findViewById(R.id.progress);
        proup=findViewById(R.id.pro);
        bro=findViewById(R.id.bro);
        post1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                pathname=snapshot.child("url").getValue(String.class);
                Glide.with(getApplicationContext()).load(snapshot.child("url").getValue(String.class)).into(proup);
            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
            }});
        bro.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                counter++;
                mGetContent.launch("image/*");
            }
        });
       /* proup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mGetContent.launch("image/*");
            }
        });*/
        post.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                String post = snapshot.child("fullname").getValue(String.class);
                String post1 = snapshot.child("email").getValue(String.class);
                String post2 = snapshot.child("mobile").getValue(String.class);
                String post3 = snapshot.child("password").getValue(String.class);
                name.setText(post);
                emailid.setText(post1);
                password.setText(post3);
                repassword.setText(post3);
                number.setText(post2);

            }@Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(editprofile.this, "Failed to load Profile ", Toast.LENGTH_SHORT).show();
                if(progressDialog.isShowing())
                    progressDialog.dismiss();


            }
        });


        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(number.getText().toString().isEmpty()){
                    number.setError("number is required");
                    number.requestFocus();
                    return;
                }
                if(name.getText().toString().isEmpty()){
                    name.setError("FUllname is required");
                    name.requestFocus();
                    return;
                }

                if(emailid.getText().toString().isEmpty()){
                    emailid.setError("Email is required");
                    emailid.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailid.getText().toString()).matches()){
                    emailid.setError("Enter a valid email ");
                    emailid.requestFocus();
                    return;
                }

                if(password.getText().toString().isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                if(repassword.getText().toString().isEmpty()){
                    repassword.setError("Password is required");
                    repassword.requestFocus();
                    return;
                }

                if(password.getText().toString().length()<6){
                    password.setError("Password of 6 characters is required");
                    password.requestFocus();
                    return;

                }
                if(repassword.getText().toString().length()<6) {
                    repassword.setError("Password of 6 characters is required");
                    repassword.requestFocus();
                    return;
                }
                if(!password.getText().toString().equals(repassword.getText().toString())){
                    repassword.setError("passwords do not match");
                    repassword.requestFocus();
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                signup1.setVisibility(View.INVISIBLE);
                User user= new User(name.getText().toString(),number.getText().toString(),emailid.getText().toString(),password.getText().toString());
                FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    if(counter==1)uploadpdf();
                                    else{
                                        uploadpdftodb(pathname);
                                    }

                                }else
                                {
                                    Toast.makeText(editprofile.this, "User Updation Failed", Toast.LENGTH_SHORT).show();
                                    progress.setVisibility(View.GONE);
                                    signup1.setVisibility(View.VISIBLE);
                                    Intent intent=new Intent(editprofile.this,home.class);
                                    Bundle b= ActivityOptions.makeSceneTransitionAnimation(editprofile.this).toBundle();
                                    startActivity(intent,b);
                                }}});






























             /*  Boolean getuser = Db.getuser(number.getText().toString());
                if(getuser==true){
                    number.setEnabled(false);

                    password.setVisibility(View.VISIBLE);
                    repassword.setVisibility(View.VISIBLE);
                    emailid.setVisibility(View.VISIBLE);
                    name.setVisibility(View.VISIBLE);
                    //spinsem.setVisibility(View.VISIBLE);
                   // spinyear.setVisibility(View.VISIBLE);
                    signup1.setVisibility(View.VISIBLE);
                    signup.setVisibility(View.INVISIBLE);
                    textView.setText("Update Profile");
                    Toast.makeText(getActivity(), "You can now update your Account", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "Account is not linked with this phone number", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
        /*signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = number.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String name1 = name.getText().toString();
                String email = emailid.getText().toString();
               // String sem = spinsem.getText().toString();
               // String yearofpassing = spinyear.getText().toString();


              if (user.equals("") || pass.equals("") || repass.equals("") || name1.equals("") || email.equals("") || sem.equals("") || yearofpassing.equals(""))
                    Toast.makeText(getContext(), "please enter all the feilds", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(repass)) {
                        Toast.makeText(getContext(), "passwords match", Toast.LENGTH_SHORT).show();
                        Boolean update=Db.update(user,pass,name1,email,yearofpassing,sem);
                        if(update==true){
                            Toast.makeText(getContext(),"Updated successfully",Toast.LENGTH_SHORT).show();}
                        else{Toast.makeText(getContext(),"Not Updated successfully",Toast.LENGTH_SHORT).show();}
                    } else {
                        Toast.makeText(getContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    }


                }
            }

        });*/
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();*/
                Intent intent=new Intent(editprofile.this,home.class);
                startActivity(intent);


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
                Toast.makeText(editprofile.this, "pic upload Successfull ", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                String uploadedpdfUrl=""+uriTask.getResult();
                uploadpdftodb(uploadedpdfUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(editprofile.this, "Pdf upload failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void uploadpdftodb(String uploadedpdfUrl) {

        Userprofile user= new Userprofile(uploadedpdfUrl);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("profile");
        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            updateuser();
                            Toast.makeText(editprofile.this, "Image Upload Successfull", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(editprofile.this, "Image upload Failed", Toast.LENGTH_SHORT).show();

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
                        proup.setImageURI(result);
                        Toast.makeText(editprofile.this, "Image Selected Succesfully", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(editprofile.this, "Image selection Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            });

private void updateuser(){
    Toast.makeText(editprofile.this, "User profile update Successfull", Toast.LENGTH_SHORT).show();
    progress.setVisibility(View.GONE);
    signup1.setVisibility(View.VISIBLE);
    Intent intent=new Intent(editprofile.this,home.class);
    startActivity(intent);
}
}