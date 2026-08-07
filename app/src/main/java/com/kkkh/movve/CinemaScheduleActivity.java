package com.kkkh.movve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.kkkh.movve.adapter.DateAdapter;
import com.kkkh.movve.adapter.ShowtimeAdapter;
import com.kkkh.movve.model.DateItem;
import com.kkkh.movve.model.ShowTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CinemaScheduleActivity extends AppCompatActivity {

    TextView txtCinemaName;

    ImageView imageCinema;

    private ImageButton btnBack;
    RecyclerView rvDate, rvTime1, rvTime2;
    private ShowtimeAdapter adapter1;
    private ShowtimeAdapter adapter2;

    private MaterialButton btnContinue;

    private int movieId;

    private String selectedDate = "";
    private String selectedDay = "";
    private String selectedTime = "";
    private String selectedCinema = "";

    private int ticketPrice = 8000;
    private String selectedCinemaScreen = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_schedule);

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {

            Intent intent = new Intent(
                    CinemaScheduleActivity.this,
                    SeatSelectionActivity.class);

            // Movie
            intent.putExtra("movieId", movieId);

            // Cinema
            intent.putExtra("cinemaName", selectedCinema);

            // Date
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedDay", selectedDay);

            // Showtime
            intent.putExtra("selectedTime", selectedTime);

            // Price
            intent.putExtra("ticketPrice", ticketPrice);

            // Cinema 1 or 2
            intent.putExtra("cinemaScreen", selectedCinemaScreen);

            startActivity(intent);
        });

        btnBack = findViewById(R.id.btnBack);

        // Back button
        btnBack.setOnClickListener(v -> finish());
        txtCinemaName = findViewById(R.id.txtCinemaName);
        imageCinema = findViewById(R.id.cinemaImageInSchedule);

        String cinemaName = getIntent().getStringExtra("cinemaName");
        selectedCinema = cinemaName;

        int cinemaImage = getIntent().getIntExtra("cinemaImage", R.drawable.cinema1);

        movieId = getIntent().getIntExtra("movieId", -1);

        txtCinemaName.setText(cinemaName);
        imageCinema.setImageResource(cinemaImage);

        //For Date
        rvDate = findViewById(R.id.rvDate);

        rvDate.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));


        //Getting actual dates
        ArrayList<DateItem> dates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat fullDateFormat =new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        for (int i = 0; i < 3; i++) {

            String date = dateFormat.format(calendar.getTime());
            String day = dayFormat.format(calendar.getTime());
            String fullDate = fullDateFormat.format(calendar.getTime());

            dates.add(new DateItem(date, day,fullDate, i == 0));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        rvDate.setAdapter(
                new DateAdapter(
                        this,
                        dates,
                        (date, day) -> {

                            selectedDate = date;
                            selectedDay = day;

                        }
                )
        );


        //For Time
        rvTime1 = findViewById(R.id.rvTime1);
        rvTime2 = findViewById(R.id.rvTime2);

        rvTime1.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        rvTime2.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        ArrayList<ShowTime> cinema1 = new ArrayList<>();
        cinema1.add(new ShowTime("10:30 AM", false));
        cinema1.add(new ShowTime("1:30 PM", false));
        cinema1.add(new ShowTime("4:30 PM", false));

        ArrayList<ShowTime> cinema2 = new ArrayList<>();
        cinema2.add(new ShowTime("11:00 AM", false));
        cinema2.add(new ShowTime("2:00 PM", false));
        cinema2.add(new ShowTime("5:00 PM", false));

        adapter1 = new ShowtimeAdapter(
                this,
                cinema1,
                1,
                (cinema,pos)->{

                    adapter1.selectPosition(pos);
                    adapter2.selectPosition(-1);

                    adapter1.setActive(true);
                    adapter2.setActive(false);
                    selectedTime = cinema1.get(pos).getTime();
                    selectedCinemaScreen = "Cinema 1";
                    btnContinue.setVisibility(View.VISIBLE);

                });

        adapter2 = new ShowtimeAdapter(
                this,
                cinema2,
                2,
                (cinema,pos)->{

                    adapter2.selectPosition(pos);
                    adapter1.selectPosition(-1);

                    adapter2.setActive(true);
                    adapter1.setActive(false);
                    selectedTime = cinema2.get(pos).getTime();
                    selectedCinemaScreen = "Cinema 2";
                    btnContinue.setVisibility(View.VISIBLE);

                });


        rvTime1.setAdapter(adapter1);
        rvTime2.setAdapter(adapter2);

    }
}