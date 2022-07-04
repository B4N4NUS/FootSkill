package com.example.football.ui.stats;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.football.MainActivity;
import com.example.football.R;
import com.example.football.components.Chart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatsFragment extends Fragment {

    private String[] names = {"date", "Speed", "Hit", "Reaction", "Jump", "Hitt", "date_of_game", "sharpshooting", "Speed2", "Speed_s_razbega", "Jump2"};
    private ArrayList<String[]> stats = new ArrayList<>();

    static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // User is viewing the fragment,
            // or fragment is inside the screen
            refresh();
        } else {
            // User is not viewing the fragment,
            // or fragment is our of the screen
            //doYourThing();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    private void GetStats() throws JSONException {
        if (MainActivity.rawUser == null) {
            stats = null;
            return;
        }

        JSONArray raw = MainActivity.rawUser.getJSONArray("Statistics");
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

    Chart strength;
    Chart walk;
    Chart walk2;
    Chart run;
    Chart run2;
    Chart pounce;
    Chart pounce2;
    Chart reaction;
    Chart sharp;

    public void refresh() {
        System.out.println("______________________REFRESHED___________________________________");
        if (strength != null) {
            strength.refresh();
            walk.refresh();
            walk2.refresh();
            run.refresh();
            run2.refresh();
            pounce.refresh();
            pounce2.refresh();
            reaction.refresh();
            sharp.refresh();
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

        //refresh();
        //System.out.println("______________________REFRESH___________________________________");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        prettyAndTheBeast.put(names[0],"Число");
//        prettyAndTheBeast.put(names[1],"Скорость");
//        prettyAndTheBeast.put(names[2],"Удар");
//        prettyAndTheBeast.put(names[3],"Реакция");
//        prettyAndTheBeast.put(names[4],"Паунс в окно");
//        prettyAndTheBeast.put(names[5],"Удар");
//        prettyAndTheBeast.put(names[6],"Время игры");
//        prettyAndTheBeast.put(names[7],"Меткость");
//        prettyAndTheBeast.put(names[8],"Скорость 2");
//        prettyAndTheBeast.put(names[9],"Скорость с разбега");
//        prettyAndTheBeast.put(names[10],"Скорокть с разбега 2");
//        prettyAndTheBeast.put(names[11],"Паунс в окно 2");

        Chart strength = getView().findViewById(R.id.strength);
        Chart walk = getView().findViewById(R.id.walk);
        Chart walk2 = getView().findViewById(R.id.walk2);
        Chart run = getView().findViewById(R.id.run);
        Chart run2 = getView().findViewById(R.id.run2);
        Chart pounce = getView().findViewById(R.id.pounce);
        Chart pounce2 = getView().findViewById(R.id.pounce2);
        Chart reaction = getView().findViewById(R.id.reaction);
        Chart sharp = getView().findViewById(R.id.sharp);

        strength.setHeader("Сила удара");
        walk.setHeader("10 метров с места (км/ч)");
        walk2.setHeader("10 метров с места (м/с)");
        run2.setHeader("10 метров с разбега (км/ч)");
        run.setHeader("10 метров с разбега (м/с)");
        pounce.setHeader("Прыжок в высоту");
        pounce2.setHeader("Прыжок в длинну");
        reaction.setHeader("Скорость реакции");
        sharp.setHeader("Точность");

        try {
            GetStats();
            //"date", "Speed", "Hit", "Reaction", "Jump", "Hitt", "date_of_game", "sharpshooting", "Speed2", "Speed_s_razbega", "Jump2"
            //  0        1       2         3         4       5        6                 7              8              9            10
            strength.setData(GetFloat(2), getDates());
            walk.setData(GetFloat(1), getDates());
            walk2.setData(GetFloat(8), getDates());
            run2.setData(GetFloat(9), getDates());
            run.setData(GetFloat(9), getDates());
            pounce.setData(GetFloat(4), getDates());
            pounce2.setData(GetFloat(10), getDates());
            reaction.setData(GetFloat(3), getDates());
            sharp.setData(GetFloat(7), getDates());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}