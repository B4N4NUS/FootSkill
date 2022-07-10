package com.example.football.ui.stats;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.football.Connection;
import com.example.football.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class StatsFragment extends Fragment {
    private final String[] names = {"date", "Speed", "Hit", "Reaction", "Jump", "Hitt", "date_of_game", "sharpshooting", "Speed2", "Speed_s_razbega", "Jump2"};
    private ArrayList<String[]> stats = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    private void GetStats() throws JSONException {
        if (!Connection.isPersonAlive()) {
            stats = null;
            return;
        }

        JSONArray raw = Connection.getStats();
        stats.clear();
        for (int i = 0; i < raw.length(); i++) {
            stats.add(new String[names.length]);

            for (int j = 0; j < names.length; j++) {
                stats.get(i)[j] = ((JSONObject) raw.get(i)).getString(names[j]);
            }
        }

        for (int i = 0; i < stats.size(); i++) {
            for (int j = 0; j < stats.get(i).length; j++) {
                System.out.print(stats.get(i)[j] + "  ");
            }
            System.out.println();
        }
    }

    private float[] GetFloat(int pos) {
        if (stats == null) {
            return null;
        }
        float[] newArr = new float[stats.size()];
        for (int i = 0; i < stats.size(); i++) {
            if (Objects.equals(stats.get(i)[pos], "null")) {
                return null;
            }
            try {
                newArr[i] = Float.parseFloat(stats.get(i)[pos]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return newArr;
    }

    private String[] getDates() {
        if (stats == null) {
            return null;
        }
        String[] newArr = new String[stats.size()];
        for (int i = 0; i < stats.size(); i++) {
            if (Objects.equals(stats.get(i)[0], "null")) {
                return null;
            }
            newArr[i] = stats.get(i)[0];
        }
        return newArr;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Chart strength = view.findViewById(R.id.strength);
        Chart walk = view.findViewById(R.id.walk);
        Chart walk2 = view.findViewById(R.id.walk2);
        Chart run = view.findViewById(R.id.run);
        Chart run2 = view.findViewById(R.id.run2);
        Chart pounce = view.findViewById(R.id.pounce);
        Chart pounce2 = view.findViewById(R.id.pounce2);
        Chart reaction = view.findViewById(R.id.reaction);
        Chart sharp = view.findViewById(R.id.sharp);

        strength.setHeader("Сила удара");
        walk.setHeader("10 метров с места (км/ч)");
        walk2.setHeader("10 метров с места (сек)");
        run2.setHeader("10 метров с разбега (км/ч)");
        run.setHeader("10 метров с разбега (сек)");
        pounce.setHeader("Прыжок в высоту");
        pounce2.setHeader("Прыжок в длинну");
        reaction.setHeader("Скорость реакции");
        sharp.setHeader("Точность");

        try {
            GetStats();
            strength.setData(GetFloat(2), getDates());
            walk.setData(GetFloat(1), getDates());
            walk2.setData(GetFloat(8), getDates());
            run2.setData(GetFloat(9), getDates());
            run.setData(GetFloat(9), getDates());
            pounce.setData(GetFloat(4), getDates());
            pounce2.setData(GetFloat(10), getDates());
            reaction.setData(GetFloat(3), getDates());
            sharp.setData(GetFloat(7), getDates());

            Chart[] charts = {strength, walk, walk2, run2, run, pounce, pounce2, reaction, sharp};

            ScrollView scroll = view.findViewById(R.id.stats_view);
            strength.wasAnimated = true;
            walk.wasAnimated = true;
            walk2.wasAnimated = true;
            scroll.getViewTreeObserver().addOnScrollChangedListener(() -> {
                Rect scrollBounds = new Rect();
                scroll.getHitRect(scrollBounds);
                for (Chart chart : charts) {
                    if (chart.getLocalVisibleRect(scrollBounds)) {
                        chart.animateChart();
                    } else {
                        chart.wasAnimated = false;
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}