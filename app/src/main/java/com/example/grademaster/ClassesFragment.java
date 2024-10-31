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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
            sundayButton, cancelButton, saveButton;
    private DatePicker date;
    private TimePicker startTime, endTime;

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

        //Date Picker & Time Picker
        date = view.findViewById(R.id.datePicker);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);

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

                // Switch to dark gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        onlineButton,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
                );
                onlineButton.setTextColor(Color.parseColor("#FFFFFF"));
                onlineButton.setBackgroundResource(R.drawable.button_background);

                ViewCompat.setBackgroundTintList(
                        inPersonButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                inPersonButton.setTextColor(Color.parseColor("#888888"));
                inPersonButton.setBackgroundResource(R.drawable.button_background);
            }
        });

        // Handle In Person button click
        inPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Switch to dark gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        inPersonButton,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
                );
                inPersonButton.setTextColor(Color.parseColor("#FFFFFF"));
                inPersonButton.setBackgroundResource(R.drawable.button_background);

                ViewCompat.setBackgroundTintList(
                        onlineButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                onlineButton.setTextColor(Color.parseColor("#888888"));
                onlineButton.setBackgroundResource(R.drawable.button_background);

            }
        });

        // Handle Once button click
        onceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Switch to dark gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        onceButton,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
                );
                onceButton.setTextColor(Color.parseColor("#FFFFFF"));
                onceButton.setBackgroundResource(R.drawable.button_background);

                ViewCompat.setBackgroundTintList(
                        repeatingButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                repeatingButton.setTextColor(Color.parseColor("#888888"));
                repeatingButton.setBackgroundResource(R.drawable.button_background);

            }
        });

        // Handle Repeating button click
        repeatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Switch to dark gray background with gray text color
                ViewCompat.setBackgroundTintList(
                        repeatingButton,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_teal))
                );
                repeatingButton.setTextColor(Color.parseColor("#FFFFFF"));
                repeatingButton.setBackgroundResource(R.drawable.button_background);

                ViewCompat.setBackgroundTintList(
                        onceButton,
                        ColorStateList.valueOf(Color.parseColor("#333333"))
                );
                onceButton.setTextColor(Color.parseColor("#888888"));
                onceButton.setBackgroundResource(R.drawable.button_background);

            }
        });



        return view;
    }
}