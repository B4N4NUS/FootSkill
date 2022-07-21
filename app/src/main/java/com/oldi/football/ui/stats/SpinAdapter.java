package com.oldi.football.ui.stats;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.oldi.football.Connection;
import com.oldi.football.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SpinAdapter  extends ArrayAdapter<String> {

    private Context context;
    private String[] values;

    private ComparisonPart[] comps;

    public SpinAdapter(Context context, int textViewResourceId, String[] values, ComparisonPart[] comps) {
        super(context, textViewResourceId, values);
        this.context = context;
        Arrays.sort(values);
        ArrayList<String> vals = new ArrayList<>(Arrays.asList(values));
        vals.add(0,"Выберите год рождения");
        this.values = vals.toArray(new String[vals.size()]);;
        this.comps = comps;
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public String getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        if (position == 0) {
            label.setTextColor(Color.GRAY);
        } else {
            label.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }
        label.setTextSize(24);
        label.setText(values[position]);

        int[][] stats = Connection.getAverage(values[position]);

        comps[0].SetData("Удар",R.drawable.boner, stats[0][2],stats[1][2],stats[2][2], "км/ч", true);
        comps[1].SetData("Прыжок в высоту",R.drawable.up, stats[0][4],stats[1][4],stats[2][4], "см", true);
        comps[2].SetData("Прыжок в длину", R.drawable.jumps_length,stats[0][10],stats[1][10],stats[2][10], "см", true);
        comps[3].SetData("Реакция",R.drawable.clock,stats[0][3],stats[1][3],stats[2][3], "мс", false);
        comps[4].SetData("10 метров с места",R.drawable.speed,stats[0][1],stats[1][1],stats[2][1], "км/ч", true, stats[0][8],stats[1][8],stats[2][8], "с", false, "км", "сек");
        comps[5].SetData("10 метров с разбега",R.drawable.speed,stats[0][9],stats[1][9],stats[2][9], "км/ч", true, stats[0][11],stats[1][11],stats[2][11], "с", false, "км", "сек");
        comps[6].SetData("Agility test", R.drawable.clock,stats[0][12],stats[1][12],stats[2][12], "с", false);
        comps[7].SetData("FootSkill test", R.drawable.footskill2,stats[0][13],stats[1][13],stats[2][13], "с", false, stats[0][14],stats[1][14],stats[2][14], "ударов", true, "сек", "уд");
        comps[8].SetData("Точность", R.drawable.scope,stats[0][7],stats[1][7],stats[2][7], "%", true);

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }
        return view;
    }
}