package com.example.football.ui.achievements;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.football.Connection;
import com.example.football.R;

public class AchievementFragment extends Fragment {
    private ProgressBar train;
    private ProgressBar panna;
    private ProgressBar turn;
    private ProgressBar lager;
    private ProgressBar test;
    private ProgressBar earned;
    private ProgressBar available;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_acheivment, container, false);
    }

    public void Load() {
        int tr = 0, pn = 0, tu = 0,la = 0,te = 0;
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
        int en = tr + pn + tu + la + te;

        System.out.println("train = " + tr + "\n" +
                "panna = " + pn + "\n" +
                "turn = " + tu + "\n" +
                "lager = " + la + "\n" +
                "test = " + te + "\n" +
                "earned = " + en);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            train.setProgress(tr, true);
            panna.setProgress(pn, true);
            turn.setProgress(tu, true);
            lager.setProgress(la, true);
            test.setProgress(te, true);
            earned.setProgress(en, true);
            available.setProgress(100 - en, true);
        }
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

        Load();
    }
}