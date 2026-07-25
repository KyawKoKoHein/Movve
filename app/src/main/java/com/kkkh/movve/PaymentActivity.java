package com.kkkh.movve;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnBack = findViewById(R.id.btnBack);
        btnPay = findViewById(R.id.btnPay);

        btnBack.setOnClickListener(v -> finish());

        btnPay.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            PaymentActivity.this,
                            BookingSuccessActivity.class);

            startActivity(intent);

            finish();

        });

    }
}