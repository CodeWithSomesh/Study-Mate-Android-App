package com.example.grademaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private ImageView backButton;
    private TextView registerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        registerTextView = findViewById(R.id.registerText);
        backButton = findViewById(R.id.backButton);
        Button loginButton = findViewById(R.id.loginButton);

        // Create a SpannableString
        SpannableString spannableString = new SpannableString("Don\\'t have an account? Sign Up");

        // Set an underline only on the "Sign Up" part
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 24, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable string to the TextView
        registerTextView.setText(spannableString);

        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity using the OnBackPressedDispatcher
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        // Navigate to the Login Activity
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login navigation
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get All Inputs
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
            }
        });
    }
}