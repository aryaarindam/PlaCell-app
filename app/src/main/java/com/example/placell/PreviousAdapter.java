package com.example.placell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.PreviousViewHolder> {

    Context mContext;
    List<Previouses> mData;
    SharedPreferences pref;

    public PreviousAdapter(Context mContext, List<Previouses> mData) {
        this.mContext = mContext;
        this.mData = mData;
        pref = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public PreviousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.previous_card_layout, parent, false);
        PreviousViewHolder previousViewHolder = new PreviousViewHolder(v);
        return previousViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PreviousViewHolder holder, final int position) {
        holder.company.setText(mData.get(position).getCompany());
        holder.avgPackage.setText("Cutoff CGPA : " + mData.get(position).getAvgPackage());

        if (pref.getString("KEY_USER", null).equals("admin")) {
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
                                    db.deletePrevious(mData.get(position).getId());
                                    Intent intent = new Intent(mContext, HomeActivity.class);
                                    intent.putExtra("load", 1);
                                    mContext.startActivity(new Intent(intent));
                                    Toast.makeText(mContext, "You Deleted " + mData.get(position).getCompany() + " from previous.", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class PreviousViewHolder extends RecyclerView.ViewHolder {
        TextView company, avgPackage;
        View v;

        public PreviousViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView.findViewById(R.id.previousLinearLayout);
            company = itemView.findViewById(R.id.companyPreviousTextView);
            avgPackage = itemView.findViewById(R.id.avgPackageTextView);
        }
    }
}
