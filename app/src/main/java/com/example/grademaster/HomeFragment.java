package com.example.grademaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;


public class HomeFragment extends Fragment {

    private TextView greetingMessage,username, date, classCount, examsCount, assignmentsCount, noActivityMessage;
    private FirebaseDatabase db;
    private DatabaseReference classesReference, usersReference;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<Classes> classesList;
    private ImageView gradIcon;
    private LinearLayout quick_actions_layout;
    private GifImageView greetingIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Get User ID
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Integer classCountFromDBRef = 0;

        //Initialize UI Components
        greetingMessage = view.findViewById(R.id.greetingMessage);
        username = view.findViewById(R.id.username);
        date  = view.findViewById(R.id.date);
        classCount = view.findViewById(R.id.classCount);
        examsCount = view.findViewById(R.id.examsCount);
        assignmentsCount = view.findViewById(R.id.assignmentsCount);
        greetingIcon = view.findViewById(R.id.greetingIcon);
        recyclerView = view.findViewById(R.id.activitiesCardsListRecyclerView);
        gradIcon = view.findViewById(R.id.gradIcon);
        noActivityMessage = view.findViewById(R.id.noActivityMessage);
        quick_actions_layout = view.findViewById(R.id.quick_actions_layout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        classesList = new ArrayList<>();
        myAdapter = new MyAdapter(getContext(), classesList);
        recyclerView.setAdapter(myAdapter);

        // Initialize Firebase Database
        db = FirebaseDatabase.getInstance();
        classesReference = db.getReference("Users").child(userID).child("Classes");
        usersReference = db.getReference("Users").child(userID);


        // Get the current date and format it
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
        String formattedDate = dateFormat.format(currentDate);
        date.setText(formattedDate);

        // Get today's date in "DD/MM/YYYY" format
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat2.format(new Date());

        // Get today's day in "EEE" format (abbreviated day of the week)
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
        String todayDay = dayFormat.format(new Date());

        // Get the current time and format it
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 5 && hourOfDay < 12) {
            greetingMessage.setText("Good Morning");
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            greetingMessage.setText("Good Afternoon");
        } else if (hourOfDay >= 17 && hourOfDay < 20) {
            greetingMessage.setText("Good Evening");
        } else {
            greetingMessage.setText("Good Night");
            greetingIcon.setImageResource(R.drawable.night_gif);
        }

        classesReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classesList.clear(); // Clear the list to avoid duplicate entries

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Classes classes = dataSnapshot.getValue(Classes.class);
                    //classesList.add(classes);
                    //classesList.add(0, classes); // Add at the beginning of the list

                    if (classes.getDate().equals(todayDate) || classes.getDays().contains(todayDay)) {
                        classesList.add(0, classes); // Add only today's classes
                    }

                }

                // Sort classesList by start time in ascending order
                Collections.sort(classesList, (class1, class2) -> class1.getStartTime().compareTo(class2.getStartTime()));

                // Update class count and notify adapter
                classCount.setText(String.valueOf(classesList.size()));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fullName = dataSnapshot.child("fullName").getValue(String.class);
                if (fullName != null && !fullName.isEmpty()) {
                    fullName = fullName.substring(0, 1).toUpperCase() + fullName.substring(1).toLowerCase();
                }
                System.out.println("User's Full Name: " + fullName);
                username.setText(fullName); //Set the username from DB
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Error retrieving data: " + databaseError.getMessage());
            }
        });

        classesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long classCountFromDB = dataSnapshot.getChildrenCount();
                //classCountFromDBRef = String.valueOf(classCountFromDB);
                //classCount.setText(String.valueOf(classCountFromDB));

                if (classCountFromDB > 0){
                    gradIcon.setVisibility(View.GONE);
                    noActivityMessage.setVisibility(View.GONE);
                    quick_actions_layout.setVisibility(View.GONE);
                } else {
                    gradIcon.setVisibility(View.VISIBLE);
                    noActivityMessage.setVisibility(View.VISIBLE);
                    quick_actions_layout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Error retrieving data: " + databaseError.getMessage());
            }
        });




        return view;
    }

}