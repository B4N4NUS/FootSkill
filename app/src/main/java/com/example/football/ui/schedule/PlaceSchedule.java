package com.example.football.ui.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.football.R;

import java.util.ArrayList;

public class PlaceSchedule extends RelativeLayout {
    private TextView place;
    private LinearLayout days;
    private Context context;


    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.day_week, this);

        this.context = context;
        place = findViewById(R.id.place);
        days = findViewById(R.id.days);
    }

    public PlaceSchedule(Context context) {
        super(context);
        initControl(context);
    }

    public PlaceSchedule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public PlaceSchedule(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    public PlaceSchedule(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initControl(context);
    }

    public void setData(String header, ArrayList<String> daysOfWeek, ArrayList<String> times, ArrayList<String> acts) {
        place.setText(header);

        for(int i = 0; i < times.size(); i++) {
            DailyActivity daily = new DailyActivity(context);
            //daily.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
            daily.setData(daysOfWeek.get(i), times.get(i), acts.get(i));
            days.addView(daily);
        }
    }
}
