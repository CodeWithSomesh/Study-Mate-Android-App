package com.example.grademaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class ExamsCardDetailsActivity extends AppCompatActivity {

    private TextView moduleNameText, classModeText, locationText, timeText, lecturerNameText,
            lecturerEmailText, onlineExamURLText, durationText, deleteButton, updateButton;
    private FirebaseDatabase db;
    private DatabaseReference examRef, examsReference, usersReference;
    private LinearLayout onlineClassLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exams_card_details);


        //Getting input from Card Context in MyAdapter
        String examID = getIntent().getStringExtra("examID");
        String examMode = getIntent().getStringExtra("examMode");
        String moduleName = getIntent().getStringExtra("moduleName");
        String room = getIntent().getStringExtra("roomNumber");
        String building = getIntent().getStringExtra("building");
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");
        String lecturerName = getIntent().getStringExtra("lecturerName");
        String lecturerEmail = getIntent().getStringExtra("lecturerEmail");
        String onlineExamURL = getIntent().getStringExtra("onlineExamURL");
        String date = getIntent().getStringExtra("date");
        ImageView backButton = findViewById(R.id.backButton);


        //Get User ID
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //Initialize UI Components
        moduleNameText = findViewById(R.id.moduleName);
        classModeText = findViewById(R.id.classModeText);
        locationText = findViewById(R.id.locationText);
        timeText = findViewById(R.id.timeText);
        lecturerNameText = findViewById(R.id.lecturerNameText);
        lecturerEmailText = findViewById(R.id.lecturerEmailText);
        onlineExamURLText = findViewById(R.id.onlineExamURLText);
        onlineClassLayout = findViewById(R.id.onlineClassLayout);
        durationText = findViewById(R.id.durationText);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);

        //Find the duration of exam by finding the time difference
        // Convert the string times to LocalTime
        LocalTime startTimeString = LocalTime.parse(startTime);
        LocalTime endTimeString = LocalTime.parse(endTime);

        // Calculate the duration between start and end times
        Duration duration = Duration.between(startTimeString, endTimeString);

        // Get the difference in hours and minutes
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        // Print the result
        System.out.println("Duration: " + hours + " hours " + minutes + " minutes");

        //Setting Text from retrieved variables
        classModeText.setText(examMode);
        moduleNameText.setText(moduleName);
        locationText.setText(room + ", " + building);
        timeText.setText(startTime + " - " + endTime);
        lecturerNameText.setText(lecturerName);
        lecturerEmailText.setText(lecturerEmail);
        onlineExamURLText.setText(onlineExamURL);
        if (minutes <= 0){
            durationText.setText(hours + " hours");
        } else {
            durationText.setText(hours + " hours " + minutes + " minutes");
        }


        // Initialize Firebase Database
        db = FirebaseDatabase.getInstance();
        examRef = db.getReference("Users").child(userID).child("Classes");

        //Dynamic Online Class Layout
        if (classModeText.getText().toString().equals("In Person")){
            onlineClassLayout.setVisibility(View.GONE);
        } else if (classModeText.getText().toString().equals("Online")) {
            onlineClassLayout.setVisibility(View.VISIBLE);
            onlineExamURLText.setText(onlineExamURL);
            //System.out.println(onlineExamURLText.getText().toString());
        }


        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity using the OnBackPressedDispatcher
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        //Delete Class Feature
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog(examID);
            }
        });

        //Update Class Feature
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExamsCardDetailsActivity.this, UpdateExamActivity.class);
                intent.putExtra("examID", examID);
                intent.putExtra("moduleName", moduleName);
                intent.putExtra("examMode", examMode);
                intent.putExtra("roomNumber", room);
                intent.putExtra("building", building);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                intent.putExtra("lecturerName", lecturerName);
                intent.putExtra("lecturerEmail", lecturerEmail);
                intent.putExtra("onlineExamURL", onlineExamURLText.getText().toString());
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

    private void showDeleteConfirmationDialog(String examID) {
        // Create and show the confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this exam?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteClassFromFirebase(examID);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteClassFromFirebase(String examID) {
        //System.out.println("Exam ID to be deleted: " + examID);

        //Get User ID
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize Firebase Database
        db = FirebaseDatabase.getInstance();
        examsReference = db.getReference("Users").child(userID).child("Exams");

        if (examID != null) {
            examsReference.child(examID).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Show a success message
                        Toast.makeText(ExamsCardDetailsActivity.this, "Exam deleted successfully", Toast.LENGTH_SHORT).show();
                        //Redirect to Home Page after deletion
                        Intent intent = new Intent(ExamsCardDetailsActivity.this, HomeFragment.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Show an error message
                        Toast.makeText(ExamsCardDetailsActivity.this, "Failed to delete exam: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Exam ID is missing. Cannot delete.", Toast.LENGTH_SHORT).show();
        }
    }
}