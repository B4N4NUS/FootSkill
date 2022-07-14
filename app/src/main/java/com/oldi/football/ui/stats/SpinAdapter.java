package com.oldi.football.ui.stats;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oldi.football.Connection;
import com.oldi.football.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SpinAdapter  extends ArrayAdapter<String> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
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
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        } else {
            return true;
        }
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        if (position == 0) {
            label.setTextColor(Color.GRAY);
        } else {
            label.setTextColor(Color.BLACK);
        }
        label.setTextSize(24);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position]);

        int[][] stats = Connection.getAverage(values[position]);

        comps[0].SetData("Удар",R.drawable.boner, stats[0][2],stats[1][2],stats[2][2]);
        comps[1].SetData("Прыжок в высоту",R.drawable.up, stats[0][4],stats[1][4],stats[2][4]);
        comps[2].SetData("Прыжок в длину",R.drawable.up,stats[0][10],stats[1][10],stats[2][10]);
        comps[3].SetData("Реакция",R.drawable.clock,stats[0][3],stats[1][3],stats[2][3]);
        comps[4].SetData("10 метров с места",R.drawable.speed,stats[0][1],stats[1][1],stats[2][1]);
        comps[5].SetData("10 метров с разбега",R.drawable.speed,stats[0][9],stats[1][9],stats[2][9]);

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }
}