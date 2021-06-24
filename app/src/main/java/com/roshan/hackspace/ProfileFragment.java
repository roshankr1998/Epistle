package com.roshan.hackspace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ProfileFragment extends Fragment {
    EditText number,password,repassword,name,emailid;
    Button signup1;
    TextView textView;
    ImageButton imageButton1;
    ProgressBar progress;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference post=firebaseDatabase.getReference().child("Users");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root =inflater.inflate(R.layout.fragment_profile,container,false);
        progressDialog =new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("processing");
        progressDialog.show();

        number=root.findViewById(R.id.number);
        imageButton1=root.findViewById(R.id.imageButton2);
        password=root.findViewById(R.id.pass);
        repassword=root.findViewById(R.id.repass);
        emailid=root.findViewById(R.id.emailid);
        name=root.findViewById(R.id.name);
        textView=root.findViewById(R.id.textView);
        signup1=root.findViewById(R.id.signup1);
        progress=root.findViewById(R.id.progress);
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
                Toast.makeText(getContext(), "Failed to load Profile ", Toast.LENGTH_SHORT).show();
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
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "User profile update Successfull", Toast.LENGTH_SHORT).show();
                                    progress.setVisibility(View.GONE);
                                    signup1.setVisibility(View.VISIBLE);
                                }else
                                {
                                    Toast.makeText(getContext(), "User Updation Failed", Toast.LENGTH_SHORT).show();
                                    progress.setVisibility(View.GONE);
                                    signup1.setVisibility(View.VISIBLE);
                                    FragmentManager fr= getParentFragmentManager();
                                    FragmentTransaction fragmentTransaction = fr.beginTransaction();
                                    fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();
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
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();


            }
        });
        return root;
    }
}