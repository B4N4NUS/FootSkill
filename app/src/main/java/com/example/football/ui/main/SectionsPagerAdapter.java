package com.example.football.ui.main;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.football.TabbedActivity;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    int totalTabs;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TabbedActivity.tabsFragments[0];
            case 1:
                return TabbedActivity.tabsFragments[1];
            case 2:
                return TabbedActivity.tabsFragments[2];
            case 3:
                return TabbedActivity.tabsFragments[3];
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}