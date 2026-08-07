package com.kkkh.movve;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kkkh.movve.adapter.MovieAdapter;
import com.kkkh.movve.model.Movie;

import java.util.ArrayList;
public class HomeActivity extends AppCompatActivity {

    RecyclerView rvShowing;
    private TextView txtUsername;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtUsername = findViewById(R.id.txtUsername);
        imgProfile = findViewById(R.id.imgProfile);
        loadProfile();

        rvShowing = findViewById(R.id.rvShowing);

        rvShowing.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        ArrayList<Movie> showing = new ArrayList<>();

        showing.add(new Movie(1, R.drawable.moana_poster, "Moana"));
        showing.add(new Movie(2,R.drawable.spider_man_brand_new_day, "Spider-Man: Brand New Day"));
        showing.add(new Movie(3,R.drawable.gold_land_poster, "Gold Land"));
        showing.add(new Movie(4,R.drawable.toy_story_5_poster, "Toy Story"));
        showing.add(new Movie(5,R.drawable.filing_for_love_poster, "Filing For Love"));
        showing.add(new Movie(6,R.drawable.voicemails_for_isabelle_poster, "Isabelle"));
        showing.add(new Movie(7,R.drawable.the_sheep_detectives_poster, "Sheep Detectives"));


        rvShowing.setAdapter(new MovieAdapter(this, showing));


        //Navigation
        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_home);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                return true;

            } else if (id == R.id.nav_ticket) {

                startActivity(new Intent(
                        HomeActivity.this,
                        BookingHistoryActivity.class));

                return true;

            } else if (id == R.id.nav_profile) {

                startActivity(new Intent(
                        HomeActivity.this,
                        ProfileActivity.class));

                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadProfile();
    }

    //Load profile
    private void loadProfile(){

        SharedPreferences preferences =
                getSharedPreferences(
                        "UserProfile",
                        MODE_PRIVATE);

        String name =
                preferences.getString(
                        "name",
                        "Admin");

        txtUsername.setText(name);

        String imageUri =
                preferences.getString(
                        "profileImage",
                        null);

        if(imageUri != null){

            imgProfile.setImageURI(
                    Uri.parse(imageUri));

        }

    }

}