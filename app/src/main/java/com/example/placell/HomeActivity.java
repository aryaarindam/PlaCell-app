package com.example.placell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    DatabaseHelper db;
    DrawerLayout dl;
    TextView name, email;
    ImageView image;
    BottomNavigationView bottomNavigationView;
    NavigationView nv;
    SharedPreferences pref;
    Button edit, logout;

    private int[] img = {R.drawable.boy, R.drawable.girl1, R.drawable.boyspecs, R.drawable.girl2, R.drawable.man, R.drawable.girl, R.drawable.boynormal, R.drawable.girl3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        db = new DatabaseHelper(this);
        nv = findViewById(R.id.nav_view);
        View v = nv.getHeaderView(0);
        pref = getApplicationContext().getSharedPreferences("mypref", MODE_PRIVATE);
        name = v.findViewById(R.id.navHeadName);
        email = v.findViewById(R.id.navHeadEmail);
        image = v.findViewById(R.id.navHeadImage);
        bottomNavigationView = findViewById(R.id.bottomNavBar);

        String ema = pref.getString("KEY_USER", null);

        if (!ema.equals("admin")) {
            name.setText(db.getName(ema));
            email.setText(ema);
            image.setImageResource(img[db.getImagid(ema)]);
        }

        Fragment defaultFrag;

        switch (getIntent().getIntExtra("load", 0)) {
            case 0:
                defaultFrag = new NewsfeedFragment();
                break;
            case 1:
                defaultFrag = new OngoingPreviousFragment();
                break;
            case 2:
                defaultFrag = new StoryFragment();
                break;
            default:
                defaultFrag = new NewsfeedFragment();

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragementContainer, defaultFrag).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_newsfeed:
                        selectedFragment = new NewsfeedFragment();
                        break;
                    case R.id.nav_ongoing:
                        selectedFragment = new OngoingPreviousFragment();
                        break;
                    case R.id.nav_story:
                        selectedFragment = new StoryFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragementContainer, selectedFragment).commit();
                return true;
            }
        });

        dl = findViewById(R.id.drawer_layout);
        edit = findViewById(R.id.edit_profile);
        logout = findViewById(R.id.logout);

        if (ema.equals("admin")) {
            edit.setVisibility(View.GONE);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EditProfile.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = pref.edit();
                edit.clear();
                edit.apply();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

        @Override
        public void onBackPressed() {
            if (dl.isDrawerOpen(GravityCompat.START)) {
                dl.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
}
