package com.example.grademaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;


public class ActivityFragment extends Fragment {

    private TabLayout tabLayout;    // Tab layout to display tabs for the ViewPager
    private ViewPager viewPager;    // ViewPager to display different fragments based on the selected tab

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        // Initialize TabLayout and ViewPager
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.view_pager);
        ImageView backButton = view.findViewById(R.id.backButton);

        // Link TabLayout with the ViewPager, so tab selections switch pages in the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Initialize the ViewPager adapter with fragment manager and behavior setting
        ViewPageAdapter vpAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // Add fragments and their titles to the adapter
        vpAdapter.addFragment(new ClassesFragment(), "Classes");
        vpAdapter.addFragment(new ExamsFragment(), "Exams");
        vpAdapter.addFragment(new AssignmentsFragment(), "Assignments");

        // Set the adapter for the ViewPager
        viewPager.setAdapter(vpAdapter);


        return view;
    }
}