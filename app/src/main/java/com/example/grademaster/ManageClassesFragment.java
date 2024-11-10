package com.example.grademaster;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageClassesFragment extends Fragment {

    private FirebaseDatabase db;
    private DatabaseReference classesReference;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<Object> combinedList;
    private ArrayList<Classes> classesList;
    private ArrayList<Exams> examsList;
    private ArrayList<String> moduleNames;
    private Spinner mySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_classes, container, false);

        // Initialize UI Components
        recyclerView = view.findViewById(R.id.activitiesCardsListRecyclerView);
        mySpinner = view.findViewById(R.id.my_spinner);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        combinedList = new ArrayList<>();
        classesList = new ArrayList<>();
        examsList = new ArrayList<>();
        combinedList.addAll(classesList);
        combinedList.addAll(examsList);
        moduleNames = new ArrayList<>();
        myAdapter = new MyAdapter(getContext(), combinedList, classesList, examsList);
        recyclerView.setAdapter(myAdapter);

        // Get User ID
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Initialize Firebase Database reference
        db = FirebaseDatabase.getInstance();
        classesReference = db.getReference("Users").child(userID).child("Classes");

        // Retrieve classes and module names from Firebase
        classesReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classesList.clear();
                moduleNames.clear();
                moduleNames.add("All Class Modules");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Classes classes = dataSnapshot.getValue(Classes.class);
                    if (classes != null) {
                        classesList.add(classes);
                        if (classes.getModuleName() != null) {
                            moduleNames.add(classes.getModuleName());
                        }
                    }
                }

                // Ensure the combinedList is updated
                combinedList.clear();
                combinedList.addAll(classesList); // Add updated classesList to combinedList

                // Notify the adapter of the new data
                myAdapter.updateClassesList(new ArrayList<>(classesList)); // Pass a copy of combinedList to avoid direct reference
                myAdapter.classesOriginalList = new ArrayList<>(classesList); // Reset the original list to match new data

                // Update Spinner adapter with the module names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, moduleNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if necessary
            }
        });

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedModule = parent.getItemAtPosition(position).toString();
                myAdapter.filterClassesByModuleName(selectedModule);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: handle no item selected
            }
        });

        return view;
    }

}
