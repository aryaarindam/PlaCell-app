package com.example.placell;

import android.app.Dialog;
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

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private Context mContext;
    private List<Stories> mData;
    SharedPreferences pref;

    private int[] img = {R.drawable.boy, R.drawable.girl1, R.drawable.boyspecs, R.drawable.girl2, R.drawable.man, R.drawable.girl, R.drawable.boynormal, R.drawable.girl3};

    public StoryAdapter(Context mContext, List<Stories> mData) {
        this.mContext = mContext;
        this.mData = mData;
        pref = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.story_card_layout, parent, false);
        StoryViewHolder storyViewHolder = new StoryViewHolder(v);
        return storyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, final int position) {
        holder.img.setImageResource(img[mData.get(position).getPhoto()]);
        holder.name.setText(mData.get(position).getName());
        holder.company.setText(mData.get(position).getCompany());
        holder.pack.setText("Package : " + mData.get(position).getPack());
        holder.year.setText(String.valueOf("Year : " + mData.get(position).getYear()));

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
                                    db.deleteStory(mData.get(position).getEmail());
                                    Intent intent = new Intent(mContext, HomeActivity.class);
                                    intent.putExtra("load", 2);
                                    mContext.startActivity(new Intent(intent));
                                    Toast.makeText(mContext,"You Deleted " + mData.get(position).getName() + "'s Story",Toast.LENGTH_SHORT).show();
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
                dialog.setContentView(R.layout.story_popup);
                ImageView prof = dialog.findViewById(R.id.storyPopUpImg);
                TextView comp = dialog.findViewById(R.id.storyPopUpComp);
                TextView name = dialog.findViewById(R.id.storyPopUpName);
                TextView pack = dialog.findViewById(R.id.storyPopUpPack);
                TextView year = dialog.findViewById(R.id.storyPopUpYear);
                TextView desc = dialog.findViewById(R.id.storyPopUpDesc);

                prof.setImageResource(img[mData.get(position).getPhoto()]);
                comp.setText(mData.get(position).getCompany());
                name.setText(mData.get(position).getName());
                pack.setText(mData.get(position).getPack());
                year.setText(String.valueOf(mData.get(position).getYear()));
                desc.setText(mData.get(position).getStory());

                TextView txtClose = dialog.findViewById(R.id.txt_close_2);
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
    public int getItemCount() { return mData.size(); }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, company, pack, year;
        View v;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView.findViewById(R.id.storyLinearLayout);
            img = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.nameTextView);
            company = itemView.findViewById(R.id.companyTextView);
            pack = itemView.findViewById(R.id.packageTextView);
            year = itemView.findViewById(R.id.yearTextView);
        }
    }

}
