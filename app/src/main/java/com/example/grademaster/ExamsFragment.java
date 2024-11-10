package com.example.grademaster;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ExamsFragment extends Fragment {

    private EditText moduleName, roomNum, building, lecturerName, lecturerEmail, onlineURL;
    private TextView inPersonButton, onlineButton, cancelButton, saveButton, onlineClassURLLabel,
            dateLabel;
    private DatePicker date;
    private TimePicker startTime, endTime;
    private LinearLayout roomBuildingLayout;
    private FirebaseDatabase db;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exams, container, false);

        //Initialize UI Components
        //EditTexts
        moduleName = view.findViewById(R.id.inputModuleName);
        roomNum = view.findViewById(R.id.inputRoom);
        building = view.findViewById(R.id.inputBuilding);
        lecturerName = view.findViewById(R.id.inputTeacherName);
        lecturerEmail = view.findViewById(R.id.inputTeacherEmail);
        onlineURL = view.findViewById(R.id.inputClassURL);

        //TextView
        inPersonButton = view.findViewById(R.id.inPersonButton);
        onlineButton = view.findViewById(R.id.onlineButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        saveButton = view.findViewById(R.id.saveButton);
        onlineClassURLLabel = view.findViewById(R.id.onlineClassURLLabel);
        dateLabel = view.findViewById(R.id.dateLabel);

        //Date Picker & Time Picker
        date = view.findViewById(R.id.datePicker);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);

        //Layouts
        roomBuildingLayout = view.findViewById(R.id.roomBuildingLayout);

        //Default Display
        //Online Class URL input and labels shall not be displayed
        onlineClassURLLabel.setVisibility(View.GONE);
        onlineURL.setVisibility(View.GONE);

        // Handle Cancel button click
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        // Create an empty list to hold clicked ClassMode TextView values
        List<String> currentExamMode = new ArrayList<>();
        currentExamMode.add(inPersonButton.getText().toString());

        // Handle Online button click
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //currentExamMode.remove(inPersonButton.getText().toString());
                currentExamMode.clear();
                currentExamMode.add(onlineButton.getText().toString());
                System.out.println(currentExamMode);

                // Online Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        onlineButton,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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

        saveButton.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(getActivity(), "Please Enter Your Module Name", Toast.LENGTH_LONG).show();
                        moduleName.setError("Module Name is required");
                        moduleName.requestFocus();
                    } else if (TextUtils.isEmpty(onlineExamURLText)) {
                        Toast.makeText(getActivity(), "Please Enter Your Online Class URL", Toast.LENGTH_LONG).show();
                        onlineURL.setError("Online Class URL is required");
                        onlineURL.requestFocus();
                    } else {
                        roomNumberText = "None";
                        buildingText = "None";

                        //Add Exam Function
                        addExam(currentExamModeText, moduleNameText, roomNumberText,  buildingText,
                                lecturerNameText, lecturerEmailText,
                                onlineExamURLText, dateString,  startTimeString,  endTimeString,
                                userID,  userEmail);
                    }
                } else if (Objects.equals(currentExamModeText, "In Person")){
                    if (TextUtils.isEmpty(moduleNameText)) {
                        Toast.makeText(getActivity(), "Please Enter Your Module Name", Toast.LENGTH_LONG).show();
                        moduleName.setError("Module Name is required");
                        moduleName.requestFocus();
                    } else if (TextUtils.isEmpty(roomNumberText)) {
                        Toast.makeText(getActivity(), "Please Enter Your Room Number", Toast.LENGTH_LONG).show();
                        roomNum.setError("Room Number is required");
                        roomNum.requestFocus();
                    } else if (TextUtils.isEmpty(buildingText)) {
                        Toast.makeText(getActivity(), "Please Enter The Building", Toast.LENGTH_LONG).show();
                        building.setError("Building is required");
                        building.requestFocus();
                    } else {
                        onlineExamURLText = "None";

                        //Add Exam Function
                        //Add Exam Function
                        addExam(currentExamModeText, moduleNameText, roomNumberText,  buildingText,
                                lecturerNameText, lecturerEmailText,
                                onlineExamURLText, dateString,  startTimeString,  endTimeString,
                                userID,  userEmail);
                    }
                }

            }

            private void addExam(String currentExamModeText, String moduleNameText,
                                 String roomNumberText, String buildingText, String lecturerNameText,
                                 String lecturerEmailText, String onlineExamURLText,
                                 String dateString, String startTimeString, String endTimeString,
                                 String userID, String userEmail)
            {

                // Initialize Firebase Database
                db = FirebaseDatabase.getInstance(); // Initialize Firebase Database
                reference = db.getReference("Users").child(userID).child("Exams"); // Get the reference to the user's sub-collection of classes

                // Create a unique ID for the class
                String examID = reference.push().getKey();
                assert examID != null;

                // Create a Classes object
                Exams exam = new Exams(examID, currentExamModeText, moduleNameText, roomNumberText,
                        buildingText,lecturerNameText, lecturerEmailText, onlineExamURLText, dateString, startTimeString, endTimeString,
                        userID, userEmail);

                // Save data to the database
                reference.child(examID).setValue(exam).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Use getActivity() or getContext() to provide a valid context
                            Toast.makeText(getActivity(), "Exam Added Successfully", Toast.LENGTH_LONG).show();
                            // Handle login navigation
                            Intent intent = new Intent(getActivity(), HomeFragment.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Failed to Add Exam", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        });


        return view;
    }
}