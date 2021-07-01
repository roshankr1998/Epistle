package com.roshan.hackspace;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class uploadebook extends AppCompatActivity {
    TextView select;
    EditText pdfbook,pdfauth,pdfpubl;
    EditText pdf_subject;
    Button selectfile;
    Button btnupload;
    ImageView returnimage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog pro1;
    Uri pdfuri=null;
    String filepathname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadebook);
        pro1=new ProgressDialog(uploadebook.this);
        pro1.setTitle("Please Wait...");
        pro1.setCanceledOnTouchOutside(false);

        pdf_subject=findViewById(R.id.file_sub);
        select = findViewById(R.id.filename);
        pdfbook = findViewById(R.id.file_name);
        pdfauth = findViewById(R.id.file_author);
        pdfpubl = findViewById(R.id.file_publication);
        selectfile = findViewById(R.id.upload_imagebtn);
        btnupload = findViewById(R.id.uploadbtn);
        returnimage = findViewById(R.id.returnview);
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfbook");

        pdfbook.setVisibility(View.INVISIBLE);
        pdfpubl.setVisibility(View.INVISIBLE);
        pdfauth.setVisibility(View.INVISIBLE);
        btnupload.setVisibility(View.INVISIBLE);
        pdf_subject.setVisibility(View.INVISIBLE);

        returnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fr = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame, new DashboardFragment()).commit();*/
                Intent intent= new Intent(uploadebook.this,home.class);
                startActivity(intent);
            }
        });

        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGetContent.launch("application/pdf");


            }
        });


        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pdfbook.getText().toString().isEmpty()) {
                    pdfbook.setError("Bookname is required");
                    pdfbook.requestFocus();
                    return;
                }
                if(pdfauth.getText().toString().isEmpty()) {
                    pdfauth.setError("Bookname is required");
                    pdfauth.requestFocus();
                    return;}
                if(pdfpubl.getText().toString().isEmpty()) {
                    pdfpubl.setError("Bookname is required");
                    pdfpubl.requestFocus();
                    return;}
                if(pdf_subject.getText().toString().isEmpty())
                {
                    pdf_subject.setError("Subject is required");
                    pdf_subject.requestFocus();
                    return;
                }
                uploadpdf();

            }
        });

    }

    private void uploadpdf() {
        long timestamp=System.currentTimeMillis();
        filepathname="uploads/"+timestamp;
        storageReference = FirebaseStorage.getInstance().getReference(filepathname);
        Snackbar.make(uploadebook.this.findViewById(android.R.id.content),
                "Uploading file", Snackbar.LENGTH_SHORT).show();
        pro1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pro1.setMessage("Uploading pdf....");
        pro1.setProgress(0);

        pro1.show();
        storageReference.putFile(pdfuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(uploadebook.this, "Pdf upload Successfull ", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                String uploadedpdfUrl=""+uriTask.getResult();
                uploadpdftodb(uploadedpdfUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(uploadebook.this, "Pdf upload failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                if(pro1.isShowing())
                    pro1.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                int currentprogress= (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pro1.setProgress(currentprogress);
            }
        });

    }

    private void uploadpdftodb(String uploadedpdfUrl) {
        pro1.setMessage("Uploading Details to our database hang on....");
        Uploadpdf user= new Uploadpdf(pdfbook.getText().toString(),pdfauth.getText().toString(),pdfpubl.getText().toString(),uploadedpdfUrl);
        databaseReference.child(pdf_subject.getText().toString().trim().toLowerCase().replaceAll(" ","")).child(pdfbook.getText().toString()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            uploadspinner();
                            if(pro1.isShowing())
                                pro1.dismiss();
                            pdfbook.setVisibility(View.INVISIBLE);
                            pdfpubl.setVisibility(View.INVISIBLE);
                            pdfauth.setVisibility(View.INVISIBLE);
                            btnupload.setVisibility(View.INVISIBLE);
                            pdf_subject.setVisibility(View.INVISIBLE);
                            pdfbook.setText(null);
                            pdfauth.setText(null);
                            pdfpubl.setText(null);
                            select.setText(null);
                            pdf_subject.setText(null);
                            Toast.makeText(uploadebook.this, "Book Upload Successfull", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(uploadebook.this, "Book upload Failed", Toast.LENGTH_SHORT).show();
                            if(pro1.isShowing())
                                pro1.dismiss();
                            pdfbook.setVisibility(View.INVISIBLE);
                            pdfpubl.setVisibility(View.INVISIBLE);
                            pdfauth.setVisibility(View.INVISIBLE);
                            btnupload.setVisibility(View.INVISIBLE);
                            pdf_subject.setVisibility(View.INVISIBLE);
                            pdfbook.setText(null);
                            pdf_subject.setText(null);
                            pdfauth.setText(null);
                            pdfpubl.setText(null);
                            select.setText(null);
                        }
                    }
                });
    }

    private void uploadspinner() {
        FirebaseDatabase.getInstance().getReference("spinnerdata1").push().setValue(pdf_subject.getText().toString().toLowerCase().replaceAll(" ","")).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(uploadebook.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }


    ActivityResultLauncher<String> mGetContent= registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result!=null){
                        pdfuri=result;
                        pdfbook.setVisibility(View.VISIBLE);
                        pdfpubl.setVisibility(View.VISIBLE);
                        pdfauth.setVisibility(View.VISIBLE);
                        btnupload.setVisibility(View.VISIBLE);
                        pdf_subject.setVisibility(View.VISIBLE);

                        btnupload.setEnabled(true);
                        select.setText(result.toString());
                        Snackbar.make(uploadebook.this.findViewById(android.R.id.content),
                                "File Selected Successfully", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(uploadebook.this.findViewById(android.R.id.content),
                                "File Select Cancelled", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

}