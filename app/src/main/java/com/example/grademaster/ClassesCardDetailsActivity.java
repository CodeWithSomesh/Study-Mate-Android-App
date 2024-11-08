package com.example.grademaster;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ClassesCardDetailsActivity extends AppCompatActivity {
    private TextView moduleNameText, classModeText, locationText, timeText, lecturerNameText,
            lecturerEmailText, onlineClassURLText, classOccurenceText, onceOrRepeatingLabel, occurenceDays;

    private LinearLayout onlineClassLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_details);


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

        // Check if daysList is not null and then join the elements for display
        String daysString = daysList != null ? String.join(", ", daysList) : "No days available";

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


    }
}