package com.example.placell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StoryFragment extends Fragment {

    DatabaseHelper db;
    List<Stories> stories;
    StoryAdapter storyAdapter;
    FloatingActionButton fab;
    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = new DatabaseHelper(getContext());
        View v = inflater.inflate(R.layout.fragment_story, container,false);
        fab = v.findViewById(R.id.storyFloatingActionButton);

        pref = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (pref.getString("KEY_USER", null).equals("admin")) {
            fab.hide();
        } else {
            fab.show();
        }

        stories = new ArrayList<>();
        Cursor c = db.viewProfile();
        c.moveToLast();
        while(!c.isBeforeFirst()) {
            stories.add(new Stories(c.getString(c.getColumnIndex("Name")), c.getInt(c.getColumnIndex("Year")), c.getString(c.getColumnIndex("Email")), c.getInt(c.getColumnIndex("Imageid")), c.getString(c.getColumnIndex("Package")), c.getString(c.getColumnIndex("Company")), c.getString(c.getColumnIndex("Story")), c.getInt(c.getColumnIndex("Pid"))));
            c.moveToPrevious();
        }
        storyAdapter = new StoryAdapter(getContext(), stories);
        RecyclerView rv = v.findViewById(R.id.storyRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(storyAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InsertStory.class));
            }
        });
        return v;
    }
}
