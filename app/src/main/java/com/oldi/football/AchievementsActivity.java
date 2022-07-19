package com.oldi.football;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class AchievementsActivity extends AppCompatActivity {
    private boolean swipeLR = true;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_more_achievements);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        View scroll = findViewById(R.id.more_achievements);
        scroll.setOnTouchListener(new OnSwipeTouchListener(scroll.getContext()) {
            public void onSwipeTop() {
                System.out.println("top");
            }

            public void onSwipeRight() {
                swipeLR = true;
                onBackPressed();
                System.out.println("right");
            }

            public void onSwipeLeft() {
                swipeLR = false;
                onBackPressed();
                System.out.println("left");
            }

            public void onSwipeBottom() {
                System.out.println("bot");
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        if (swipeLR) {
            overridePendingTransition(R.anim.slide_lr, R.anim.slide_lr_out);
        } else {
            overridePendingTransition(R.anim.slide_rl, R.anim.slide_rl_out);
        }
    }
}