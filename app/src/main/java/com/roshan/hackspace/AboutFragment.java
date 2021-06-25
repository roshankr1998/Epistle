package com.roshan.hackspace;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutFragment extends Fragment {
TextView textView6,textView8,textView9,textView10;
ImageView imageButton2;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        textView6=root.findViewById(R.id.textView6);
        textView6.setMovementMethod(LinkMovementMethod.getInstance());
        textView8=root.findViewById(R.id.textView8);
        textView8.setMovementMethod(LinkMovementMethod.getInstance());
        textView9=root.findViewById(R.id.textView9);
        textView9.setMovementMethod(LinkMovementMethod.getInstance());
        textView10=root.findViewById(R.id.textView10);
        textView10.setMovementMethod(LinkMovementMethod.getInstance());
        imageButton2=root.findViewById(R.id.imageButton2);
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


}