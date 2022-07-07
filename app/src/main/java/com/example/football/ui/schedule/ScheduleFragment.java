package com.example.football.ui.schedule;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.football.Connection;
import com.example.football.R;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    private LinearLayout layout;


    public ScheduleFragment() {
    }

    public ArrayList<RawSchedule> data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        data = Connection.GetScheduleCopy();
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    public void Load() {
        long startTimer = System.currentTimeMillis();
        try {
            for (int i = 0; i < data.size(); i++) {
                PlaceSchedule pl = new PlaceSchedule(getContext());
                pl.setData(data.get(i).name, data.get(i).date, data.get(i).time, data.get(i).act);
                pl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                layout.addView(pl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        TextView end = new TextView(getContext());
        end.setText("End");
        end.setTextColor(Color.WHITE);
        end.setTextSize(60);
        layout.addView(end);

        System.out.println("_____________________________________SCHEDULE_CREATED_IN_" + ((System.currentTimeMillis() - startTimer * 1.0) / 1000) + "_SECONDS_______________________________");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        layout = view.findViewById(R.id.lays);

        Load();
    }
}