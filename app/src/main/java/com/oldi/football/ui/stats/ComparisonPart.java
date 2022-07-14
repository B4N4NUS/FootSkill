package com.oldi.football.ui.stats;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.oldi.football.R;

public class ComparisonPart extends RelativeLayout {
    private TextView your, aver, best, description;
    private ImageView image;
    private final Context context;

    public ComparisonPart(Context context) {
        super(context);
        this.context = context;
        initControl(context);
    }

    public ComparisonPart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context);
    }

    public ComparisonPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initControl(context);
    }
    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.part_comp, this);

        your = findViewById(R.id.your_best_score);
        aver = findViewById(R.id.aver_score);
        best = findViewById(R.id.tot_best_score);
        description = findViewById(R.id.desk);
        image = findViewById(R.id.skill);
    }

    public void SetData(String name, int draw,int you, int ave, int bes) {
        your.setText(you+"");
        aver.setText(ave+"");
        best.setText(bes+"");
        image.setImageResource(draw);
        description.setText(name);
    }
}
