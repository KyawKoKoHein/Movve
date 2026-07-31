package com.kkkh.movve;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class BookingHistoryActivity extends AppCompatActivity {

    TextView txtMovie;
    TextView txtCinema;
    TextView txtDate;
    TextView txtTime;
    TextView txtSeats;
    TextView txtPrice;
    private CardView cardTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        txtMovie = findViewById(R.id.txtMovie);
        txtCinema = findViewById(R.id.txtCinema);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtSeats = findViewById(R.id.txtSeats);
        txtPrice = findViewById(R.id.txtPrice);
        cardTicket = findViewById(R.id.cardTicket);

        SharedPreferences preferences =
                getSharedPreferences(
                        "BookingData",
                        MODE_PRIVATE);

        int movieId =
                preferences.getInt("movieId",-1);

        String cinema =
                preferences.getString("cinemaName","");

        String selectedCinema =
                preferences.getString("selectedCinema","");

        String date =
                preferences.getString("selectedDate","");

        String time =
                preferences.getString("selectedTime","");

        String seats =
                preferences.getString("selectedSeats","");

        int price =
                preferences.getInt("totalPrice",0);

        if(movieId==-1){

            cardTicket.setVisibility(View.GONE);

            return;

        }

        txtCinema.setText("Cinema : " + cinema + " [ " + selectedCinema + " ]");
        txtDate.setText("Date : " + date);
        txtTime.setText("Time : " + time);
        txtPrice.setText("MMK " + price);

        if(seats != null){

            txtSeats.setText("Seats : " + seats.toString());

        }

        loadMovieTitle(movieId);

        // Navigation
        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_ticket);

        bottomNavigation.setOnItemSelectedListener(item -> {

            if(item.getItemId()==R.id.nav_home){

                startActivity(new Intent(
                        this,
                        HomeActivity.class));

                finish();

                return true;

            }

            if(item.getItemId()==R.id.nav_profile){

                startActivity(new Intent(
                        this,
                        ProfileActivity.class));

                finish();

                return true;

            }

            return true;

        });

    }

    private void loadMovieTitle(int movieId){

        try{

            XmlResourceParser parser =
                    getResources().getXml(R.xml.movies);

            int event = parser.getEventType();

            while(event!= XmlPullParser.END_DOCUMENT){

                if(event==XmlPullParser.START_TAG &&
                        parser.getName().equals("movie")){

                    int id=Integer.parseInt(
                            parser.getAttributeValue(null,"id"));

                    if(id==movieId){

                        txtMovie.setText(
                                parser.getAttributeValue(
                                        null,
                                        "title"));

                        break;

                    }

                }

                event=parser.next();

            }

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}