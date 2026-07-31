package com.kkkh.movve;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity {

    private ImageButton btnBack;

    private TextInputEditText edtName;
    private TextInputEditText edtPhone;

    // Receipt upload
    private CardView cardUploadReceipt;
    private TextView txtReceiptName;

    private MaterialButton btnCompleteBooking;

    // Booking information
    private TextView txtMovie;
    private TextView txtCinema;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtSeats;
    private TextView txtPrice;
    private TextView txtPaymentMethod;

    private Uri receiptUri;
    private final ActivityResultLauncher<String> receiptPicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {

                        if (uri != null) {

                            receiptUri = uri;

                            // Update upload box
                            txtReceiptName.setText(
                                    "Receipt uploaded successfully ✓"
                            );

                            checkForm();
                        }
                    });
    // Booking data
    private int movieId;
    private String cinemaName;
    private String cinemaScreen;
    private String selectedDate;
    private String selectedTime;
    private ArrayList<String> selectedSeats;
    private int totalPrice;

    // Image Picker
    private String paymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receipt);

        // Initialize Views

        btnBack = findViewById(R.id.btnBack);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);

        cardUploadReceipt =
                findViewById(R.id.cardUploadReceipt);

        txtReceiptName =
                findViewById(R.id.txtReceiptName);

        btnCompleteBooking =
                findViewById(R.id.btnCompleteBooking);


        txtMovie =
                findViewById(R.id.txtMovie);

        txtCinema =
                findViewById(R.id.txtCinema);

        txtDate =
                findViewById(R.id.txtDate);

        txtTime =
                findViewById(R.id.txtTime);

        txtSeats =
                findViewById(R.id.txtSeats);

        txtPrice =
                findViewById(R.id.txtPrice);

        txtPaymentMethod =
                findViewById(R.id.txtPaymentMethod);

        // Back Button

        btnBack.setOnClickListener(v -> finish());

        // Receive Booking Information

        movieId =getIntent().getIntExtra("movieId",-1);

        cinemaName =getIntent().getStringExtra("cinemaName");

        cinemaScreen = getIntent().getStringExtra("cinemaScreen");

        selectedDate =getIntent().getStringExtra("selectedDate");

        selectedTime =getIntent().getStringExtra("selectedTime");

        selectedSeats =getIntent().getStringArrayListExtra("selectedSeats");

        totalPrice =getIntent().getIntExtra("totalPrice",0);

        paymentMethod =getIntent().getStringExtra("paymentMethod");

        // Display Booking Information

        txtCinema.setText("Cinema: " + cinemaName + " [ " + cinemaScreen + " ]");

        txtDate.setText("Date: " + selectedDate);

        txtTime.setText("Time: " + selectedTime);


        if (selectedSeats != null &&
                !selectedSeats.isEmpty()) {

            txtSeats.setText("Seats: " + selectedSeats.toString());

        } else {

            txtSeats.setText("Seats: -");
        }


        txtPrice.setText("Total: MMK " + totalPrice);


        txtPaymentMethod.setText("Payment: " + paymentMethod);


        // Load Movie Title

        loadMovieTitle(movieId);


        // Upload Receipt

        cardUploadReceipt.setOnClickListener(v -> {

            receiptPicker.launch("image/*");

        });


        // Check Form While Typing

        edtName.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        checkForm();
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                });


        edtPhone.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        checkForm();
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                });


        // Complete Booking
        btnCompleteBooking.setOnClickListener(v -> {

            String customerName =
                    edtName.getText()
                            .toString()
                            .trim();

            String phoneNumber =
                    edtPhone.getText()
                            .toString()
                            .trim();


            Intent intent =
                    new Intent(
                            ReceiptActivity.this,
                            BookingSuccessActivity.class);


            // Booking Information

            intent.putExtra("movieId",movieId);

            intent.putExtra("cinemaName", cinemaName);

            intent.putExtra("cinemaScreen", cinemaScreen);

            intent.putExtra("selectedDate", selectedDate);

            intent.putExtra("selectedTime", selectedTime);

            intent.putStringArrayListExtra("selectedSeats", selectedSeats);

            intent.putExtra("totalPrice",totalPrice);

            intent.putExtra("paymentMethod", paymentMethod);


            // Customer Information

            intent.putExtra("customerName",customerName);

            intent.putExtra("phoneNumber", phoneNumber);


            // Receipt Information

            if (receiptUri != null) {

                intent.putExtra("receiptUri", receiptUri.toString());
            }

            // Open Success Page
            startActivity(intent);

            finish();

        });


        // Initially disable button
        checkForm();
    }


    // Check Form

    private void checkForm() {

        String name = edtName.getText().toString().trim();

        String phone = edtPhone.getText().toString().trim();


        boolean validName = !name.isEmpty();

        boolean validPhone = !phone.isEmpty();

        boolean validReceipt = receiptUri != null;


        boolean ready = validName &&
                        validPhone &&
                        validReceipt;


        btnCompleteBooking.setEnabled(ready);


        if (ready) {

            btnCompleteBooking.setAlpha(1f);

        } else {

            btnCompleteBooking.setAlpha(0.5f);

        }
    }


    // Load Movie Title
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


                eventType =
                        parser.next();
            }


            parser.close();


        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}