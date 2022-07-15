package com.oldi.football;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oldi.football.ui.achievements.AchievementFragment;
import com.oldi.football.ui.news.NewsFragment;
import com.oldi.football.ui.profile.ProfileFragment;
import com.oldi.football.ui.schedule.ScheduleFragment;
import com.oldi.football.ui.stats.ComparisonFragment;
import com.oldi.football.ui.stats.StatsFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.oldi.football.ui.main.SectionsPagerAdapter;

public class TabbedActivity extends AppCompatActivity {

    public static final Fragment[] tabsFragments = {new StatsFragment(), new AchievementFragment(), new ScheduleFragment(), new NewsFragment(), new ProfileFragment()};
    private static final String[] titles = {"Статистика","Достижения","Расписание","Новости","Профиль"};

    private void switchActivitiesWithData() {
        Intent switchActivityIntent = new Intent(this, AchievementsActivity.class);
        switchActivityIntent.putExtra("message", "From: " + TabbedActivity.class.getSimpleName());
        startActivity(switchActivityIntent);
    }

    private void switchActivitiesWithData2() {
        Intent switchActivityIntent = new Intent(this, ComparisonFragment.class);
        switchActivityIntent.putExtra("message", "From: " + TabbedActivity.class.getSimpleName());
        startActivity(switchActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        com.oldi.football.databinding.ActivityTabbedBinding binding = com.oldi.football.databinding.ActivityTabbedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

//        tabLayout.addTab(tabLayout.newTab().setText("Статистика").setIcon(R.drawable.ic_stats));
//        tabLayout.addTab(tabLayout.newTab().setText("Достижения").setIcon(R.drawable.ic_medal));
//        tabLayout.addTab(tabLayout.newTab().setText("Расписание").setIcon(R.drawable.ic_schedule));
//        tabLayout.addTab(tabLayout.newTab().setText("Новости").setIcon(R.drawable.ic_schedule));
//        tabLayout.addTab(tabLayout.newTab().setText("Профиль").setIcon(R.drawable.ic_profile));

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_stats));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_medal));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_schedule));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_news));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TextView title = findViewById(R.id.textView4);
        title.setText(titles[0]);

        ImageButton info = findViewById(R.id.infoButton);
        info.setOnClickListener(e-> switchActivitiesWithData());

        ImageButton inf = findViewById(R.id.infoButton2);
        inf.setOnClickListener(e-> switchActivitiesWithData2());

        final SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title.setText(titles[tab.getPosition()]);
                if (tab.getPosition() == 1) {
                    info.setVisibility(View.VISIBLE);
                    inf.setVisibility(View.INVISIBLE);
                } else {
                    if (tab.getPosition() == 0) {
                        inf.setVisibility(View.VISIBLE);
                        info.setVisibility(View.INVISIBLE);
                    } else {
                        info.setVisibility(View.INVISIBLE);
                        inf.setVisibility(View.INVISIBLE);
                    }
                }
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