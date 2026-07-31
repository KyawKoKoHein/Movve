package com.kkkh.movve;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialButton btnConfirmPayment;

    private TextView txtMovie;
    private TextView txtCinema;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtSeats;
    private TextView txtTotalPrice;

    private RadioButton rbKBZPay;
    private RadioButton rbAYAPay;

    // QR section
    private LinearLayout layoutQr;
    private ImageView imgPaymentQr;
    private TextView txtQrTitle;
    private TextView txtPaymentInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        btnBack = findViewById(R.id.btnBack);
        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);

        txtMovie = findViewById(R.id.txtMovie);
        txtCinema = findViewById(R.id.txtCinema);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtSeats = findViewById(R.id.txtSeats);
        txtTotalPrice = findViewById(R.id.txtTotal);

        rbKBZPay = findViewById(R.id.rbKBZPay);
        rbAYAPay = findViewById(R.id.rbAYAPay);

        // QR views
        layoutQr = findViewById(R.id.layoutQr);
        imgPaymentQr = findViewById(R.id.imgPaymentQr);
        txtQrTitle = findViewById(R.id.txtQrTitle);
        txtPaymentInstruction =
                findViewById(R.id.txtPaymentInstruction);

        // Back button

        btnBack.setOnClickListener(v -> finish());

        // Receive booking data
        int movieId = getIntent().getIntExtra("movieId",-1);

        String cinemaName = getIntent().getStringExtra("cinemaName");

        String cinemaScreen = getIntent().getStringExtra("cinemaScreen");

        String selectedDate =getIntent().getStringExtra("selectedDate");

        String selectedTime = getIntent().getStringExtra("selectedTime");

        ArrayList<String> selectedSeats = getIntent().getStringArrayListExtra("selectedSeats");

        int totalPrice = getIntent().getIntExtra("totalPrice",0);

        // Display booking data
        txtCinema.setText("Cinema: " + cinemaName);

        txtDate.setText("Date: " + selectedDate);
        txtTime.setText("Time: " + selectedTime);


        if (selectedSeats != null &&
                !selectedSeats.isEmpty()) {

            txtSeats.setText(
                    "Seats: "
                            + android.text.TextUtils.join(
                            ", ",
                            selectedSeats));

        } else {

            txtSeats.setText("Seats: -");
        }


        txtTotalPrice.setText("MMK " + totalPrice);

        // Load movie title
        loadMovieTitle(movieId);

        // Payment selection
        rbKBZPay.setOnClickListener(v -> {

            // Select KBZPay
            rbKBZPay.setChecked(true);
            rbAYAPay.setChecked(false);

            showKBZPayQR();

        });


        rbAYAPay.setOnClickListener(v -> {

            // Select AYAPay
            rbAYAPay.setChecked(true);
            rbKBZPay.setChecked(false);

            showAYAPayQR();

        });

        // Pay button

        btnConfirmPayment.setOnClickListener(v -> {

            if (!rbKBZPay.isChecked() && !rbAYAPay.isChecked()) {

                Toast.makeText(
                        this,
                        "Please select a payment method",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            String paymentMethod;

            if (rbAYAPay.isChecked()) {

                paymentMethod = "AYAPay";

            } else {

                paymentMethod = "KBZPay";

            }

            // Navigate to success page

            Intent intent =
                    new Intent(
                            PaymentActivity.this,
                            ReceiptActivity.class);


            intent.putExtra("movieId", movieId);

            intent.putExtra("cinemaName",cinemaName);

            intent.putExtra("cinemaScreen", cinemaScreen);

            intent.putExtra( "selectedDate", selectedDate);

            intent.putExtra("selectedTime", selectedTime);

            intent.putStringArrayListExtra("selectedSeats", selectedSeats);

            intent.putExtra("totalPrice", totalPrice);

            intent.putExtra("paymentMethod",paymentMethod);
            startActivity(intent);

            finish();

        });

    }

    // Showing KBZPay QR

    private void showKBZPayQR() {

        layoutQr.setVisibility(View.VISIBLE);

        txtQrTitle.setText(
                "KBZPay QR");

        imgPaymentQr.setImageResource(
                R.drawable.kpay_qr);

        txtPaymentInstruction.setText(
                "Open KBZPay and scan this QR code to make payment."
        );

    }

    // Showing AYAPay QR

    private void showAYAPayQR() {

        layoutQr.setVisibility(View.VISIBLE);

        txtQrTitle.setText(
                "AYAPay QR");

        imgPaymentQr.setImageResource(
                R.drawable.aya_qr);

        txtPaymentInstruction.setText(
                "Open AYAPay and scan this QR code to make payment."
        );

    }


    // Loading movie title from movies.xml
    private void loadMovieTitle(int movieId) {

        try {

            XmlResourceParser parser =
                    getResources().getXml(
                            R.xml.movies);

            int eventType =
                    parser.getEventType();

            while (eventType !=
                    XmlPullParser.END_DOCUMENT) {

                if (eventType ==
                        XmlPullParser.START_TAG
                        &&
                        parser.getName()
                                .equals("movie")) {

                    int id =
                            Integer.parseInt(
                                    parser.getAttributeValue(
                                            null,
                                            "id"));

                    if (id == movieId) {

                        String title =
                                parser.getAttributeValue(
                                        null,
                                        "title");

                        txtMovie.setText(
                                "Movie: " + title);

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