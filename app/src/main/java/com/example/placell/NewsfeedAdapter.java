package com.example.placell;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.NewsfeedViewHolder> {

    Context mContext;
    List<Newsfeeds> mData;
    SharedPreferences pref;
    Dialog dialog;
    DatabaseHelper databaseHelper;

    public NewsfeedAdapter(Context mContext, List<Newsfeeds> mData) {
        this.mContext = mContext;
        this.mData = mData;
        pref = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public NewsfeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.newsfeed_card_layout, parent, false);
        NewsfeedViewHolder newsfeedViewHolder = new NewsfeedViewHolder(v);
        return newsfeedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsfeedViewHolder holder, final int position) {
            holder.heading.setText(mData.get(position).getHeading());
            holder.jobTitle.setText(mData.get(position).getJobTitle());
            holder.date.setText("L.D. : " + mData.get(position).getDate());
            holder.story.setText("Knowledge Stack : " + mData.get(position).getStory());
            holder.type.setText(mData.get(position).getType());
            holder.year.setText(mData.get(position).getYear());

        if(pref.getString("KEY_USER",null).equals("admin")){
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
                                    db.deleteNewsfeed(mData.get(position).getId());
                                    Intent intent = new Intent(mContext, HomeActivity.class);
                                    intent.putExtra("load", 0);
                                    mContext.startActivity(new Intent(intent));
                                    Toast.makeText(mContext,"Newsfeed Deletes!",Toast.LENGTH_SHORT).show();
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
        final Dialog dialog = new Dialog(mContext);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.newsfeed_popup);
                TextView comp = dialog.findViewById(R.id.newsfeedComp);
                TextView jobTitle = dialog.findViewById(R.id.newsfeedJobTitle);
                TextView date = dialog.findViewById(R.id.newsfeedDate);
                TextView year = dialog.findViewById(R.id.newsfeedYear);
                TextView desc = dialog.findViewById(R.id.newsfeedStory);

                comp.setText(holder.heading.getText());
                jobTitle.setText(holder.jobTitle.getText());
                date.setText(mData.get(position).getDate());
                year.setText(holder.year.getText());
                desc.setText(mData.get(position).getDesc());

                TextView txtClose = dialog.findViewById(R.id.txt_close);
                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class NewsfeedViewHolder extends RecyclerView.ViewHolder {
        TextView heading, jobTitle, date, story, type, year;
        View v;

        public NewsfeedViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView.findViewById(R.id.newsfeedLinearLayout);
            heading = itemView.findViewById(R.id.headingNewsfeedTextView);
            jobTitle = itemView.findViewById(R.id.jobTitleNewsfeedTextView);
            date = itemView.findViewById(R.id.dateNewsfeedTextView);
            story = itemView.findViewById(R.id.storyNewsfeedTextView);
            type = itemView.findViewById(R.id.typeNewsfeedTextView);
            year = itemView.findViewById(R.id.yearTextView);
        }
    }
}
