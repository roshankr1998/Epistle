package com.roshan.hackspace;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Uploadpdf> list1;

    public MyAdapter(Context context, ArrayList<Uploadpdf> list1) {
        this.context = context;
        this.list1 = list1;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.pdfelements,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            Uploadpdf uploadpdf= list1.get(position);
        holder.pdfbookname.setText(uploadpdf.getPdfbookname());
        holder.pdfbookauth.setText(uploadpdf.getPdfauthor());
        holder.pdfbookpubl.setText(uploadpdf.getPdfpublication());
        holder.bookdownload.setText(uploadpdf.getUrl());
        holder.btn_downloadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(holder.pdfbookname.getContext(),list1.get(position).getPdfbookname(),".pdf",DIRECTORY_DOWNLOADS,list1.get(position).getUrl());
            }
        });



    }

    private void downloadFile(Context context, String pdfbookname, String fileExtension, String directoryDownloads, String url) {
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,directoryDownloads,pdfbookname+fileExtension);

        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }


    protected static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pdfbookname,pdfbookauth,pdfbookpubl,bookdownload;
        Button btn_downloadpdf;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            pdfbookname=itemView.findViewById(R.id.pdfbookname);
            pdfbookauth=itemView.findViewById(R.id.pdfbookauth);
            pdfbookpubl=itemView.findViewById(R.id.pdfbookpubl);
            bookdownload=itemView.findViewById(R.id.bookdownload);
            btn_downloadpdf=itemView.findViewById(R.id.btn_downloadpdf);
    }}
    public void filterList1(ArrayList<Uploadpdf> filterlist1){
        list1=filterlist1;
        notifyDataSetChanged();
    }
}
