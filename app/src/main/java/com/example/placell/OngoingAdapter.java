package com.example.placell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.OngoingViewHolder>{
    Context mContext;
    List<Ongoings> mData;
    SharedPreferences pref;

    int[] images = {R.drawable.apple, R.drawable.dell, R.drawable.microsoft, R.drawable.ibm};

    public OngoingAdapter(Context mContext, List<Ongoings> mData) {
        this.mContext = mContext;
        this.mData = mData;
        pref = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public OngoingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.ongoing_card_layout, parent, false);
        OngoingViewHolder ongoingViewHolder = new OngoingViewHolder(v);
        return ongoingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingViewHolder holder, final int position) {
        holder.img.setImageResource(images[mData.get(position).getImg()]);
        holder.company.setText(mData.get(position).getCompany());
        holder.cgpa.setText("Cut-Off CGPA : " + mData.get(position).getCgpa());

        if (pref.getString("KEY_USER", null).equals("admin")){
            holder.v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder abuild = new AlertDialog.Builder(mContext);
                    abuild.setMessage("Do You Want To Delete This Item ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHelper db = new DatabaseHelper(mContext);
                                    db.insertPrevious(mData.get(position).getCompany(),mData.get(position).getCgpa());
                                    db.deleteOngoing(mData.get(position).getId());
                                    Intent intent = new Intent(mContext, HomeActivity.class);
                                    intent.putExtra("load", 1);
                                    mContext.startActivity(new Intent(intent));
                                    Toast.makeText(mContext,"You Deleted "+mData.get(position).getCompany()+" from ongoing.",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(" No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = abuild.create();
                    alert.setTitle("Alert !");
                    alert.show();
                    return true;
                }
            });
        }

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ViewOngoing.class);
                intent.putExtra("img",images[mData.get(position).getImg()]);
                intent.putExtra("comp",mData.get(position).getCompany());
                intent.putExtra("cgpa",mData.get(position).getCgpa());
                intent.putExtra("desc",mData.get(position).getDesc());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class OngoingViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView company, cgpa;
        View v;

        public OngoingViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView.findViewById(R.id.containerOngoing);
            img = itemView.findViewById(R.id.imageView5);
            company = itemView.findViewById(R.id.companyOngoingTextView);
            cgpa = itemView.findViewById(R.id.cgpaOngoingTextView);
        }
    }
}
