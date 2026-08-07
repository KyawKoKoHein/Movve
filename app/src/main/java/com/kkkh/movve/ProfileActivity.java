package com.kkkh.movve;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtPhone;
    private ImageView imgProfile;

    private MaterialButton btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        imgProfile = findViewById(R.id.imgProfile);

        btnEditProfile = findViewById(R.id.btnEditProfile);

        loadProfile();

        btnEditProfile.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            EditProfileActivity.class);

            startActivity(intent);

        });


        // Navigation
        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_profile);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if(item.getItemId()==R.id.nav_home){

                startActivity(new Intent(
                        this,
                        HomeActivity.class));

                finish();

                return true;

            }

            if(item.getItemId()==R.id.nav_ticket){

                startActivity(new Intent(
                        this,
                        BookingHistoryActivity.class));

                finish();

                return true;

            }

            return true;

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfile();
    }

    private void loadProfile() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "UserProfile",
                        MODE_PRIVATE);

        String name =
                preferences.getString(
                        "name",
                        "Admin");

        String phone =
                preferences.getString(
                        "phone",
                        "098765456654");

        txtName.setText(name);
        txtPhone.setText(phone);

        String imageUri =
                preferences.getString(
                        "profileImage",
                        null);

        if (imageUri != null) {

            imgProfile.setImageURI(
                    Uri.parse(imageUri));

        } else {

            imgProfile.setImageResource(
                    R.drawable.profileupdate);

        }
    }

}