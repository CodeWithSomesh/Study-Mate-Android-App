package com.example.grademaster;

import android.annotation.SuppressLint;
import android.content.Context;
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

    private TextView greetingMessage, username, date, classCount, examsCount, assignmentsCount, noActivityMessage;
    private FirebaseDatabase db;
    private DatabaseReference classesReference, examsReference, usersReference;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<Object> combinedList;
    private ArrayList<Exams> examsList;
    private ArrayList<Classes> classesList;
    private ImageView gradIcon;
    private LinearLayout quick_actions_layout;
    private GifImageView greetingIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize UI Components
        greetingMessage = view.findViewById(R.id.greetingMessage);
        username = view.findViewById(R.id.username);
        date = view.findViewById(R.id.date);
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

        combinedList = new ArrayList<>();
        classesList = new ArrayList<>();
        examsList = new ArrayList<>();
        myAdapter = new MyAdapter(getContext(), combinedList, classesList, examsList);
        recyclerView.setAdapter(myAdapter);

        // Get the current date and format it
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
        String formattedDate = dateFormat.format(currentDate);
        date.setText(formattedDate);

        // Get today's date and day
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat2.format(new Date());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
        String todayDay = dayFormat.format(new Date());

        // Greet user based on time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        setGreetingMessage(hourOfDay);

        // Initialize Firebase Database
        db = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        classesReference = db.getReference("Users").child(userID).child("Classes");
        examsReference = db.getReference("Users").child(userID).child("Exams");
        usersReference = db.getReference("Users").child(userID);

        fetchClasses(todayDate, todayDay);
        fetchExams(todayDate);
        fetchUserData(userID);

        return view;
    }

    private void setGreetingMessage(int hourOfDay) {
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
    }

    private void fetchClasses(String todayDate, String todayDay) {
        classesReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    long classCountFromDB = dataSnapshot.getChildrenCount();
                    //classCountFromDBRef = String.valueOf(classCountFromDB);
                    //classCount.setText(String.valueOf(classCountFromDB));
                    Classes classes = dataSnapshot.getValue(Classes.class);

                    //Dynamic Home Page depending whether there are activities or not
                    if (classCountFromDB > 0){
                        gradIcon.setVisibility(View.GONE);
                        noActivityMessage.setVisibility(View.GONE);
                        quick_actions_layout.setVisibility(View.GONE);
                    } else {
                        gradIcon.setVisibility(View.VISIBLE);
                        noActivityMessage.setVisibility(View.VISIBLE);
                        quick_actions_layout.setVisibility(View.VISIBLE);
                    }

                    //Display only today activities
                    if (classes.getDate().equals(todayDate) || classes.getDays().contains(todayDay)) {
                        classesList.add(classes);
                    }
                }
                Collections.sort(classesList, (class1, class2) -> class1.getStartTime().compareTo(class2.getStartTime()));
                classCount.setText(String.valueOf(classesList.size()));
                updateCombinedList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error fetching classes: " + error.getMessage());
            }
        });
    }

    private void fetchExams(String todayDate) {
        examsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                examsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Exams exams = dataSnapshot.getValue(Exams.class);
                    if (exams.getDate().equals(todayDate)) {
                        examsList.add(exams);
                    }
                }
                examsCount.setText(String.valueOf(examsList.size()));
                updateCombinedList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error fetching exams: " + error.getMessage());
            }
        });
    }

    private void fetchUserData(String userID) {
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fullName = dataSnapshot.child("fullName").getValue(String.class);
                if (fullName != null && !fullName.isEmpty()) {
                    username.setText(fullName.substring(0, 1).toUpperCase() + fullName.substring(1).toLowerCase());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Error retrieving user data: " + databaseError.getMessage());
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCombinedList() {
        combinedList.clear();
        combinedList.addAll(classesList);
        combinedList.addAll(examsList);
        myAdapter.notifyDataSetChanged();
    }
}
