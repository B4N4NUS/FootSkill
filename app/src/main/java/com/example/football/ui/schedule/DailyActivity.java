package com.example.football.ui.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.football.R;

public class DailyActivity extends ConstraintLayout {
    private TextView time, activity, day;

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.day_activity, this);

        activity = findViewById(R.id.activity);
        time = findViewById(R.id.time);
        day = findViewById(R.id.day_of_the_week);
    }

    public DailyActivity(Context context) {
        super(context);
        initControl(context);
    }

    public DailyActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public DailyActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    public void setData(String textDay, String textTime, String textActivity) {
        time.setText(textTime);
        activity.setText(textActivity);
        day.setText(textDay);
    }
}
