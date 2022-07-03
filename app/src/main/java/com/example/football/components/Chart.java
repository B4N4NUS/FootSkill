package com.example.football.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.football.R;
import com.example.football.ui.stats.CustomMaker;
import com.example.football.ui.stats.DateValueFormatter;
import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chart extends LinearLayout {
    private TextView header;
    private ImageView splitter;
    private LineChart chart;
    private Context context;

    public Chart(Context context) {
        super(context);
        this.context = context;
        initControl(context);
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context);
        //setData(null, null);
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initControl(context);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initControl(context);
    }

    public void refresh() {
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    public void setHeader(String name) {
        header.setText(name);
        int size = splitter.getMaxWidth();
        header.getTextSize();
        ViewGroup.LayoutParams params = splitter.getLayoutParams();
        params.width = (int)(header.getTextSize() * name.length() * 0.6);
        splitter.setLayoutParams(params);
//        splitter.setMaxWidth((int)(size * header.getTextSize() * name.length()));
//        splitter.setMinimumWidth((int)(size * header.getTextSize() * name.length()));
    }

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.stat_part, this);

        // layout is inflated, assign local variables to components
        header = findViewById(R.id.header);
        splitter = findViewById(R.id.splitter);
        chart = findViewById(R.id.chart1);
    }

    private float DateToMillis(String date) {
        String[] raw = date.split("-");
        System.out.println("DATE TO MILLIS " +date);
        try {
            @SuppressLint("SimpleDateFormat")
            Date daate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            System.out.println("SECONDS " + daate.getTime()/1000);
            return daate.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //return 0;
        return (float)(Integer.parseInt(raw[2]))*365*24*3600 + (Integer.parseInt(raw[1])-1)*24*3600 + (Integer.parseInt(raw[0])*36000);
    }

    public void setData(float[] stats, String[] labels) {
        float start = 1f;
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

        LineDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
//            set1.setDrawIcons(false);
//            set1.disableDashedLine();
//            set1.setDrawCircles(false);
//            set1.setDrawFilled(true);
//            set1.setCubicIntensity(12);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            //set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(3f);
            set1.setCircleHoleColor(ContextCompat.getColor(context, R.color.purple_700));
            set1.setCircleColor(ContextCompat.getColor(context, R.color.purple_700));
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(ContextCompat.getColor(context, R.color.purple_700));
            set1.setFillColor(ContextCompat.getColor(context, R.color.purple_700));
            set1.setFillAlpha(40);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setDrawValues(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

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
//            chart.setDrawGridBackground(false);
//            chart.setDrawBorders(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);


            if (labels != null) {
                XAxis xAxis = chart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value) {
                        System.out.println(labels[(int) value]);
                        return labels[((int) value)];
                    }
                });
            }
            chart.getXAxis().setValueFormatter(new DateValueFormatter());
            set1.setHighlightEnabled(true); // allow highlighting for DataSet

            // set this to false to disable the drawing of highlight indicator (lines)
            set1.setDrawHighlightIndicators(true);
            set1.setHighLightColor(Color.GREEN); // color for highlight indicator

            data.setValueTextSize(10f);
            //data.setBarWidth(0.9f);
            chart.setData(data);

//           IMarker marker = new CustomMaker(context, R.layout.fragment_stats);
//           chart.setMarkerView(marker);
        }
    }
}
