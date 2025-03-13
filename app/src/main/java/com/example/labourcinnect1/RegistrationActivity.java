package com.example.labourcinnect1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextPassword, editTextName, editTextAddress, editTextMobile, editTextAge, editTextWorkType, editTextLocation;
    private ImageView profileImageView;
    private Button buttonSubmit, uploadPhotoButton, buttonGetLocation, buttonLogin; // Add Login button
    private Spinner spinnerState, spinnerCity;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration); // Ensure the layout is correct

        // Initialize views
        profileImageView = findViewById(R.id.profile_image);
        uploadPhotoButton = findViewById(R.id.upload_photo_button);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextAge = findViewById(R.id.editTextAge);
        editTextWorkType = findViewById(R.id.editTextWorkType);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextLocation = findViewById(R.id.editTextLocation);  // EditText to show location as address
        buttonSubmit = findViewById(R.id.buttonSubmit);
        spinnerState = findViewById(R.id.spinnerStates);
        spinnerCity = findViewById(R.id.spinnerCity);
        buttonGetLocation = findViewById(R.id.buttonGetLocation);
        buttonLogin = findViewById(R.id.buttonLogin); // Initialize the Login button

        // Initialize Location Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set OnClickListener for Upload Photo button
        uploadPhotoButton.setOnClickListener(v -> dispatchTakePictureIntent());

        // Set OnClickListener for Get Location button
        buttonGetLocation.setOnClickListener(v -> getCurrentLocation());

        // Populate state spinner with data
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(
                this, R.array.states_array, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(stateAdapter);

        // Set up state spinner selection to populate city spinner dynamically
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCitySpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Define the LocationCallback to get updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                Location location = locationResult.getLastLocation();
                if (location != null) {
                    getAddressFromLocation(location.getLatitude(), location.getLongitude());
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                }
            }
        };

        // Set onClickListener for submit button
        buttonSubmit.setOnClickListener(view -> {
            // Collect input values
            String name = editTextName.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String mobile = editTextMobile.getText().toString().trim();
            String ageStr = editTextAge.getText().toString().trim();
            String password = editTextPassword.getText().toString();
            String workType = editTextWorkType.getText().toString().trim();
            String state = spinnerState.getSelectedItem().toString();
            String city = spinnerCity.getSelectedItem().toString();
            String location = editTextLocation.getText().toString();

            // Perform validation
            if (name.isEmpty() || address.isEmpty() || mobile.isEmpty() || ageStr.isEmpty() || workType.isEmpty() || state.isEmpty() || city.isEmpty() || location.isEmpty()) {
                Toast.makeText(RegistrationActivity.this,""+R.string.please_fill_all_the_fields, Toast.LENGTH_SHORT).show();
            } else if (mobile.length() != 10) {
                editTextMobile.setError(getString(R.string.please_enter_a_10_digit_number));
            } else if (Integer.parseInt(ageStr) <= 18) {
                editTextAge.setError(getString(R.string.age_must_be_greater_than_18));
            } else if (password.length() <= 6) {
                editTextPassword.setError(getString(R.string.password_must_be_greater_than_6_characters));
            } else {
                Toast.makeText(RegistrationActivity.this, " "+R.string.registration_successful, Toast.LENGTH_SHORT).show();
                // Here you might want to proceed with the registration process (e.g., saving data)
            }
        });

        // Set onClickListener for the Login button
        buttonLogin.setOnClickListener(view -> {
            Intent loginIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
            loginIntent.putExtra("user_type", "labour");

            startActivity(loginIntent); // Navigate to LoginActivity
        });
    }

    // Capture image method
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImageView.setImageBitmap(imageBitmap);
        }
    }

    // Request location updates
    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            requestLocationUpdates();
        }
    }

    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    // Convert latitude and longitude to address
    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressString = address.getAddressLine(0);
                editTextLocation.setText(addressString);  // Display address in editTextLocation
            } else {
                Toast.makeText(this, ""+R.string.unable_to_fetch_address, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoder service not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates();
            } else {
                Toast.makeText(this, ""+R.string.location_permission_is_required_to_use_this_feature, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Update city spinner based on selected state
    private void updateCitySpinner(int statePosition) {
        int cityArrayId;
        switch (statePosition) {
            case 0:  // Maharashtra (or whatever your first state is)
                cityArrayId = R.array.Maharashtra;  // Update this based on your array
                break;
            // Add cases for other states
            default:
                return; // No default, no city updates
        }

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(
                this, cityArrayId, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);
    }
    private void setAppLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("SelectedLanguage", "en");
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
