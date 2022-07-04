package com.example.football.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.football.R;

public class ScheduleActivity  extends LinearLayout {
    private TextView time, name;
    private Context context;


    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.day_week, this);

        // layout is inflated, assign local variables to components
        name = findViewById(R.id.activity);
        time = findViewById(R.id.time);
    }

    public ScheduleActivity(Context context) {
        super(context);
        initControl(context);
    }

    public ScheduleActivity(Context context, String time, String name) {
        super(context);
        initControl(context);
        this.name.setText(name);
        this.time.setText(time);
    }
}
