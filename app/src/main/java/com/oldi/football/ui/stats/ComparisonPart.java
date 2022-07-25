package com.oldi.football.ui.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.oldi.football.R;

public class ComparisonPart extends RelativeLayout {
    private TextView your, aver, best, description;
    private ImageView image;
    private final Context context;

    public ComparisonPart(Context context) {
        super(context);
        this.context = context;
        initControl(context);
    }

    public ComparisonPart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context);
    }

    public ComparisonPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initControl(context);
    }

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.part_comp, this);

        your = findViewById(R.id.your_best_score);
        aver = findViewById(R.id.aver_score);
        best = findViewById(R.id.tot_best_score);
        description = findViewById(R.id.desk);
        image = findViewById(R.id.skill);
    }

    @SuppressLint("SetTextI18n")
    public void SetData(String name, int draw, String you, String ave, String bes, String postfix, boolean highest) {
        your.setText(you + " " + postfix);
        aver.setText(ave + " " + postfix);
        best.setText(bes + " " + postfix);

        String ayou = you.replace(",",".");
        String aave = ave.replace(",",".");
        String abes = bes.replace(",",".");

        MaterialButtonToggleGroup group = findViewById(R.id.group_selectable);
        group.setVisibility(INVISIBLE);

        if (highest) {
            if (Float.parseFloat(ayou) > Float.parseFloat(abes)) {
                your.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                best.setTextColor(aver.getTextColors());
            } else {
                best.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                your.setTextColor(aver.getTextColors());
            }
        } else {
            if (Float.parseFloat(ayou) > Float.parseFloat(abes)) {
                best.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                your.setTextColor(aver.getTextColors());
            } else {
                your.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                best.setTextColor(aver.getTextColors());
            }
        }

        image.setImageResource(draw);
        description.setText(name);
    }


    @SuppressLint("SetTextI18n")
    public void SetData(String name, int draw,String you, String ave, String bes, String firstHeader, boolean highest, String you2, String ave2, String bes2, String secondHeader, boolean highest2, String h1, String h2) {
        MaterialButton firstButton = findViewById(R.id.first_selectable);
        MaterialButton secondButton = findViewById(R.id.second_selectable);
        MaterialButtonToggleGroup group = findViewById(R.id.group_selectable);
        group.setVisibility(VISIBLE);

        String ayou = you.replace(",",".");
        String aave = ave.replace(",",".");
        String abes = bes.replace(",",".");
        String ayou2 = you2.replace(",",".");
        String aave2 = ave2.replace(",",".");
        String abes2 = bes2.replace(",",".");

        firstButton.setOnClickListener(e -> {
            System.out.println("FIRST");
            your.setText(you + " " + firstHeader);
            aver.setText(ave + " " + firstHeader);
            best.setText(bes + " " + firstHeader);
            if (highest) {
                if (Float.parseFloat(ayou) > Float.parseFloat(abes)) {
                    your.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    best.setTextColor(aver.getTextColors());
                } else {
                    best.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    your.setTextColor(aver.getTextColors());
                }
            } else {
                if (Float.parseFloat(ayou) > Float.parseFloat(abes)) {
                    best.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    your.setTextColor(aver.getTextColors());
                } else {
                    your.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    best.setTextColor(aver.getTextColors());
                }
            }
        });
        firstButton.performClick();
        secondButton.setOnClickListener(e -> {
            System.out.println("SECOND");
            your.setText(you2 + " " + secondHeader);
            aver.setText(ave2 + " " + secondHeader);
            best.setText(bes2 + " " + secondHeader);
            if (highest2) {
                if (Float.parseFloat(ayou2) > Float.parseFloat(abes2)) {
                    your.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    best.setTextColor(aver.getTextColors());
                } else {
                    best.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    your.setTextColor(aver.getTextColors());
                }
            } else {
                if (Float.parseFloat(ayou2) > Float.parseFloat(abes2)) {
                    best.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    your.setTextColor(aver.getTextColors());
                } else {
                    your.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                    best.setTextColor(aver.getTextColors());
                }
            }
        });

        firstButton.setText(h1);
        secondButton.setText(h2);

        image.setImageResource(draw);
        description.setText(name);
    }
}
