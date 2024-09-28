package com.example.grademaster;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button emailLoginButton = findViewById(R.id.loginButton);
        Button emailSignUpButton = findViewById(R.id.signUpButton);


        emailLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle email login
                Toast.makeText(MainActivity.this, "Email login clicked", Toast.LENGTH_SHORT).show();
            }
        });

        emailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle email sign-up
                Toast.makeText(MainActivity.this, "Email sign-up clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}