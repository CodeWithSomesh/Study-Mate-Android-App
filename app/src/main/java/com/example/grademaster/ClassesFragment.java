package com.example.grademaster;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import android.content.res.ColorStateList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ClassesFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ClassesFragment extends Fragment {

    private EditText moduleName, roomNum, building, lecturerName, lecturerEmail, onlineURL;
    private TextView inPersonButton, onlineButton, onceButton, repeatingButton, mondayButton,
            tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton,
            sundayButton, cancelButton, saveButton, onlineClassURLLabel, daysLabel;
    private DatePicker date;
    private TimePicker startTime, endTime;
    private LinearLayout roomBuildingLayout, daysLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_classes, container, false);

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
        onceButton = view.findViewById(R.id.onceButton);
        repeatingButton = view.findViewById(R.id.repeatingButton);
        mondayButton = view.findViewById(R.id.mondayButton);
        tuesdayButton = view.findViewById(R.id.tuesdayButton);
        wednesdayButton = view.findViewById(R.id.wednesdayButton);
        thursdayButton = view.findViewById(R.id.thursdayButton);
        fridayButton = view.findViewById(R.id.fridayButton);
        saturdayButton = view.findViewById(R.id.saturdayButton);
        sundayButton = view.findViewById(R.id.sundayButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        saveButton = view.findViewById(R.id.saveButton);
        onlineClassURLLabel = view.findViewById(R.id.onlineClassURLLabel);
        daysLabel = view.findViewById(R.id.daysLabel);

        //Date Picker & Time Picker
        date = view.findViewById(R.id.datePicker);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);

        //Layouts
        roomBuildingLayout = view.findViewById(R.id.roomBuildingLayout);
        daysLayout = view.findViewById(R.id.daysLayout);

        //Default Display
        //Online Class URL input and labels shall not be displayed
        onlineClassURLLabel.setVisibility(View.GONE);
        onlineURL.setVisibility(View.GONE);
        //Days inputs and labels MUST NOT be displayed
        daysLabel.setVisibility(View.GONE);
        daysLayout.setVisibility(View.GONE);

        // Handle Cancel button click
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

         // Handle Online button click
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        // Handle Once button click
        onceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Once Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        onceButton,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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

            }
        });

        //Handle Repeating button click
        repeatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Repeating Button need to Switch to Light Teal background with White text color
                ViewCompat.setBackgroundTintList(
                        repeatingButton,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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

            }
        });

        // Create an empty list to hold clicked TextView values
        List<String> clickedTextViews = new ArrayList<>();

        // Ensuring the mondayButton switches colors when clicked on
        AtomicBoolean isMondayButtonClicked = new AtomicBoolean(false);
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
                            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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
        AtomicBoolean isTuesdayButtonClicked = new AtomicBoolean(false);
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
                            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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
        AtomicBoolean isWednesdayButtonClicked = new AtomicBoolean(false);
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
                            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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
        AtomicBoolean isThursdayButtonClicked = new AtomicBoolean(false);
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
                            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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
        AtomicBoolean isFridayButtonClicked = new AtomicBoolean(false);
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
                            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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
        AtomicBoolean isSaturdayButtonClicked = new AtomicBoolean(false);
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
                            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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
        AtomicBoolean isSundayButtonClicked = new AtomicBoolean(false);
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
                            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
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





        return view;
    }
}