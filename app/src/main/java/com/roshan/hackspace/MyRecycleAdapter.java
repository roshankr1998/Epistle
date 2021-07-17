package com.roshan.hackspace;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>
{

    Context context;
    ArrayList<Hero> list;
    String state1;

    public MyRecycleAdapter(Context context, ArrayList<Hero> list,String state1) {
        this.context = context;
        this.list = list;
        this.state1=state1;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder,final int position) {
        //DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("bookinfo").child(getRef(position).getkey());
        //final String myKeyItem = itemRef.getKey();
        Hero hero= list.get(position);
        holder.txt_listname.setText(hero.getBookname());
        holder.txt_listteam.setText(hero.getAuthor());
        holder.txt_listname1.setText(hero.getPublication());
        holder.txt_listteam1.setText(hero.getDonorname());
        holder.txt_listname2.setText(hero.getDonoradress());
        holder.txt_listteam2.setText(hero.getDonormobile());
       //String key= list.get(position).getKey();
        holder.getbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                // FirebaseDatabase.getInstance().getReference().child("bookinfo").child(getRe)
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Get Book");
                builder.setMessage("Note down the donor details and contact them to get the book");
                builder.setPositiveButton("Get Book", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DatabaseReference dref= FirebaseDatabase.getInstance().getReference("bookinfo");
                        //FirebaseDatabase.getInstance().getReference().child("bookname").child(getRef().getKey());
                        //FirebaseDatabase.getInstance().getReference("bookinfo").child.removeValue();


                        FirebaseDatabase.getInstance().getReference("bookinfo").child(state1).child(list.get(position).getBookname()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "The Book is being Wrapped you may contact the donor", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }
                });
                builder.setNegativeButton("Note details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Note Down Details And Try Again", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_listname2,txt_listname1,txt_listname,txt_listteam,txt_listteam1,txt_listteam2;
        Button getbook;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_listname=itemView.findViewById(R.id.txt_listbname);
            txt_listname1=itemView.findViewById(R.id.txt_listpub);
            txt_listname2=itemView.findViewById(R.id.txt_listname2);
            txt_listteam=itemView.findViewById(R.id.txt_listaname);
            txt_listteam1=itemView.findViewById(R.id.txt_listadress);
            txt_listteam2=itemView.findViewById(R.id.txt_listmob);
            getbook=itemView.findViewById(R.id.getbook);
        }
    }

    public void filterList(ArrayList<Hero> filterlist){
        list=filterlist;
        notifyDataSetChanged();
    }
   }