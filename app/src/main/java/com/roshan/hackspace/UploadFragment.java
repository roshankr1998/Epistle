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
import android.widget.TextView;


public class UploadFragment extends Fragment {
TextView select;
EditText pdfbook,pdfauth,pdfpubl;
ImageButton selectfile;
Button btnupload;
ImageView returnimage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upload, container, false);


        select=root.findViewById(R.id.filename);
        pdfbook=root.findViewById(R.id.file_name);
        pdfauth=root.findViewById(R.id.file_author);
        pdfpubl=root.findViewById(R.id.file_publication);
        selectfile=root.findViewById(R.id.upload_imagebtn);
        btnupload=root.findViewById(R.id.uploadbtn);
        returnimage=root.findViewById(R.id.returnview);

        returnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fr= getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fr.beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DashboardFragment()).commit();
            }
        });

        return root;
    }}