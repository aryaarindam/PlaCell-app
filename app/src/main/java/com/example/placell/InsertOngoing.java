package com.example.placell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertOngoing extends AppCompatActivity {

    Spinner company;
    ArrayAdapter<String> aa;
    String[] companyName = {"Select Company","Apple", "Dell", "Microsoft", "IBM"};
    EditText desc,cutoff;
    Button addOngoing;
    int imgid;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_ongoing);

        company = findViewById(R.id.companySpinner);
        desc = findViewById(R.id.descEditText);
        cutoff = findViewById(R.id.cutoffEditText);
        addOngoing = findViewById(R.id.addOngoingButton);
        db = new DatabaseHelper(this);

        aa = new ArrayAdapter<String>(this, R.layout.custom_spinner, companyName);
        company.setAdapter(aa);
        company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItemPosition() != 0)
                    imgid = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String des = desc.getText().toString();
                String cut = cutoff.getText().toString();
                if (imgid == 0){
                    Toast.makeText(InsertOngoing.this, "Please select a company!", Toast.LENGTH_SHORT).show();
                }else if ( des.equals("") || cut.equals("") ){
                    Toast.makeText(InsertOngoing.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }else{
                    db.insertOngoing(imgid-1, companyName[imgid], cut, des);
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    i.putExtra("load", 1);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
