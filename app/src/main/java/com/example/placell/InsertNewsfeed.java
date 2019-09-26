package com.example.placell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InsertNewsfeed extends AppCompatActivity {

    private EditText heading,jobtitle,lastdate,subject,type,year,desc;
    private Button submit;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_newsfeed);

        db = new DatabaseHelper(this);
        heading = findViewById(R.id.addNewsfeedHead);
        jobtitle = findViewById(R.id.addNewsfeedJob);
        lastdate = findViewById(R.id.addNewsfeedLastDate);
        subject = findViewById(R.id.addNewsfeedSubject);
        year = findViewById(R.id.addNewsfeedYear);
        type = findViewById(R.id.addNewsfeedType);
        desc = findViewById(R.id.addNewsfeedDesc);

        submit = findViewById(R.id.addNewsfeedSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String head, job, date, sub, yr, typ, des;

                head = heading.getText().toString();
                job = jobtitle.getText().toString();
                date = lastdate.getText().toString();
                sub = subject.getText().toString();
                yr = year.getText().toString();
                typ = type.getText().toString();
                des = desc.getText().toString();

                db.insertNewsfeed(head, job, sub, des, date, typ, yr);
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.putExtra("load", 0);
                startActivity(i);
                finish();
            }
        });

    }
}
