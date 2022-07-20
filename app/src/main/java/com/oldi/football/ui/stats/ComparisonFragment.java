package com.oldi.football.ui.stats;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.oldi.football.Connection;
import com.oldi.football.OnSwipeTouchListener;
import com.oldi.football.R;

public class ComparisonFragment extends AppCompatActivity {
    private boolean swipeLR = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparison);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);



        LinearLayout lay = findViewById(R.id.lay_comp);
        ComparisonPart strength = findViewById(R.id.strength);
        ComparisonPart up = findViewById(R.id.up);
        ComparisonPart side = findViewById(R.id.side);
        ComparisonPart reaction = findViewById(R.id.reaction);
        ComparisonPart met_cold = findViewById(R.id.met_cold);
        ComparisonPart met_warn = findViewById(R.id.met_warm);

        ComparisonPart[] comps = new ComparisonPart[]{strength, up, side, reaction, met_cold, met_warn};

//        strength.SetData(R.drawable.boner, stats[0][2],stats[1][2],stats[2][2]);
//        up.SetData(R.drawable.up, stats[0][4],stats[1][4],stats[2][4]);
//        side.SetData(R.drawable.up,stats[0][10],stats[1][10],stats[2][10]);
//        reaction.SetData(R.drawable.clock,stats[0][3],stats[1][3],stats[2][3]);
//        met_cold.SetData(R.drawable.speed,stats[0][1],stats[1][1],stats[2][1]);
//        met_warn.SetData(R.drawable.speed,stats[0][9],stats[1][9],stats[2][9]);

        Spinner spin = findViewById(R.id.years);
        SpinAdapter adapter = new SpinAdapter(getBaseContext(),  android.R.layout.simple_spinner_item, Connection.getYears(), comps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        View scroll = findViewById(R.id.scroll_comp);
        scroll.setOnTouchListener(new OnSwipeTouchListener(scroll.getContext()) {
            public void onSwipeTop() {
                super.onSwipeTop();
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
                super.onSwipeBottom();
                System.out.println("bot");
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        if (android.os.Build.VERSION.SDK_INT > 29) {
            if (swipeLR) {
                overridePendingTransition(R.anim.slide_lr, R.anim.slide_lr_out);
            } else {
                overridePendingTransition(R.anim.slide_rl, R.anim.slide_rl_out);
            }
        }
    }
}
