package com.oldi.football.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.oldi.football.TabbedActivity;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    int totalTabs;

    public SectionsPagerAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TabbedActivity.tabsFragments[0];
            case 1:
                return TabbedActivity.tabsFragments[1];
            case 2:
                return TabbedActivity.tabsFragments[2];
            default:
                return TabbedActivity.tabsFragments[3];
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}