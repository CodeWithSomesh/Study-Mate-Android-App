package com.example.grademaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClassesCardDetailsActivity extends AppCompatActivity {
    private TextView moduleNameText, classModeText, locationText, timeText, lecturerNameText,
            lecturerEmailText, onlineClassURLText, classOccurenceText, onceOrRepeatingLabel,
            occurenceDays, deleteButton, updateButton;
    private FirebaseDatabase db;
    private DatabaseReference classesReference, usersReference;
    private LinearLayout onlineClassLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_details);

        //Getting input from Card Context in MyAdapter
        String classID = getIntent().getStringExtra("classID");
        String classMode = getIntent().getStringExtra("classMode");
        String moduleName = getIntent().getStringExtra("moduleName");
        String room = getIntent().getStringExtra("roomNumber");
        String building = getIntent().getStringExtra("building");
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");
        String lecturerName = getIntent().getStringExtra("lecturerName");
        String lecturerEmail = getIntent().getStringExtra("lecturerEmail");
        String onlineClassURL = getIntent().getStringExtra("onlineClassURL");
        String occurence = getIntent().getStringExtra("occurence");
        ArrayList<String> daysList = getIntent().getStringArrayListExtra("days");
        String date = getIntent().getStringExtra("date");
        ImageView backButton = findViewById(R.id.backButton);
        System.out.println(onlineClassURL);
        System.out.println("Class ID: " + classID);

        // Check if daysList is not null and then join the elements for display
        String daysString = daysList != null ? String.join(", ", daysList) : "No days available";

        //Get User ID
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //Initialize UI Components
        moduleNameText = findViewById(R.id.moduleName);
        classModeText = findViewById(R.id.classModeText);
        locationText = findViewById(R.id.locationText);
        timeText = findViewById(R.id.timeText);
        lecturerNameText = findViewById(R.id.lecturerNameText);
        lecturerEmailText = findViewById(R.id.lecturerEmailText);
        onlineClassURLText = findViewById(R.id.onlineClassURLText);
        classOccurenceText = findViewById(R.id.classOccurenceText);
        onlineClassLayout = findViewById(R.id.onlineClassLayout);
        onceOrRepeatingLabel = findViewById(R.id.onceOrRepeatingLabel);
        occurenceDays = findViewById(R.id.occurenceDays);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);

        //Setting Text from retrieved variables
        classModeText.setText(classMode);
        moduleNameText.setText(moduleName);
        locationText.setText(room + ", " + building);
        timeText.setText(startTime + " - " + endTime);
        lecturerNameText.setText(lecturerName);
        lecturerEmailText.setText(lecturerEmail);
        classOccurenceText.setText(occurence);
        occurenceDays.setText(daysString);
        onlineClassURLText.setText(onlineClassURL);

        if (classModeText.getText().toString().equals("In Person")){
            onlineClassLayout.setVisibility(View.GONE);
        } else if (classModeText.getText().toString().equals("Online")) {
            onlineClassLayout.setVisibility(View.VISIBLE);
            onlineClassURLText.setText(onlineClassURL);
            System.out.println(onlineClassURLText.getText().toString());
        }

        if (classOccurenceText.getText().toString().equals("Once")){
            onceOrRepeatingLabel.setText("Once On:");
            occurenceDays.setText(date);
        } else if (classOccurenceText.getText().toString().equals("Repeating")) {
            occurenceDays.setText(daysString);
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
                showDeleteConfirmationDialog(classID);
            }
        });

        //Update Class Feature
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassesCardDetailsActivity.this, UpdateClassActivity.class);
                intent.putExtra("classID", classID);
                intent.putExtra("moduleName", moduleName);
                intent.putExtra("classMode", classMode);
                intent.putExtra("roomNumber", room);
                intent.putExtra("building", building);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                intent.putExtra("lecturerName", lecturerName);
                intent.putExtra("lecturerEmail", lecturerEmail);
                intent.putExtra("onlineClassURL", onlineClassURL);
                intent.putExtra("occurence", occurence);
                intent.putStringArrayListExtra("days", (ArrayList<String>) daysList);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });


    }

    private void showDeleteConfirmationDialog(String classID) {
        // Create and show the confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this class?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteClassFromFirebase(classID);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteClassFromFirebase(String classID) {
        System.out.println("Class ID to be deleted: " + classID);

        //Get User ID
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize Firebase Database
        db = FirebaseDatabase.getInstance();
        classesReference = db.getReference("Users").child(userID).child("Classes");

        if (classID != null) {
            classesReference.child(classID).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Show a success message
                        Toast.makeText(ClassesCardDetailsActivity.this, "Class deleted successfully", Toast.LENGTH_SHORT).show();
                        //Redirect to Home Page after deletion
                        Intent intent = new Intent(ClassesCardDetailsActivity.this, HomeFragment.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        // Show an error message
                        Toast.makeText(ClassesCardDetailsActivity.this, "Failed to delete class: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Class ID is missing. Cannot delete.", Toast.LENGTH_SHORT).show();
        }
    }
}