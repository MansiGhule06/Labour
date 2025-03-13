package com.example.labourcinnect1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangePassword extends Fragment {
    private EditText oldPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button changePasswordButton;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "UserPrefs";
    private static final String PASSWORD_KEY = "password";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        // Initialize UI components
        oldPasswordEditText = view.findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);

        // Initialize SharedPreferences (to store password locally)
        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Set button click listener
        changePasswordButton.setOnClickListener(v -> changePassword());

        return view;
    }

    private void changePassword() {
        String oldPass = oldPasswordEditText.getText().toString().trim();
        String newPass = newPasswordEditText.getText().toString().trim();
        String confirmPass = confirmPasswordEditText.getText().toString().trim();

        // Retrieve the stored password (default: "123456" for testing)
        String storedPassword = sharedPreferences.getString(PASSWORD_KEY, "123456");

        // Validate inputs
        if (TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmPass)) {
            showToast("Please fill in all fields");
            return;
        }
        if (!oldPass.equals(storedPassword)) {
            showToast("Old password is incorrect!");
            return;
        }
        if (!newPass.equals(confirmPass)) {
            showToast("New password and confirm password do not match!");
            return;
        }
        if (newPass.length() < 6) {
            showToast("Password must be at least 6 characters long!");
            return;
        }

        // Save the new password
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD_KEY, newPass);
        editor.apply();

        showToast("Password changed successfully!");
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
