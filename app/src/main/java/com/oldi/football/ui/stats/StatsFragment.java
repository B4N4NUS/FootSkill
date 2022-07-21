package com.oldi.football.ui.stats;

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

import com.oldi.football.Connection;
import com.oldi.football.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class StatsFragment extends Fragment {
    public static final String[] names =
            {
                    "date",             // 0
                    "Speed",            // 1
                    "Hit",              // 2
                    "Reaction",         // 3
                    "Jump",             // 4
                    "Hitt",             // 5
                    "date_of_game",     // 6
                    "sharpshooting",    // 7
                    "Speed2",           // 8
                    "Speed_s_razbega",  // 9
                    "Jump2",            // 10
                    "Speed_s_razbega2", // 11
                    "Agility",          // 12
                    "FootSkill",        // 13
                    "FootSkill2",       // 14
                    "special_number"    // 15
            };
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
        SwitchChart walk = view.findViewById(R.id.walk);
        SwitchChart run = view.findViewById(R.id.run);
        Chart pounce = view.findViewById(R.id.pounce);
        Chart pounce2 = view.findViewById(R.id.pounce2);
        Chart reaction = view.findViewById(R.id.reaction);
        Chart agility = view.findViewById(R.id.agility);
        SwitchChart footskill = view.findViewById(R.id.footskill);
        Chart sharp = view.findViewById(R.id.sharp);

        strength.setHeader("Сила удара");
        walk.setHeader("10 метров с места");
        run.setHeader("10 метров с разбега");
        pounce.setHeader("Прыжок в высоту");
        pounce2.setHeader("Прыжок в длинну");
        reaction.setHeader("Скорость реакции");
        agility.setHeader("AGILITY TEST");
        footskill.setHeader("FOOTSKILL TEST");
        sharp.setHeader("Точность");

        try {
            GetStats();
            String[] date = getDates();

            strength.setData(GetFloat(2), date);
            walk.setData(GetFloat(1), date, GetFloat(8), "км/ч","сек");
            run.setData(GetFloat(9), date, GetFloat(11), "км/ч","сек");
            pounce.setData(GetFloat(4), date);
            pounce2.setData(GetFloat(10), date);
            reaction.setData(GetFloat(3), date);
            agility.setData(GetFloat(12), date);
            footskill.setData(GetFloat(13), date, GetFloat(14),"сек","удары");
            sharp.setData(GetFloat(7), date);

            Chart[] charts = {strength, pounce, pounce2, reaction, sharp, agility};
            SwitchChart[] switchCharts = {walk, run, footskill};

            ScrollView scroll = view.findViewById(R.id.stats_view);
            strength.wasAnimated = true;
            walk.wasAnimated = true;
            run.wasAnimated = true;

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
                for (SwitchChart chart : switchCharts) {
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