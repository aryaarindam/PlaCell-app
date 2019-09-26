package com.example.placell;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper db;
    TextInputEditText name,email,pass,yop;
    Button submit;
    LinearLayout linearlayout;
    StringBuffer index = new StringBuffer("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DatabaseHelper(this);
	    name = findViewById(R.id.signupName);
        email = findViewById(R.id.signupEmail);
        pass = findViewById(R.id.signupPassword);
        yop = findViewById(R.id.signupYear);
        submit = findViewById(R.id.singupSubmit);
        linearlayout = findViewById(R.id.imageLinearLayout);

        selectedImage(linearlayout);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index.length() > 1) {
                    Toast.makeText(SignUpActivity.this, "Too many images selected!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (index.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Please select an image!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String Name = name.getText().toString().trim();
                    String Email = email.getText().toString().trim();
                    String Pass = pass.getText().toString().trim();
                    String Year = yop.getText().toString().trim();
                    db.insertProfile(Name, Pass, Integer.parseInt(Year), Email, Integer.parseInt(index.toString()), null, null, null, 0);
                    Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void selectedImage(LinearLayout linearLayout) {
        for(int i = 0; i < linearLayout.getChildCount(); i++) {
            final ImageView img = (ImageView) linearLayout.getChildAt(i);
            final int a = i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ColorDrawable color = (ColorDrawable) img.getBackground();
                    if (color.getColor() == Color.parseColor("#232F34")) {
                        img.setBackgroundColor(Color.parseColor("#F9AA33"));
                        index.append(a);
                    } else {
                        img.setBackgroundColor(Color.parseColor("#232F34"));
                        index.deleteCharAt(index.indexOf(String.valueOf(a)));
                    }
                }
            });
        }
    }

}