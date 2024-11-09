package com.example.grademaster;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ManageTasksActivity extends AppCompatActivity {
    private TabLayout tabLayout;    // Tab layout to display tabs for the ViewPager
    private ViewPager viewPager;    // ViewPager to display different fragments based on the selected tab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_tasks);


        // Initialize TabLayout and ViewPager
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        ImageView backButton = findViewById(R.id.backButton);

        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity using the OnBackPressedDispatcher
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        // Link TabLayout with the ViewPager, so tab selections switch pages in the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Initialize the ViewPager adapter with fragment manager and behavior setting
        ViewPageAdapter vpAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // Add fragments and their titles to the adapter
        vpAdapter.addFragment(new ManageClassesFragment(), "Classes");
        vpAdapter.addFragment(new ExamsFragment(), "Exams");
        vpAdapter.addFragment(new AssignmentsFragment(), "Assignments");

        // Set the adapter for the ViewPager
        viewPager.setAdapter(vpAdapter);
    }
}