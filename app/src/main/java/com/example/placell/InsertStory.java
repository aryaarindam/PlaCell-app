package com.example.placell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InsertStory extends AppCompatActivity {

    EditText name, company, pack, story;
    Button submit;
    SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_story_layout);

        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        name = findViewById(R.id.storyName);
        company = findViewById(R.id.storyCompany);
        pack = findViewById(R.id.storyPackage);
        story = findViewById(R.id.story);
        submit = findViewById(R.id.storySubmit);
        final DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Company = company.getText().toString();
                String Pack = pack.getText().toString();
                String Story = story.getText().toString();

                databaseHelper.updateStory(pref.getString("KEY_USER", null),Company,Story,Pack);
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.putExtra("load", 2);
                startActivity(i);
                finish();
            }
        });
    }
}
