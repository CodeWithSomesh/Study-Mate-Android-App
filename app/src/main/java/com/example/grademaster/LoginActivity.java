package com.example.grademaster;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView registerTextView;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";

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
        ImageView backButton = findViewById(R.id.backButton);
        ImageView eyeIcon = findViewById(R.id.eyeIcon);
        Button loginButton = findViewById(R.id.loginButton);
        TextView forgotPasswordText = findViewById(R.id.forgotPasswordLink);

        authProfile = FirebaseAuth.getInstance();

        // Create a SpannableString
        SpannableString spannableString = new SpannableString("Do not have an account? Sign Up");

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

        //Hide and Show Eye Icons while typing Password
        eyeIcon.setImageResource(R.drawable.eye_icon);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance()); //By default it is set to be hidden
        eyeIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyeIcon.setImageResource(R.drawable.eye_hide_icon);
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyeIcon.setImageResource(R.drawable.eye_icon);
                }
            }
        });

        //Redirect to Forgot Password Page
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        //Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get All Inputs
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                //Validate All inputs
                if (TextUtils.isEmpty(emailText)){
                    Toast.makeText(LoginActivity.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                    email.setError("Email is required");
                    email.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    Toast.makeText(LoginActivity.this, "Please Enter A Valid Email", Toast.LENGTH_LONG).show();
                    email.setError("Valid Email is required");
                    email.requestFocus();
                } else if(TextUtils.isEmpty(passwordText)){
                    Toast.makeText(LoginActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                    password.setError("Password is required");
                    password.requestFocus();
                } else {
                    loginUser(emailText, passwordText);
                }


            }
        });
    }

    private void loginUser(String emailText, String passwordText) {

        authProfile.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Get Instance of the Current User
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //Redirect to Home Page after successful registration
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                    //To prevent user from returning to Register Activity when they press the back button after successful registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    assert firebaseUser != null;
                    if (firebaseUser.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish(); //To close Register Activity
                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }



                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        password.setError("User does not exists. Please register again");
                        password.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        email.setError("Invalid credentials. Please try again.");
                        email.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void showAlertDialog() {
        //Setup Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can't login without email verification.");

        //Open Email Apps when user clicks Continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //To open email app in new window and not within our app
                startActivity(intent);
            }
        });

        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        //Show the AlertDialog
        alertDialog.show();
    }

    //Check is user is logged in, if they are, then redirect them to the Home Page
    @Override
    protected void onStart() {
        super.onStart();

        if (authProfile.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish(); //To close Register Activity
        }
    }
}