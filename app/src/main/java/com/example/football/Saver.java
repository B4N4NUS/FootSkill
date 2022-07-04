package com.example.football;

import android.app.Activity;
import android.content.SharedPreferences;

public class Saver {
    public static String login;
    public static String pass;

    public static void SaveAut(Activity act, String log, String pas) {
        SharedPreferences settings = act.getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Username",log);
        editor.putString("Password",pas);
        editor.commit();
    }
    public static void LoadAut(Activity act) {
        SharedPreferences settings = act.getSharedPreferences("UserInfo", 0);
        login = settings.getString("Username", "");
        pass = settings.getString("Password", "");
    }
}
