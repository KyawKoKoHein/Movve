package com.kkkh.movve;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class BookingSuccessActivity extends AppCompatActivity {

    private TextView txtMovie;
    private TextView txtCinema;
    private TextView txtScreen;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtSeats;
    private TextView txtPrice;
    private TextView txtPayment;

    private MaterialButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        txtMovie = findViewById(R.id.txtMovie);
        txtCinema = findViewById(R.id.txtCinema);
        txtScreen = findViewById(R.id.txtScreen);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtSeats = findViewById(R.id.txtSeats);
        txtPrice = findViewById(R.id.txtPrice);
        txtPayment = findViewById(R.id.txtPayment);

        btnHome = findViewById(R.id.btnHome);

        int movieId = getIntent().getIntExtra("movieId",-1);

        String cinemaName = getIntent().getStringExtra("cinemaName");
        String cinemaScreen = getIntent().getStringExtra("cinemaScreen");
        String date = getIntent().getStringExtra("selectedDate");
        String time = getIntent().getStringExtra("selectedTime");
        String payment = getIntent().getStringExtra("paymentMethod");
        int totalPrice = getIntent().getIntExtra("totalPrice",0);

        ArrayList<String> seats =
                getIntent().getStringArrayListExtra("selectedSeats");

        loadMovieTitle(movieId);

        txtCinema.setText("Cinema : " + cinemaName);
        txtScreen.setText("Screen : " + cinemaScreen);
        txtDate.setText("Date : " + date);
        txtTime.setText("Time : " + time);
        txtPrice.setText("Total : MMK " + totalPrice);
        txtPayment.setText("Payment : " + payment);

        if(seats!=null)
            txtSeats.setText("Seats : "+seats.toString());

        // temporary savind booking data start
        SharedPreferences preferences =
                getSharedPreferences("BookingData", MODE_PRIVATE);

        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putInt("movieId", movieId);

        editor.putString("cinemaName", cinemaName);

        editor.putString("selectedCinema", cinemaScreen);

        editor.putString("selectedDate", date);

        editor.putString("selectedTime", time);

        editor.putString("selectedSeats",
                seats == null ? "" :
                        android.text.TextUtils.join(",", seats));

        editor.putInt("totalPrice", totalPrice);

        editor.apply();

        // temporary savind booking data end

        btnHome.setOnClickListener(v->{

            Intent intent =
                    new Intent(
                            BookingSuccessActivity.this,
                            HomeActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();

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
                                "Movie : "+
                                        parser.getAttributeValue(null,"title"));

                        break;
                    }

                }

                event=parser.next();

            }

            parser.close();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}