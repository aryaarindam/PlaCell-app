package com.example.placell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OngoingPreviousFragment extends Fragment {
    DatabaseHelper db;
    SharedPreferences pref;
    List<Previouses> previouses;
    PreviousAdapter previousAdapter;
    List<Ongoings> ongoings;
    OngoingAdapter ongoingAdapter;
    RecyclerView rv;
    Button changeView;
    FloatingActionButton fab;
    Cursor c;
    TextView tv;
    Boolean isClicked = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = new DatabaseHelper(getContext());
        View v = inflater.inflate(R.layout.fragment_ongoing_previous, container,false);
        tv = v.findViewById(R.id.ongoingTextView);
        changeView = v.findViewById(R.id.ongoingChangeButton);
        fab = v.findViewById(R.id.ongoingPreviousFloatingActionButton);

        pref = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (!pref.getString("KEY_USER", null).equals("admin")) {
            fab.hide();
        } else {
            fab.show();
        }

        rv = v.findViewById(R.id.ongoinRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        selectedView();
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClicked = !isClicked;
                selectedView();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InsertOngoing.class));
            }
        });
        return v;
    }

    public void selectedView(){
        if (isClicked) {
            tv.setText("On-Going");
            if (pref.getString("KEY_USER", null).equals("admin")) {
                fab.show();
            }
            ongoings = new ArrayList<>();
            c = db.viewOngoing();
            c.moveToLast();
            while (!c.isBeforeFirst()) {
                ongoings.add(new Ongoings(c.getInt(c.getColumnIndex("Imageid")), c.getString(c.getColumnIndex("Company")), c.getString(c.getColumnIndex("Cutoff")), c.getString(c.getColumnIndex("Description")), c.getInt(c.getColumnIndex("Id"))));
                c.moveToPrevious();
            }
            ongoingAdapter = new OngoingAdapter(getContext(), ongoings);
            rv.setAdapter(ongoingAdapter);
        } else {
            tv.setText("Previous");
            fab.hide();
            previouses = new ArrayList<>();
            c = db.viewPrevious();
            c.moveToLast();
            while (!c.isBeforeFirst()) {
                previouses.add(new Previouses(c.getString(c.getColumnIndex("Company")), c.getString(c.getColumnIndex("Cutoff")), c.getInt(c.getColumnIndex("Id"))));
                c.moveToPrevious();
            }
            previousAdapter = new PreviousAdapter(getContext(), previouses);
            rv.setAdapter(previousAdapter);
        }
    }

}
