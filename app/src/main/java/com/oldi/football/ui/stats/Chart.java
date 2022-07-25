package com.oldi.football.ui.stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.oldi.football.R;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Chart extends LinearLayout {
    private TextView header;
    private LineChart chart;
    private final Context context;
    public boolean wasAnimated = false;

    public Chart(Context context) {
        super(context);
        this.context = context;
        initControl(context);
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context);
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initControl(context);
    }

    public void animateChart() {
        if (!wasAnimated) {
            wasAnimated = true;
            chart.animateY(1000);

        }
    }

    public void setHeader(String name) {
        header.setText(name);
    }

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.part_stats, this);

        header = findViewById(R.id.header);
        chart = findViewById(R.id.chart1);
    }

    private float DateToMillis(String date) {
        try {
            @SuppressLint("SimpleDateFormat")
            Date normDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if (normDate != null) {
                return 1.0f * normDate.getTime() / 1000;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] raw = date.split("-");
        return (float) (Integer.parseInt(raw[2])) * 365 * 24 * 3600 + (Integer.parseInt(raw[1]) - 1) * 24 * 3600 + (Integer.parseInt(raw[0]) * 36000);
    }

    public void setData(float[] stats, String[] labels) {
        ArrayList<Entry> values = new ArrayList<>();

        if (stats != null) {
            for (int i = 0; i < stats.length; i++) {
                float val = (float) stats[i];
                if (labels[i].split("-").length == 1) {
                    continue;
                }
                values.add(new Entry(DateToMillis(labels[i]), val));
            }
        } else {
            for (int i = 0; i < 10; i++) {
                values.add(new Entry(i, (float) Math.random() % 100));
            }
        }

        Collections.sort(values, (entry, t1) -> Float.compare(entry.getX(),t1.getX()));

        LineDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.0f);
            set1.setDrawFilled(true);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(3f);
            set1.setCircleHoleColor(ContextCompat.getColor(context, R.color.purple_700));
            set1.setCircleColor(ContextCompat.getColor(context, R.color.purple_700));
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(ContextCompat.getColor(context, R.color.purple_700));
            set1.setFillColor(ContextCompat.getColor(context, R.color.light_green));
            set1.setFillAlpha(20);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setDrawValues(false);
            set1.setFillFormatter((dataSet, dataProvider) -> chart.getAxisLeft().getAxisMinimum());

            chart.getAxisLeft().setDrawGridLines(true);
            chart.getAxisRight().setDrawGridLines(false);
            chart.getXAxis().setDrawGridLines(true);

            chart.getAxisLeft().setDrawAxisLine(false);
            chart.getAxisRight().setDrawAxisLine(false);
            chart.getXAxis().setDrawAxisLine(false);

            chart.getLegend().setEnabled(false);
            chart.setDescription(null);

            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            chart.getAxisRight().setEnabled(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setDrawVerticalHighlightIndicator(false);
            LineData data = new LineData(dataSets);


            if (labels != null) {
                XAxis xAxis = chart.getXAxis();
//                xAxis.setGranularity(1f);
//                xAxis.setLabelCount(4);
                xAxis.setLabelRotationAngle(45f);
                xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        System.out.println(labels[(int) value]);
                        return labels[((int) value)];
                    }
                });
            }
            chart.getXAxis().setValueFormatter(new DateValueFormatter());
            set1.setHighlightEnabled(true);

            //set1.setDrawHighlightIndicators(true);
            //set1.setHighLightColor(Color.GREEN);

            data.setValueTextSize(10f);

            chart.setData(data);
        }
    }
}
