package com.example.grademaster;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextView loginTextView;
    private EditText fullName, email, password, confirmPassword;
    private static final String TAG = "RegisterActivity";
    private FirebaseDatabase db;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        fullName = findViewById(R.id.inputFullName);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        confirmPassword = findViewById(R.id.inputConfirmPassword);
        loginTextView = findViewById(R.id.loginText);
        ImageView backButton = findViewById(R.id.backButton);
        ImageView eyeIcon1 = findViewById(R.id.eyeIcon1);
        ImageView eyeIcon2 = findViewById(R.id.eyeIcon2);
        Button registerButton = findViewById(R.id.signUpButton);


        // Create a SpannableString for "Already have an account? Log in"
        SpannableString spannableString = new SpannableString("Already have an account? Log In");

        // Set an underline only on the "Log in" part (index 25 to 31)
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 25, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable string to the TextView
        loginTextView.setText(spannableString);


        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity using the OnBackPressedDispatcher
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        // Navigate to the Login Activity
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login navigation
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Hide and Show Eye Icons while typing Password (Password EditText)
        eyeIcon1.setImageResource(R.drawable.eye_icon);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance()); //By default it is set to be hidden
        eyeIcon1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyeIcon1.setImageResource(R.drawable.eye_hide_icon);
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyeIcon1.setImageResource(R.drawable.eye_icon);
                }
            }
        });

        //Hide and Show Eye Icons while typing Password (Confirm Password EditText)
        eyeIcon2.setImageResource(R.drawable.eye_icon);
        confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance()); //By default it is set to be hidden
        eyeIcon2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (confirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyeIcon2.setImageResource(R.drawable.eye_hide_icon);
                } else {
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyeIcon2.setImageResource(R.drawable.eye_icon);
                }
            }
        });

        //Sign Up Button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get All Inputs
                String nameText = fullName.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();

                //Validate All Inputs
                if (TextUtils.isEmpty(nameText)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Name", Toast.LENGTH_LONG).show();
                    fullName.setError("Name is required");
                    fullName.requestFocus();
                } else if (TextUtils.isEmpty(emailText)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                    email.setError("Email is required");
                    email.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    Toast.makeText(RegisterActivity.this, "Please Enter A Valid Email", Toast.LENGTH_LONG).show();
                    email.setError("Valid Email is required");
                    email.requestFocus();
                } else if(TextUtils.isEmpty(passwordText)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                    password.setError("Password is required");
                    password.requestFocus();
                } else if (passwordText.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password should have 6 or more characters", Toast.LENGTH_LONG).show();
                    password.setError("Password too weak");
                    password.requestFocus();
                } else if(TextUtils.isEmpty(confirmPasswordText)){
                    Toast.makeText(RegisterActivity.this, "Please Confirm Your Password", Toast.LENGTH_LONG).show();
                    confirmPassword.setError("Password Confirmation is required");
                    confirmPassword.requestFocus();
                } else if(!passwordText.equals(confirmPasswordText)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Same Password", Toast.LENGTH_LONG).show();
                    confirmPassword.setError("Same Password is required");
                    confirmPassword.requestFocus();
                    //Clear the entered passwords
                    password.clearComposingText();
                    confirmPassword.clearComposingText();
                } else {
                    registerUser(nameText, emailText, passwordText);
                }
            }
        });
    }

    // Register User using the given credentials
    private void registerUser(String nameText, String emailText, String passwordText) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Get Instance of the Current User
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Get User ID & Email
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                    //Redirect to Login Page after successful registration
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                    //To prevent user from returning to Register Activity when they press the back button after successful registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    //Add User and Send Verification Email
                    assert firebaseUser != null;
                    Users user = new Users(userID, nameText, emailText, false, passwordText);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    reference.child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Use getActivity() or getContext() to provide a valid context
                                Toast.makeText(RegisterActivity.this, "Account Registered Successfully. You Can Log In Now.", Toast.LENGTH_LONG).show();
                                firebaseUser.sendEmailVerification();
                                startActivity(intent);
                                finish(); //To close Register Activity
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to Register User", Toast.LENGTH_LONG).show();
                            }
                        }
                    });






                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        password.setError("Your password is too weak. Use a mix of alphabets, symbols and numbers");
                        password.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        email.setError("The email you have entered is already in use");
                        email.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        email.setError("The email you have entered is already in use");
                        email.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}