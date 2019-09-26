package com.example.placell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextView signUp;
    TextInputEditText username, password;
    Button signIn;
    DatabaseHelper db;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        username = findViewById(R.id.usernameTextInput);
        password = findViewById(R.id.passwordTextInput);
        signIn = findViewById(R.id.signInButton);
        signUp = findViewById(R.id.signUpTextView);


        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);

        SpannableString ss = new SpannableString("New User? Create Account"); //10 24
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        };
        ss.setSpan(clickableSpan, 10, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp.setText(ss);
        signUp.setMovementMethod(LinkMovementMethod.getInstance());

        String check;
        if ((check=pref.getString("KEY_USER", null)) != null ) {
            if(!check.equals("admin")){
                Toast.makeText(this, "Welcome back, " + db.getName(check), Toast.LENGTH_LONG).show();
            }
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                } else if ((db.checkUser().contains(username.getText().toString().trim()) && db.checkPass(username.getText().toString().trim()).equals(password.getText().toString().trim())) || (username.getText().toString().trim().equals("admin") && password.getText().toString().trim().equals("admin"))) {
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("KEY_USER", username.getText().toString().trim());
                    edit.apply();
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}