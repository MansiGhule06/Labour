package com.example.labourcinnect1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 101;
    private ImageView profileImage;
    private AppCompatButton uploadPhotoButton, buttonSubmit;
    private EditText editTextName, editTextAge, editTextMobile, editTextPassword, editTextAddress;
    private Spinner spinnerWorkType, spinnerStates, spinnerCity;
    private Uri imageUri;  // **This should be set AFTER image selection**
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        database referec

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("LabourConnectApp1/LabourUser");
        storageReference = FirebaseStorage.getInstance().getReference("LabourConnectApp1/LabourProfileImage");

        // Initialize Views
        profileImage = findViewById(R.id.profile_image);
        uploadPhotoButton = findViewById(R.id.upload_photo_button);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAddress = findViewById(R.id.editTextAddress);
        spinnerWorkType = findViewById(R.id.spinnerWorkType);
        spinnerStates = findViewById(R.id.spinnerStates);
        spinnerCity = findViewById(R.id.spinnerCity);

        // Setup Spinners
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStates.setAdapter(stateAdapter);

        ArrayAdapter<CharSequence> workTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.TypeOfWork, android.R.layout.simple_spinner_item);
        workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkType.setAdapter(workTypeAdapter);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
                R.array.Maharashtra, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);

        // Upload Image Button Click
        uploadPhotoButton.setOnClickListener(v -> checkStoragePermission());

        // **Image Upload SHOULD NOT be here, move it inside `registerUser()`**

        // Register Button Click
        buttonSubmit.setOnClickListener(v -> {
            if (validateInputs()) {
                registerUser();
            }
        });
    }

    // **Check and Request Storage Permission**
    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            pickImage();
        }
    }

    // **Activity Result Launcher for Image Picker**
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    profileImage.setImageURI(imageUri);
                    Log.d("ImagePicker", "Image selected: " + imageUri);
                } else {
                    Log.e("ImagePicker", "No image selected");
                }
            });

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    // **Validate Inputs**
    private boolean validateInputs() {
        boolean isValid = true;

        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name) || name.length() < 2) {
            editTextName.setError("Enter a valid name");
            isValid = false;
        }
        if (TextUtils.isEmpty(ageStr)) {
            editTextAge.setError("Enter age");
            isValid = false;
        }
        if (TextUtils.isEmpty(mobile) || mobile.length() != 10 || !mobile.matches("\\d+")) {
            editTextMobile.setError("Enter a valid 10-digit mobile number");
            isValid = false;
        }
        if (TextUtils.isEmpty(address)) {
            editTextAddress.setError("Enter address");
            isValid = false;
        }
        if (spinnerStates.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a state", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (spinnerCity.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (spinnerWorkType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a work type", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }
        if (imageUri == null) {
            Toast.makeText(this, "Please upload a profile image", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    // **Register User**
    private void registerUser() {
        String mobile = editTextMobile.getText().toString().trim();

        if (imageUri == null) {
            Toast.makeText(this, "Please upload a profile image", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference imageRef = storageReference.child(mobile + ".jpg");

        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot ->
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("profileImageUrl", uri.toString());

                            databaseReference.child(mobile).setValue(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(RegistrationActivity.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                    );
                        })
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Image Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
