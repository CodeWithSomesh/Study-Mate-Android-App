package com.example.grademaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private TextView backToLoginText;
    private Button resetPasswordButton;
    private FirebaseAuth authProfile;
    private final static String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        ImageView backButton = findViewById(R.id.backButton);
        backToLoginText = findViewById(R.id.backToLoginText);
        emailEditText = findViewById(R.id.emailInput);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);


        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity using the OnBackPressedDispatcher
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        // Navigate to the Login Activity
        backToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login navigation
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Reset password feature
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Get Email Input
                String emailInput = emailEditText.getText().toString();

                //Validate entered email
                if (TextUtils.isEmpty(emailInput)){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter A Valid Email", Toast.LENGTH_LONG).show();
                    emailEditText.setError("Valid Email is required");
                    emailEditText.requestFocus();
                } else {
                    resetPassword(emailInput);
                }
            }
        });
        
    }

    private void resetPassword(String emailInput) {
        //Get Instance of the Current User
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(emailInput).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please check your emails for password reset link", Toast.LENGTH_LONG).show();

                    //Redirect to Home Page after successful registration
                    Intent intent = new Intent(ForgotPasswordActivity.this, HomeActivity.class);

                    //To prevent user from returning to Register Activity when they press the back button after successful registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish(); //To close Register Activity
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        emailEditText.setError("User does not exists. Please register again");
                        emailEditText.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}