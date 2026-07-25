package com.kkkh.movve;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kkkh.movve.adapter.CinemaAdapter;
import com.kkkh.movve.model.Cinema;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imgBanner;
    private ImageView imgPoster;

    private TextView txtTitle;
    private TextView txtRating;
    private TextView txtInfo;
    private TextView txtDescription;

    private ImageButton btnBack;

    private Button btnBuy;
    private RecyclerView rvCinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Initialize Views
        imgBanner = findViewById(R.id.imgBanner);
        imgPoster = findViewById(R.id.imgPoster);

        txtTitle = findViewById(R.id.txtTitle);
        txtRating = findViewById(R.id.txtRating);
        txtInfo = findViewById(R.id.txtInfo);
        txtDescription = findViewById(R.id.txtDescription);

        btnBack = findViewById(R.id.btnBack);

        // Back button
        btnBack.setOnClickListener(v -> finish());

        rvCinema = findViewById(R.id.rvCinema);
        rvCinema.setLayoutManager(new LinearLayoutManager(this));

        // Get Movie ID
        int movieId = getIntent().getIntExtra("movieId", -1);

        if (movieId != -1) {
            loadMovie(movieId);
            ArrayList<Cinema> cinemaList = new ArrayList<>();

            cinemaList.add(new Cinema(
                    R.drawable.cinema1,
                    "J Cineplex Junction City",
                    "Yangon",
                    "Dolby Atmos"));

            cinemaList.add(new Cinema(
                    R.drawable.cinema2,
                    "J Cineplex Junction Square",
                    "Yangon",
                    "Screen 2"));

            cinemaList.add(new Cinema(
                    R.drawable.cinema3,
                    "J Cineplex Maw Tin",
                    "Yangon",
                    "IMAX"));

            rvCinema.setAdapter(new CinemaAdapter(this, cinemaList));
        }
    }

    private void loadMovie(int movieId) {

        try {

            XmlResourceParser parser = getResources().getXml(R.xml.movies);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG &&
                        parser.getName().equals("movie")) {

                    int id = Integer.parseInt(
                            parser.getAttributeValue(null, "id"));

                    if (id == movieId) {

                        String title = parser.getAttributeValue(null, "title");
                        String year = parser.getAttributeValue(null, "year");
                        String genre = parser.getAttributeValue(null, "genre");
                        String duration = parser.getAttributeValue(null, "duration");
                        String rating = parser.getAttributeValue(null, "rating");
                        String description = parser.getAttributeValue(null, "description");

                        String posterName = parser.getAttributeValue(null, "poster");

                        int posterId = getResources().getIdentifier(
                                posterName,
                                "drawable",
                                getPackageName()
                        );

                        String bannerName = parser.getAttributeValue(null, "banner");

                        int bannerId = getResources().getIdentifier(
                                bannerName,
                                "drawable",
                                getPackageName()
                        );

                        imgPoster.setImageResource(posterId);
                        imgBanner.setImageResource(bannerId);

                        txtTitle.setText(title);
                        txtRating.setText("★ " + rating + " IMDb");
                        txtInfo.setText(year + " • " + genre + " • " + duration);
                        txtDescription.setText(description);

                        break;
                    }
                }

                eventType = parser.next();
            }

            parser.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}