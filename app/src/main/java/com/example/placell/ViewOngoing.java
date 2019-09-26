package com.example.placell;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewOngoing extends AppCompatActivity {

    TextView desc,comp,cgpa;
    int[] images = {R.drawable.apple, R.drawable.dell, R.drawable.microsoft, R.drawable.ibm};
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ongoing);

        comp = findViewById(R.id.detailOngoingCompName);
        desc = findViewById(R.id.detailOngoingDesc);
        cgpa = findViewById(R.id.detailOngoingCgpa);
        img = findViewById(R.id.detailOngoingImg);

        comp.setText(getIntent().getStringExtra("comp"));
        desc.setText(getIntent().getStringExtra("desc"));
        cgpa.setText("Cut Off CGPA: "+getIntent().getStringExtra("cgpa"));
        img.setImageResource(getIntent().getIntExtra("img",0));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
