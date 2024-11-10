package com.example.grademaster;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

public class ExamsFragment extends Fragment {

    private EditText moduleName, roomNum, building, onlineURL;
    private TextView inPersonButton, onlineButton, cancelButton, saveButton, onlineClassURLLabel,
            dateLabel;
    private DatePicker date;
    private TimePicker startTime, endTime;
    private LinearLayout roomBuildingLayout;

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
        List<String> currentClassMode = new ArrayList<>();
        currentClassMode.add(inPersonButton.getText().toString());

        // Handle Online button click
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //currentClassMode.remove(inPersonButton.getText().toString());
                currentClassMode.clear();
                currentClassMode.add(onlineButton.getText().toString());
                System.out.println(currentClassMode);

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

                if (currentClassMode.contains(onlineButton.getText().toString())) {

                    //currentClassMode.remove(onlineButton.getText().toString());
                    currentClassMode.clear();
                    currentClassMode.add(inPersonButton.getText().toString());
                    System.out.println(currentClassMode);
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


        return view;
    }
}