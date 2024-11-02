package com.example.grademaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPageAdapter extends FragmentPagerAdapter {

    // List to store fragments to display in the ViewPager
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    // List to store titles of the fragments (used as tab titles)
    private final ArrayList<String> fragmentTitle = new ArrayList<>();

    // Constructor that takes a FragmentManager and behavior setting for the adapter
    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Returns the fragment at the specified position in the ViewPager
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        // Returns the number of fragments in the list
        return fragmentArrayList.size();
    }

    // Method to add a fragment and its title to the lists
    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment);  // Adds the fragment to the fragment list
        fragmentTitle.add(title);         // Adds the title to the title list
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Returns the title of the fragment at the specified position (used for tab labels)
        return fragmentTitle.get(position);
    }
}
