package com.oldi.football.ui.achievements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldi.football.Connection;
import com.oldi.football.R;

public class AchievementFragment extends Fragment {
    private int tr, pn, tu, la, te, en;
    private PrettyProgressBar train, panna, turn, lager, test, earned;
    SuperPrettyProgressBar available;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Load();
        return inflater.inflate(R.layout.fragment_acheivment, container, false);
    }

    public void Load() {
        tr = pn = tu = la = te = en = 0;

        try {
            for (int i = 0; i < Connection.userAchievements.size(); i++) {
                String name = Connection.userAchievements.get(i).getString("last_statements");
                switch (name) {
                    case "Панна": {
                        pn += Integer.parseInt(Connection.userAchievements.get(i).getString("adresse"));
                        break;
                    }
                    case "3 VS 3": {
                        tu += Integer.parseInt(Connection.userAchievements.get(i).getString("adresse"));
                        break;
                    }
                    case "Тестирование": {
                        te += Integer.parseInt(Connection.userAchievements.get(i).getString("adresse"));
                        break;
                    }
                    default:
                        System.out.println(name);
                }
            }
            tr += Connection.getCountOfTraining();
            la += Connection.getCountOfCamps();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        en = tr + pn + tu + la + te;

        System.out.println("train = " + tr + "\n" +
                "panna = " + pn + "\n" +
                "turn = " + tu + "\n" +
                "lager = " + la + "\n" +
                "test = " + te + "\n" +
                "earned = " + en);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        train = view.findViewById(R.id.pb_train);
        panna = view.findViewById(R.id.pb_panna);
        turn = view.findViewById(R.id.pb_turn);
        lager = view.findViewById(R.id.pb_lager);
        test = view.findViewById(R.id.pb_test);
        earned = view.findViewById(R.id.pb_earned);
        available = view.findViewById(R.id.pb_avaliable);

        train.setData(tr);
        panna.setData(pn);
        turn.setData(tu);
        lager.setData(la);
        test.setData(te);
        earned.setData(en);
        available.setData(en - Connection.getCountOfMinusPoints());
    }

    @Override
    public void onResume() {
        super.onResume();
        train.setData(tr);
        panna.setData(pn);
        turn.setData(tu);
        lager.setData(la);
        test.setData(te);
        earned.setData(en);
        available.setData(en - Connection.getCountOfMinusPoints());
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}