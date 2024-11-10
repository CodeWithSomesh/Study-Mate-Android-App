package com.example.grademaster;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class UpdateExamActivity extends AppCompatActivity {

    private EditText moduleName, roomNum, building, lecturerName, lecturerEmail, onlineURL;
    private TextView inPersonButton, onlineButton, cancelButton, updateButton, onlineClassURLLabel,
            dateLabel;
    private ImageView backButton;
    private DatePicker date;
    private TimePicker startTime, endTime;
    private LinearLayout roomBuildingLayout;
    private FirebaseDatabase db;
    private DatabaseReference examsReference;
    private String examID, userID, examMode, dateText, startTimeText, endTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_exam);


        //Initialize UI Components
        //EditTexts
        moduleName = findViewById(R.id.inputModuleName);
        roomNum = findViewById(R.id.inputRoom);
        building = findViewById(R.id.inputBuilding);
        lecturerName = findViewById(R.id.inputTeacherName);
        lecturerEmail = findViewById(R.id.inputTeacherEmail);
        onlineURL = findViewById(R.id.inputClassURL);

        //ImageView
        backButton = findViewById(R.id.backButton);

        //TextView
        inPersonButton = findViewById(R.id.inPersonButton);
        onlineButton = findViewById(R.id.onlineButton);
        cancelButton = findViewById(R.id.cancelButton);
        updateButton = findViewById(R.id.updateButton);
        onlineClassURLLabel = findViewById(R.id.onlineClassURLLabel);
        dateLabel = findViewById(R.id.dateLabel);

        //Date Picker & Time Picker
        date = findViewById(R.id.datePicker);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);

        //Layouts
        roomBuildingLayout = findViewById(R.id.roomBuildingLayout);

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        examID = getIntent().getStringExtra("examID");
        examsReference = db.getReference("Users").child(userID).child("Exams").child(examID);
        System.out.println(examID);
        //System.out.println("I am at Update Class Activity Page");

        // Populate fields with existing data in Firebase DB
        examMode = getIntent().getStringExtra("examMode");
        moduleName.setText(getIntent().getStringExtra("moduleName"));
        roomNum.setText(getIntent().getStringExtra("roomNumber"));
        building.setText(getIntent().getStringExtra("building"));
        lecturerName.setText(getIntent().getStringExtra("lecturerName"));
        lecturerEmail.setText(getIntent().getStringExtra("lecturerEmail"));
        onlineURL.setText(getIntent().getStringExtra("onlineExamURL"));
        dateText = getIntent().getStringExtra("date");
        startTimeText = getIntent().getStringExtra("startTime");
        endTimeText = getIntent().getStringExtra("endTime");

        // Set time in Start TimePicker
        if (startTimeText != null && !startTimeText.isEmpty()) {
            String[] startTimeParts = startTimeText.split(":"); // Split the start time string (HH:mm)
            int startHour = Integer.parseInt(startTimeParts[0]);
            int startMinute = Integer.parseInt(startTimeParts[1]);

            startTime.setHour(startHour);
            startTime.setMinute(startMinute);
        }

        // Set time in End TimePicker
        if (endTimeText != null && !endTimeText.isEmpty()) {
            String[] endTimeParts = endTimeText.split(":"); // Split the end time string (HH:mm)
            int endHour = Integer.parseInt(endTimeParts[0]);
            int endMinute = Integer.parseInt(endTimeParts[1]);

            endTime.setHour(endHour);
            endTime.setMinute(endMinute);
        }

        //Default Display
        if (examMode.equals("In Person")){
            // In Person Button need to Switch to Light Teal background with White text color
            ViewCompat.setBackgroundTintList(
                    inPersonButton,
                    ColorStateList.valueOf(ContextCompat.getColor(UpdateExamActivity.this, R.color.light_teal))
            );
            inPersonButton.setTextColor(Color.parseColor("#FFFFFF"));
            inPersonButton.setBackgroundResource(R.drawable.button_background);

            // Online Button need to Switch to Gray background with gray text color
            ViewCompat.setBackgroundTintList(
                    onlineButton,
                    ColorStateList.valueOf(Color.parseColor("#333333"))
            );
            onlineButton.setTextColor(Color.parseColor("#888888"));
            onlineButton.setBackgroundResource(R.drawable.button_background);

            //Online Class URL input and labels shall not be displayed
            onlineClassURLLabel.setVisibility(View.GONE);
            onlineURL.setVisibility(View.GONE);

            //Room Number and Building inputs and labels MUST be displayed
            roomBuildingLayout.setVisibility(View.VISIBLE);
        } else{
            // Online Button need to Switch to Light Teal background with White text color
            ViewCompat.setBackgroundTintList(
                    onlineButton,
                    ColorStateList.valueOf(ContextCompat.getColor(UpdateExamActivity.this, R.color.light_teal))
            );
            onlineButton.setTextColor(Color.parseColor("#FFFFFF"));
            onlineButton.setBackgroundResource(R.drawable.button_background);

            // In Person Button need to Switch to Gray background with gray text color
            ViewCompat.setBackgroundTintList(
                    inPersonButton,
                    ColorStateList.valueOf(Color.parseColor("#333333"))
            );
            inPersonButton.setTextColor(Color.parseColor("#888888"));
            inPersonButton.setBackgroundResource(R.drawable.button_background);

            //Room Number and Building inputs and labels shall not be displayed
            roomBuildingLayout.setVisibility(View.GONE);

            //Online Class URL input and labels MUST be displayed
            onlineClassURLLabel.setVisibility(View.VISIBLE);
            onlineURL.setVisibility(View.VISIBLE);
        }

        // Handle Cancel button click
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        // Handle Back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        // Create an empty list to hold clicked Days TextView values
        List<String> currentExamMode = new ArrayList<>();
        currentExamMode.add(examMode);

        // Handle Online button click
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentExamMode.contains(inPersonButton.getText().toString())) {
                    //currentExamMode.remove(inPersonButton.getText().toString());
                    currentExamMode.clear();
                    currentExamMode.add(onlineButton.getText().toString());
                    System.out.println(currentExamMode);
                }

                // Online Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        onlineButton,
                        ColorStateList.valueOf(ContextCompat.getColor(UpdateExamActivity.this, R.color.light_teal))
                );
                onlineButton.setTextColor(Color.parseColor("#FFFFFF"));
                onlineButton.setBackgroundResource(R.drawable.button_background);

                // In Person Button need to Switch to Gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        inPersonButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                inPersonButton.setTextColor(Color.parseColor("#888888"));
                inPersonButton.setBackgroundResource(R.drawable.button_background);

                //Room Number and Building inputs and labels shall not be displayed
                roomBuildingLayout.setVisibility(View.GONE);

                //Online Class URL input and labels MUST be displayed
                onlineClassURLLabel.setVisibility(View.VISIBLE);
                onlineURL.setVisibility(View.VISIBLE);
            }
        });

        // Handle In Person button click
        inPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentExamMode.contains(onlineButton.getText().toString())) {

                    //currentExamMode.remove(onlineButton.getText().toString());
                    currentExamMode.clear();
                    currentExamMode.add(inPersonButton.getText().toString());
                    System.out.println(currentExamMode);
                }

                // In Person Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        inPersonButton,
                        ColorStateList.valueOf(ContextCompat.getColor(UpdateExamActivity.this, R.color.light_teal))
                );
                inPersonButton.setTextColor(Color.parseColor("#FFFFFF"));
                inPersonButton.setBackgroundResource(R.drawable.button_background);

                // Online Button need to Switch to Gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        onlineButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                onlineButton.setTextColor(Color.parseColor("#888888"));
                onlineButton.setBackgroundResource(R.drawable.button_background);

                //Online Class URL input and labels shall not be displayed
                onlineClassURLLabel.setVisibility(View.GONE);
                onlineURL.setVisibility(View.GONE);

                //Room Number and Building inputs and labels MUST be displayed
                roomBuildingLayout.setVisibility(View.VISIBLE);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get User ID & Email
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


                //Get All Inputs
                String currentExamModeText = currentExamMode.get(0);
                System.out.println("Current Class Mode: " + currentExamModeText);
                String moduleNameText = moduleName.getText().toString();
                String roomNumberText = roomNum.getText().toString();
                String buildingText = building.getText().toString();
                String lecturerNameText = lecturerName.getText().toString();
                String lecturerEmailText = lecturerEmail.getText().toString();
                String onlineExamURLText = onlineURL.getText().toString();

                // Retrieve and format date
                String dateString = "";
                int day = date.getDayOfMonth();
                int month = date.getMonth() + 1; // Month is 0-based, so add 1
                int year = date.getYear();
                dateString = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);

                // Retrieve and format start time
                String startTimeString = "";
                int startHour = startTime.getHour();
                int startMinute = startTime.getMinute();
                startTimeString = String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute);

                // Retrieve and format end time
                String endTimeString = "";
                int endHour = endTime.getHour();
                int endMinute = endTime.getMinute();
                endTimeString = String.format(Locale.getDefault(), "%02d:%02d", endHour, endMinute);

                // Print out the date and time strings to verify
                System.out.println("Date: " + dateString);
                System.out.println("Start Time: " + startTimeString);
                System.out.println("End Time: " + endTimeString);


                //Validate All Inputs

                //If Class Mode is Online
                if (Objects.equals(currentExamModeText, "Online")) {
                    if (TextUtils.isEmpty(moduleNameText)) {
                        Toast.makeText(UpdateExamActivity.this, "Please Enter Your Module Name", Toast.LENGTH_LONG).show();
                        moduleName.setError("Module Name is required");
                        moduleName.requestFocus();
                    } else if (TextUtils.isEmpty(onlineExamURLText)) {
                        Toast.makeText(UpdateExamActivity.this, "Please Enter Your Online Class URL", Toast.LENGTH_LONG).show();
                        onlineURL.setError("Online Class URL is required");
                        onlineURL.requestFocus();
                    } else {
                        roomNumberText = "None";
                        buildingText = "None";

                        // Prepare data for update
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("examMode", currentExamModeText);
                        updates.put("moduleName", moduleNameText);
                        updates.put("roomNumber", roomNumberText);
                        updates.put("building", buildingText);
                        updates.put("lecturerName", lecturerNameText);
                        updates.put("lecturerEmail", lecturerEmailText);
                        updates.put("onlineExamURL", onlineExamURLText);
                        updates.put("date", dateString);
                        updates.put("startTime", startTimeString);
                        updates.put("endTime", endTimeString);

                        // Update Firebase database
                        examsReference.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateExamActivity.this,
                                            "Exam details updated successfully",
                                            Toast.LENGTH_SHORT).show();
                                    // Navigate to Class Details Page
                                    Intent intent = new Intent(UpdateExamActivity.this, HomeFragment.class);
                                    startActivity(intent);
                                }

                                else {
                                    Toast.makeText(UpdateExamActivity.this,
                                            "Failed to update exam details",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                } else if (Objects.equals(currentExamModeText, "In Person")) {
                    if (TextUtils.isEmpty(moduleNameText)) {
                        Toast.makeText(UpdateExamActivity.this, "Please Enter Your Module Name", Toast.LENGTH_LONG).show();
                        moduleName.setError("Module Name is required");
                        moduleName.requestFocus();
                    } else if (TextUtils.isEmpty(roomNumberText)) {
                        Toast.makeText(UpdateExamActivity.this, "Please Enter Your Room Number", Toast.LENGTH_LONG).show();
                        roomNum.setError("Room Number is required");
                        roomNum.requestFocus();
                    } else if (TextUtils.isEmpty(buildingText)) {
                        Toast.makeText(UpdateExamActivity.this, "Please Enter The Building", Toast.LENGTH_LONG).show();
                        building.setError("Building is required");
                        building.requestFocus();
                    } else {
                        onlineExamURLText = "None";

                        // Prepare data for update
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("examMode", currentExamModeText);
                        updates.put("moduleName", moduleNameText);
                        updates.put("roomNumber", roomNumberText);
                        updates.put("building", buildingText);
                        updates.put("lecturerName", lecturerNameText);
                        updates.put("lecturerEmail", lecturerEmailText);
                        updates.put("onlineExamURL", onlineExamURLText);
                        updates.put("date", dateString);
                        updates.put("startTime", startTimeString);
                        updates.put("endTime", endTimeString);

                        // Update Firebase database
                        examsReference.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateExamActivity.this,
                                            "Exam details updated successfully",
                                            Toast.LENGTH_SHORT).show();
                                    // Navigate to Class Details Page
                                    Intent intent = new Intent(UpdateExamActivity.this, HomeFragment.class);
                                    startActivity(intent);
                                }

                                else {
                                    Toast.makeText(UpdateExamActivity.this,
                                            "Failed to update exam details",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }

            }
        });
    }
}