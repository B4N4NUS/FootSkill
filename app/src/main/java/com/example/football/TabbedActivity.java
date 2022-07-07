package com.example.football;

import android.os.Bundle;

import com.example.football.ui.achievements.AchievementFragment;
import com.example.football.ui.profile.ProfileFragment;
import com.example.football.ui.schedule.ScheduleFragment;
import com.example.football.ui.stats.StatsFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.football.ui.main.SectionsPagerAdapter;
import com.example.football.databinding.ActivityTabbedBinding;

public class TabbedActivity extends AppCompatActivity {

    public static final Fragment[] tabsFragments = {new StatsFragment(), new AchievementFragment(), new ScheduleFragment(), new ProfileFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.football.databinding.ActivityTabbedBinding binding = ActivityTabbedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Статистика").setIcon(R.drawable.ic_stats));
        tabLayout.addTab(tabLayout.newTab().setText("Достижения").setIcon(R.drawable.ic_medal));
        tabLayout.addTab(tabLayout.newTab().setText("Расписание").setIcon(R.drawable.ic_schedule));
        tabLayout.addTab(tabLayout.newTab().setText("Профиль").setIcon(R.drawable.ic_schedule));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final SectionsPagerAdapter adapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), 4);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}