package com.kkkh.movve;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kkkh.movve.adapter.SeatAdapter;
import com.kkkh.movve.model.Seat;

import java.util.ArrayList;

public class SeatSelectionActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvSeat;
    private TextView txtSelectedSeats;
    private TextView txtTotalPrice;
    private Button btnContinue;

    private final int PRICE_PER_SEAT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        btnBack = findViewById(R.id.btnBack);
        rvSeat = findViewById(R.id.rvSeat);
        txtSelectedSeats = findViewById(R.id.txtSelectedSeats);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        btnContinue = findViewById(R.id.btnContinue);

        btnBack.setOnClickListener(v -> finish());

        rvSeat.setLayoutManager(new GridLayoutManager(this, 8));

        ArrayList<Seat> seats = new ArrayList<>();

        createSeats(seats);

        SeatAdapter adapter =
                new SeatAdapter(this, seats, selectedSeats -> {

                    txtSelectedSeats.setText(
                            "Seats : " + selectedSeats.toString());

                    int total = selectedSeats.size() * PRICE_PER_SEAT;

                    txtTotalPrice.setText(
                            "MMK " + total);

                    btnContinue.setEnabled(!selectedSeats.isEmpty());

                });

        rvSeat.setAdapter(adapter);

        btnContinue.setOnClickListener(v -> {

            Intent intent =
                    new Intent(this, PaymentActivity.class);

            startActivity(intent);

        });

    }

    private void createSeats(ArrayList<Seat> seats){

        String[] rows={"A","B","C","D","E","F"};

        for(String row:rows){

            for(int i=1;i<=8;i++){

                boolean reserved=false;

                if(row.equals("A")&&(i==3||i==5))
                    reserved=true;

                if(row.equals("C")&&(i==2||i==6))
                    reserved=true;

                if(row.equals("E")&&i==4)
                    reserved=true;

                seats.add(new Seat(row+i,reserved));

            }

        }

    }

}