package com.example.placell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    StringBuffer index = new StringBuffer("");
    SharedPreferences pref;
    DatabaseHelper db;
    EditText name, password, cnfPassword;
    int imageId;
    LinearLayout linearLayout;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        linearLayout = findViewById(R.id.imgEditLinearLayout);
        selectedImage(linearLayout);

        db = new DatabaseHelper(this);
        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        submit = findViewById(R.id.editSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index.length() > 1) {
                    Toast.makeText(EditProfile.this, "Too many images selected!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (index.length() == 0) {
                    Toast.makeText(EditProfile.this, "Please select an image!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    name = findViewById(R.id.nameEditText);
                    password = findViewById(R.id.passEditText);
                    cnfPassword = findViewById(R.id.cnfPassEditText);
                    if (name.getText().toString().trim().equals("") || password.getText().toString().trim().equals("") || cnfPassword.getText().toString().trim().equals("")) {
                        Toast.makeText(EditProfile.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!password.getText().toString().trim().equals(cnfPassword.getText().toString().trim())) {
                        Toast.makeText(EditProfile.this, "Confirm password does not match!", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        cnfPassword.setText("");
                    } else {
                        db.updateProfile(pref.getString("KEY_USER", null), name.getText().toString().trim(), password.getText().toString().trim(), Integer.parseInt(index.toString()));
                        Toast.makeText(EditProfile.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
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
