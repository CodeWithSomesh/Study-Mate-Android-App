package com.example.grademaster;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateClassActivity extends AppCompatActivity {

    private EditText moduleName, roomNum, building, lecturerName, lecturerEmail, onlineURL;
    private TextView inPersonButton, onlineButton, onceButton, repeatingButton, mondayButton,
            tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton,
            sundayButton, cancelButton, saveButton, onlineClassURLLabel, daysLabel, dateLabel;
    private ImageView backButton;
    private DatePicker date;
    private TimePicker startTime, endTime;
    private LinearLayout roomBuildingLayout, daysLayout;
    private FirebaseDatabase db;
    private DatabaseReference classesReference;
    private String classID, userID, classMode, occurence, dateText, startTimeText, endTimeText;

    private AtomicBoolean isMondayButtonClicked = new AtomicBoolean(false);
    private AtomicBoolean isTuesdayButtonClicked = new AtomicBoolean(false);
    private AtomicBoolean isWednesdayButtonClicked = new AtomicBoolean(false);
    private AtomicBoolean isThursdayButtonClicked = new AtomicBoolean(false);
    private AtomicBoolean isFridayButtonClicked = new AtomicBoolean(false);
    private AtomicBoolean isSaturdayButtonClicked = new AtomicBoolean(false);
    private AtomicBoolean isSundayButtonClicked = new AtomicBoolean(false);

    private List<String> clickedTextViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_class);


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
        onceButton = findViewById(R.id.onceButton);
        repeatingButton = findViewById(R.id.repeatingButton);
        mondayButton = findViewById(R.id.mondayButton);
        tuesdayButton = findViewById(R.id.tuesdayButton);
        wednesdayButton = findViewById(R.id.wednesdayButton);
        thursdayButton = findViewById(R.id.thursdayButton);
        fridayButton = findViewById(R.id.fridayButton);
        saturdayButton = findViewById(R.id.saturdayButton);
        sundayButton = findViewById(R.id.sundayButton);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        onlineClassURLLabel = findViewById(R.id.onlineClassURLLabel);
        daysLabel = findViewById(R.id.daysLabel);
        dateLabel = findViewById(R.id.dateLabel);

        //Date Picker & Time Picker
        date = findViewById(R.id.datePicker);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);

        //Layouts
        roomBuildingLayout = findViewById(R.id.roomBuildingLayout);
        daysLayout = findViewById(R.id.daysLayout);

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        classID = getIntent().getStringExtra("classID");
        classesReference = db.getReference("Users").child(userID).child("Classes").child(classID);
        System.out.println(classID);

        // Populate fields with existing data in Firebase DB
        classMode = getIntent().getStringExtra("classMode");
        moduleName.setText(getIntent().getStringExtra("moduleName"));
        roomNum.setText(getIntent().getStringExtra("roomNumber"));
        building.setText(getIntent().getStringExtra("building"));
        lecturerName.setText(getIntent().getStringExtra("lecturerName"));
        lecturerEmail.setText(getIntent().getStringExtra("lecturerEmail"));
        onlineURL.setText(getIntent().getStringExtra("onlineClassURL"));
        occurence = getIntent().getStringExtra("occurence");
        dateText = getIntent().getStringExtra("date");
        ArrayList<String> daysList = getIntent().getStringArrayListExtra("days");
        startTimeText = getIntent().getStringExtra("startTime");
        endTimeText = getIntent().getStringExtra("endTime");
        System.out.println("Date: " + dateText);
        System.out.println("Days List: " + daysList);
        System.out.println("Occurrence: " + occurence);
        if (daysList != null) {
            for (String day : daysList) {
                System.out.println("Day: " + day);
            }
        }


        if (startTimeText != null && !startTimeText.isEmpty()) {
            String[] startTimeParts = startTimeText.split(":"); // Split the start time string (HH:mm)
            int startHour = Integer.parseInt(startTimeParts[0]);
            int startMinute = Integer.parseInt(startTimeParts[1]);

            // Set time in Start TimePicker
            startTime.setHour(startHour);
            startTime.setMinute(startMinute);
        }

        if (endTimeText != null && !endTimeText.isEmpty()) {
            String[] endTimeParts = endTimeText.split(":"); // Split the end time string (HH:mm)
            int endHour = Integer.parseInt(endTimeParts[0]);
            int endMinute = Integer.parseInt(endTimeParts[1]);

            // Set time in End TimePicker
            endTime.setHour(endHour);
            endTime.setMinute(endMinute);
        }

        //Default Display
        if (classMode.equals("In Person")){
            // In Person Button need to Switch to Light Teal background with White text color
            ViewCompat.setBackgroundTintList(
                    inPersonButton,
                    ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
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
                    ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
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

        if (occurence.equals("Once")){
            //Once Button need to Switch to Light Teal background with White text color
            ViewCompat.setBackgroundTintList(
                    onceButton,
                    ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
            );
            onceButton.setTextColor(Color.parseColor("#FFFFFF"));
            onceButton.setBackgroundResource(R.drawable.button_background);

            //Repeating Button need to Switch to Gray background with gray text color
            ViewCompat.setBackgroundTintList(
                    repeatingButton,
                    ColorStateList.valueOf(Color.parseColor("#333333"))
            );
            repeatingButton.setTextColor(Color.parseColor("#888888"));
            repeatingButton.setBackgroundResource(R.drawable.button_background);

            //Set the date from DB
            if (dateText != null && !dateText.isEmpty()) {
                String[] dateParts = dateText.split("/"); // Split the date string (dd/MM/yyyy)
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]) - 1; // Month is 0-based in DatePicker
                int year = Integer.parseInt(dateParts[2]);

                // Set date in DatePicker
                date.updateDate(year, month, day);
            }

            //Days inputs and labels MUST NOT be displayed
            daysLabel.setVisibility(View.GONE);
            daysLayout.setVisibility(View.GONE);

            //Date inputs and labels MUST be displayed
            date.setVisibility(View.VISIBLE);
            dateLabel.setVisibility(View.VISIBLE);
        } else{
            //Repeating Button need to Switch to Light Teal background with White text color
            ViewCompat.setBackgroundTintList(
                    repeatingButton,
                    ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
            );
            repeatingButton.setTextColor(Color.parseColor("#FFFFFF"));
            repeatingButton.setBackgroundResource(R.drawable.button_background);

            //Once Button need to Switch to Gray background with gray text color
            ViewCompat.setBackgroundTintList(
                    onceButton,
                    ColorStateList.valueOf(Color.parseColor("#333333"))
            );
            onceButton.setTextColor(Color.parseColor("#888888"));
            onceButton.setBackgroundResource(R.drawable.button_background);

            //Days inputs and labels MUST be displayed
            daysLayout.setVisibility(View.VISIBLE);
            daysLabel.setVisibility(View.VISIBLE);

            //Date inputs and labels MUST NOT be displayed
            date.setVisibility(View.GONE);
            dateLabel.setVisibility(View.GONE);

            // Initialize the buttons based on saved days
            for (String day : daysList) {
                switch (day) {
                    case "Mon":
                        isMondayButtonClicked.set(true);
                        ViewCompat.setBackgroundTintList(
                                mondayButton,
                                ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                        );
                        mondayButton.setTextColor(Color.parseColor("#FFFFFF"));
                        clickedTextViews.add(day);
                        break;
                    case "Tue":
                        isTuesdayButtonClicked.set(true);
                        ViewCompat.setBackgroundTintList(
                                tuesdayButton,
                                ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                        );
                        tuesdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                        clickedTextViews.add(day);
                        break;
                    case "Wed":
                        isWednesdayButtonClicked.set(true);
                        ViewCompat.setBackgroundTintList(
                                wednesdayButton,
                                ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                        );
                        wednesdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                        clickedTextViews.add(day);
                        break;
                    case "Thurs":
                        isThursdayButtonClicked.set(true);
                        ViewCompat.setBackgroundTintList(
                                thursdayButton,
                                ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                        );
                        thursdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                        clickedTextViews.add(day);
                        break;
                    case "Fri":
                        isFridayButtonClicked.set(true);
                        ViewCompat.setBackgroundTintList(
                                fridayButton,
                                ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                        );
                        fridayButton.setTextColor(Color.parseColor("#FFFFFF"));
                        clickedTextViews.add(day);
                        break;
                    case "Sat":
                        isSaturdayButtonClicked.set(true);
                        ViewCompat.setBackgroundTintList(
                                saturdayButton,
                                ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                        );
                        saturdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                        clickedTextViews.add(day);
                        break;
                    case "Sun":
                        isSundayButtonClicked.set(true);
                        ViewCompat.setBackgroundTintList(
                                sundayButton,
                                ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                        );
                        sundayButton.setTextColor(Color.parseColor("#FFFFFF"));
                        clickedTextViews.add(day);
                        break;
                }
            }
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
        List<String> currentClassMode = new ArrayList<>();

        // Handle Online button click
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentClassMode.contains(inPersonButton.getText().toString())) {
                    //currentClassMode.remove(inPersonButton.getText().toString());
                    currentClassMode.clear();
                    currentClassMode.add(onlineButton.getText().toString());
                    System.out.println(currentClassMode);
                }

                // Online Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        onlineButton,
                        ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
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

                if (currentClassMode.contains(onlineButton.getText().toString())) {

                    //currentClassMode.remove(onlineButton.getText().toString());
                    currentClassMode.clear();
                    currentClassMode.add(inPersonButton.getText().toString());
                    System.out.println(currentClassMode);
                }

                // In Person Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        inPersonButton,
                        ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
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

        // Create an empty list to hold clicked Days TextView values
        List<String> currentOccurMode = new ArrayList<>();

        // Handle Once button click
        onceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentOccurMode.contains(repeatingButton.getText().toString())) {

                    //currentOccurMode.remove(repeatingButton.getText().toString());
                    currentOccurMode.clear();
                    currentOccurMode.add(onceButton.getText().toString());
                    System.out.println(currentOccurMode);
                }

                //Once Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        onceButton,
                        ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                );
                onceButton.setTextColor(Color.parseColor("#FFFFFF"));
                onceButton.setBackgroundResource(R.drawable.button_background);

                //Repeating Button need to Switch to Gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        repeatingButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                repeatingButton.setTextColor(Color.parseColor("#888888"));
                repeatingButton.setBackgroundResource(R.drawable.button_background);

                //Days inputs and labels MUST NOT be displayed
                daysLabel.setVisibility(View.GONE);
                daysLayout.setVisibility(View.GONE);

                //Date inputs and labels MUST be displayed
                date.setVisibility(View.VISIBLE);
                dateLabel.setVisibility(View.VISIBLE);

            }
        });

        //Handle Repeating button click
        repeatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentOccurMode.contains(onceButton.getText().toString())) {
                    //currentOccurMode.remove(onceButton.getText().toString());
                    currentOccurMode.clear();
                    currentOccurMode.add(repeatingButton.getText().toString());
                    System.out.println(currentOccurMode);
                }

                //Repeating Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        repeatingButton,
                        ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                );
                repeatingButton.setTextColor(Color.parseColor("#FFFFFF"));
                repeatingButton.setBackgroundResource(R.drawable.button_background);

                //Once Button need to Switch to Gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        onceButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                onceButton.setTextColor(Color.parseColor("#888888"));
                onceButton.setBackgroundResource(R.drawable.button_background);

                //Days inputs and labels MUST be displayed
                daysLayout.setVisibility(View.VISIBLE);
                daysLabel.setVisibility(View.VISIBLE);

                //Date inputs and labels MUST NOT be displayed
                date.setVisibility(View.GONE);
                dateLabel.setVisibility(View.GONE);
            }
        });

        // Create an empty list to hold clicked Days TextView values
        //List<String> clickedTextViews = new ArrayList<>();

        // Ensuring the mondayButton switches colors when clicked on
        //AtomicBoolean isMondayButtonClicked = new AtomicBoolean(false);
        mondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isMondayButtonClicked.get()) {
                    isMondayButtonClicked.set(true);
                    clickedTextViews.add(mondayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Light Teal background with White text color
                    ViewCompat.setBackgroundTintList(
                            mondayButton,
                            ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                    );
                    mondayButton.setTextColor(Color.parseColor("#FFFFFF"));
                    mondayButton.setBackgroundResource(R.drawable.button_background);
                } else {
                    isMondayButtonClicked.set(false);
                    clickedTextViews.remove(mondayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Gray background with gray text color
                    ViewCompat.setBackgroundTintList(
                            mondayButton,
                            ColorStateList.valueOf(Color.parseColor("#333333"))
                    );
                    mondayButton.setTextColor(Color.parseColor("#888888"));
                    mondayButton.setBackgroundResource(R.drawable.button_background);
                }

            }
        });



        // Ensuring the tuesdayButton switches colors when clicked on
        //AtomicBoolean isTuesdayButtonClicked = new AtomicBoolean(false);
        tuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTuesdayButtonClicked.get()) {
                    isTuesdayButtonClicked.set(true);
                    clickedTextViews.add(tuesdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Light Teal background with White text color
                    ViewCompat.setBackgroundTintList(
                            tuesdayButton,
                            ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                    );
                    tuesdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                    tuesdayButton.setBackgroundResource(R.drawable.button_background);
                } else {
                    isTuesdayButtonClicked.set(false);
                    clickedTextViews.remove(tuesdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Gray background with gray text color
                    ViewCompat.setBackgroundTintList(
                            tuesdayButton,
                            ColorStateList.valueOf(Color.parseColor("#333333"))
                    );
                    tuesdayButton.setTextColor(Color.parseColor("#888888"));
                    tuesdayButton.setBackgroundResource(R.drawable.button_background);
                }

            }
        });

        // Ensuring the wednesdayButton switches colors when clicked on
        //AtomicBoolean isWednesdayButtonClicked = new AtomicBoolean(false);
        wednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWednesdayButtonClicked.get()) {
                    isWednesdayButtonClicked.set(true);
                    clickedTextViews.add(wednesdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Light Teal background with White text color
                    ViewCompat.setBackgroundTintList(
                            wednesdayButton,
                            ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                    );
                    wednesdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                    wednesdayButton.setBackgroundResource(R.drawable.button_background);
                } else {
                    isWednesdayButtonClicked.set(false);
                    clickedTextViews.remove(wednesdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Gray background with gray text color
                    ViewCompat.setBackgroundTintList(
                            wednesdayButton,
                            ColorStateList.valueOf(Color.parseColor("#333333"))
                    );
                    wednesdayButton.setTextColor(Color.parseColor("#888888"));
                    wednesdayButton.setBackgroundResource(R.drawable.button_background);
                }

            }
        });

        // Ensuring the thursdayButton switches colors when clicked on
        //AtomicBoolean isThursdayButtonClicked = new AtomicBoolean(false);
        thursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isThursdayButtonClicked.get()) {
                    isThursdayButtonClicked.set(true);
                    clickedTextViews.add(thursdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Light Teal background with White text color
                    ViewCompat.setBackgroundTintList(
                            thursdayButton,
                            ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                    );
                    thursdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                    thursdayButton.setBackgroundResource(R.drawable.button_background);
                } else {
                    isThursdayButtonClicked.set(false);
                    clickedTextViews.remove(thursdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Gray background with gray text color
                    ViewCompat.setBackgroundTintList(
                            thursdayButton,
                            ColorStateList.valueOf(Color.parseColor("#333333"))
                    );
                    thursdayButton.setTextColor(Color.parseColor("#888888"));
                    thursdayButton.setBackgroundResource(R.drawable.button_background);
                }

            }
        });

        // Ensuring the fridayButton switches colors when clicked on
        //AtomicBoolean isFridayButtonClicked = new AtomicBoolean(false);
        fridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFridayButtonClicked.get()) {
                    isFridayButtonClicked.set(true);
                    clickedTextViews.add(fridayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Light Teal background with White text color
                    ViewCompat.setBackgroundTintList(
                            fridayButton,
                            ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                    );
                    fridayButton.setTextColor(Color.parseColor("#FFFFFF"));
                    fridayButton.setBackgroundResource(R.drawable.button_background);
                } else {
                    isFridayButtonClicked.set(false);
                    clickedTextViews.remove(fridayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Gray background with gray text color
                    ViewCompat.setBackgroundTintList(
                            fridayButton,
                            ColorStateList.valueOf(Color.parseColor("#333333"))
                    );
                    fridayButton.setTextColor(Color.parseColor("#888888"));
                    fridayButton.setBackgroundResource(R.drawable.button_background);
                }

            }
        });

        // Ensuring the saturdayButton switches colors when clicked on
        //AtomicBoolean isSaturdayButtonClicked = new AtomicBoolean(false);
        saturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSaturdayButtonClicked.get()) {
                    isSaturdayButtonClicked.set(true);
                    clickedTextViews.add(saturdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Light Teal background with White text color
                    ViewCompat.setBackgroundTintList(
                            saturdayButton,
                            ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                    );
                    saturdayButton.setTextColor(Color.parseColor("#FFFFFF"));
                    saturdayButton.setBackgroundResource(R.drawable.button_background);
                } else {
                    isSaturdayButtonClicked.set(false);
                    clickedTextViews.remove(saturdayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Gray background with gray text color
                    ViewCompat.setBackgroundTintList(
                            saturdayButton,
                            ColorStateList.valueOf(Color.parseColor("#333333"))
                    );
                    saturdayButton.setTextColor(Color.parseColor("#888888"));
                    saturdayButton.setBackgroundResource(R.drawable.button_background);
                }

            }
        });

        // Ensuring the sundayButton switches colors when clicked on
        //AtomicBoolean isSundayButtonClicked = new AtomicBoolean(false);
        sundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSundayButtonClicked.get()) {
                    isSundayButtonClicked.set(true);
                    clickedTextViews.add(sundayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Light Teal background with White text color
                    ViewCompat.setBackgroundTintList(
                            sundayButton,
                            ColorStateList.valueOf(ContextCompat.getColor(UpdateClassActivity.this, R.color.light_teal))
                    );
                    sundayButton.setTextColor(Color.parseColor("#FFFFFF"));
                    sundayButton.setBackgroundResource(R.drawable.button_background);
                } else {
                    isSundayButtonClicked.set(false);
                    clickedTextViews.remove(sundayButton.getText().toString());
                    System.out.println(clickedTextViews);

                    //Monday Button need to Switch to Gray background with gray text color
                    ViewCompat.setBackgroundTintList(
                            sundayButton,
                            ColorStateList.valueOf(Color.parseColor("#333333"))
                    );
                    sundayButton.setTextColor(Color.parseColor("#888888"));
                    sundayButton.setBackgroundResource(R.drawable.button_background);
                }

            }
        });
    }
}