package com.example.football.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.football.R;
import com.example.football.ui.acheivments.AcheivmentFragment;
import com.example.football.ui.profile.ProfileFragment;
import com.example.football.ui.schedule.ScheduleFragment;
import com.example.football.ui.stats.StatsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StatsFragment homeFragment = new StatsFragment();
                return homeFragment;
            case 1:
                AcheivmentFragment sportFragment = new AcheivmentFragment();
                return sportFragment;
            case 2:
                ScheduleFragment movieFragment = new ScheduleFragment();
                return movieFragment;
            case 3:
                ProfileFragment movieFragmnt = new ProfileFragment();
                return movieFragmnt;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}