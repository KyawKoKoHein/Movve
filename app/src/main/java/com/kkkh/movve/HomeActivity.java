package com.kkkh.movve;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kkkh.movve.adapter.MovieAdapter;
import com.kkkh.movve.model.Movie;

import java.util.ArrayList;
public class HomeActivity extends AppCompatActivity {

    RecyclerView rvTrending, rvPopular, rvRecommended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvTrending = findViewById(R.id.rvTrending);
        rvPopular = findViewById(R.id.rvPopular);

        rvTrending.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        rvPopular.setLayoutManager(
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

        ArrayList<Movie> upcoming = new ArrayList<>();

        upcoming.add(new Movie(8,R.drawable.ghostfluencer_poster, "Ghostfluencer"));
        upcoming.add(new Movie(9,R.drawable.my_daughter_is_a_zombie_film_poster, "My Daughter"));
        upcoming.add(new Movie(10,R.drawable.mission_impossible_the_final_reckoning_poster, "Mission Impossible"));
        upcoming.add(new Movie(11,R.drawable.the_rip_poster, "The Rip"));
        upcoming.add(new Movie(12,R.drawable.the_legend_of_kitchen_soldier_poster, "The Legend Of Kitchen Soldier"));
        upcoming.add(new Movie(13,R.drawable.one_battle_after_another, "One Battle After Another"));
        upcoming.add(new Movie(14,R.drawable.the_super_mario_galaxy_movie_poster, "The Super Mario Galaxy Movie"));

        rvTrending.setAdapter(new MovieAdapter(this, showing));
        rvPopular.setAdapter(new MovieAdapter(this, upcoming));


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

}