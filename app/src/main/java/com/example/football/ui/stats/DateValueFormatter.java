package com.example.football.ui.stats;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateValueFormatter extends IndexAxisValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        long emissionsMilliSince1970Time = ((long) value) * 1000;

        Date timeMilliseconds = new Date(emissionsMilliSince1970Time);
        DateFormat dateTimeFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

        return dateTimeFormat.format(timeMilliseconds);
    }
}
