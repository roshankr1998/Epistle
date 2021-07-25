package com.roshan.hackspace;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Mybookadapter extends RecyclerView.Adapter<Mybookadapter.MyViewHolder> {

    Context context;
    ArrayList<Mybooks> booklist;

    public Mybookadapter(Context context, ArrayList<Mybooks> booklist) {
        this.context = context;
        this.booklist= booklist;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.mybooks,parent,false);
        return new Mybookadapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Mybookadapter.MyViewHolder holder, int position) {
        Mybooks mybooks = booklist.get(position);
        holder.bookname.setText(mybooks.getBookname());
        holder.bookauth.setText(mybooks.getAuthor());
        holder.bookpubl.setText(mybooks.getPublication());
        holder.withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                // FirebaseDatabase.getInstance().getReference().child("bookinfo").child(getRe)
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Withdraw Book");
                builder.setMessage("Do you really want to withdraw book?");
                builder.setPositiveButton("Withdraw", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference("mybooks").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(booklist.get(position).getBookname()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(context, "book could not be removed!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        FirebaseDatabase.getInstance().getReference("bookinfo").child(mybooks.getSubject()).child(booklist.get(position).getBookname()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    booklist.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Your book has been withdrawn", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.show();
            }
        });

    }

   /* private void downloadFile(Context context, String pdfbookname, String fileExtension, String directoryDownloads, String url) {
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,directoryDownloads,pdfbookname+fileExtension);

        downloadManager.enqueue(request);
    }*/

    @Override
    public int getItemCount() {
        return booklist.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookname,bookauth,bookpubl;
        ImageView withdraw;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            bookname=itemView.findViewById(R.id.bname);
            bookauth=itemView.findViewById(R.id.bookauth);
            bookpubl=itemView.findViewById(R.id.bookpub);
            withdraw=itemView.findViewById(R.id.withdraw);
    }
}}
