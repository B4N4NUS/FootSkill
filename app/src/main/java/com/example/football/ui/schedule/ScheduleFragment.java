package com.example.football.ui.schedule;

import androidx.lifecycle.ViewModelProvider;

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
import com.example.football.components.Chart;
import com.example.football.components.Loading;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduleFragment extends Fragment {
    private LinearLayout layout;


    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    public void Load() {
        long startTimer = System.currentTimeMillis();
        if (Objects.equals(Connection.schedule, "")) {
            TextView errorText = new TextView(getContext());
            errorText.setText("  Расписание недоступно");
            layout.addView(errorText);
            return;
        }

        String[] names;
        try {
            JSONArray array = new JSONArray(Connection.schedule);
            names = new String[array.length()];

            for (int i = 0; i < array.length(); i++) {
                ArrayList<String> date = new ArrayList<>();
                ArrayList<String> time = new ArrayList<>();
                ArrayList<String> act = new ArrayList<>();
                JSONObject object = array.getJSONObject(i);
                names[i] = object.getString("Loation");

                if (!(object.getString("MondayStart").equals("null") || object.getString("MondayStart").equals(""))) {
                    date.add("Понедельник");
                    time.add(object.getString("MondayStart"));
                    act.add(object.getString("MondayArtema"));
                }
                if (!(object.getString("TuesdayStart").equals("null") || object.getString("TuesdayStart").equals(""))) {
                    date.add("Вторник");
                    time.add(object.getString("TuesdayStart"));
                    act.add(object.getString("TuesdayArtema"));
                }
                if (!(object.getString("WednesdayStart").equals("null") || object.getString("WednesdayStart").equals(""))) {
                    date.add("Среда");
                    time.add(object.getString("WednesdayStart"));
                    act.add(object.getString("WednesdayArtema"));
                }
                if (!(object.getString("ThursdayStart").equals("null") || object.getString("ThursdayStart").equals(""))) {
                    date.add("Четверг");
                    time.add(object.getString("ThursdayStart"));
                    act.add(object.getString("ThursdayArtema"));
                }
                if (!(object.getString("FridayStart").equals("null") || object.getString("FridayStart").equals(""))) {
                    date.add("Пятница");
                    time.add(object.getString("FridayStart"));
                    act.add(object.getString("FridayArtema"));
                }
                if (!(object.getString("SaturdayStart").equals("null") || object.getString("SaturdayStart").equals(""))) {
                    date.add("Суббота");
                    time.add(object.getString("SaturdayStart"));
                    act.add(object.getString("SaturdayArtema"));
                }
                if (!(object.getString("SundayStart").equals("null") || object.getString("SundayStart").equals(""))) {
                    date.add("Воскресенье");
                    time.add(object.getString("SundayStart"));
                    act.add(object.getString("SundayArtema"));
                }
                if (date.size() == 0) {
                    continue;
                }

                PlaceSchedule pl = new PlaceSchedule(getContext());
                pl.setData(names[i], date, time, act);
                pl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                layout.addView(pl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//
//        ArrayList<String> data = new ArrayList<>();
//        data.add("123123");
//        ArrayList<PlaceSchedule> places = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            PlaceSchedule pl = new PlaceSchedule(getContext());
//            pl.setData("dawdA", data, data, data);
//            pl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
//            data.add(Math.random() * 10 % 10 + "");
//            layout.addView(pl);
//        }
        TextView end = new TextView(getContext());
        end.setText("End");
        end.setTextColor(Color.WHITE);
        end.setTextSize(60);
        layout.addView(end);

        System.out.println("_____________________________________SCHEDULE_CREATED_IN_" +  ((System.currentTimeMillis() - startTimer*1.0)/1000) + "_SECONDS_______________________________");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        layout = view.findViewById(R.id.lays);

        Load();
    }
}