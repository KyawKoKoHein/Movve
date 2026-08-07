package com.kkkh.movve;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {
    private ImageButton btnBack;

    private TextInputEditText edtName;
    private TextInputEditText edtPhone;
    private ImageView imgProfile;

    private Uri profileUri;

    private MaterialButton btnSave;

    //Image upload
    private final ActivityResultLauncher<String[]> imagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.OpenDocument(),
                    uri -> {

                        if (uri != null) {

                            try {

                                getContentResolver().takePersistableUriPermission(
                                        uri,
                                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                                );

                            } catch (SecurityException e) {

                                e.printStackTrace();

                            }

                            profileUri = uri;

                            imgProfile.setImageURI(uri);

                        }

                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnBack = findViewById(R.id.btnBack);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        imgProfile = findViewById(R.id.imgProfile);

        btnSave = findViewById(R.id.btnSave);

        TextView txtChangePhoto = findViewById(R.id.txtChangePhoto);

        imgProfile.setOnClickListener(v ->
                imagePicker.launch(new String[]{"image/*"}));

        txtChangePhoto.setOnClickListener(v ->
                imagePicker.launch(new String[]{"image/*"}));

        btnBack.setOnClickListener(v -> finish());

        loadProfile();

        btnSave.setOnClickListener(v -> saveProfile());

    }

    private void loadProfile() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "UserProfile",
                        MODE_PRIVATE);

        edtName.setText(
                preferences.getString(
                        "name",
                        ""));

        edtPhone.setText(
                preferences.getString(
                        "phone",
                        ""));

        String imageUri =
                preferences.getString(
                        "profileImage",
                        null);

        if (imageUri != null) {

            profileUri = Uri.parse(imageUri);

            imgProfile.setImageURI(profileUri);

        }

    }

    private void saveProfile() {

        String name =
                edtName.getText()
                        .toString()
                        .trim();

        String phone =
                edtPhone.getText()
                        .toString()
                        .trim();

        SharedPreferences.Editor editor =
                getSharedPreferences(
                        "UserProfile",
                        MODE_PRIVATE)
                        .edit();

        editor.putString("name", name);
        editor.putString("phone", phone);

        if(profileUri != null){

            editor.putString(
                    "profileImage",
                    profileUri.toString());

        }

        editor.apply();

        finish();

    }

}