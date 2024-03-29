package com.oldi.football.ui.achievements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.oldi.football.R;

public class SuperPrettyProgressBar extends ConstraintLayout {
    private TextView progress, t25, t50, t75, t100;
    private ProgressBar bar;
    private Context context;

    private void Init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.super_pretty_progressbar, this);

        this.context = context;
        progress = findViewById(R.id.progress);
        bar = findViewById(R.id.bar);
        t25 = findViewById(R.id.t25);
        t50 = findViewById(R.id.t50);
        t75 = findViewById(R.id.t75);
        t100 = findViewById(R.id.t100);
    }

    public SuperPrettyProgressBar(Context context) {
        super(context);
        Init(context);
    }

    public SuperPrettyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }

    public SuperPrettyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context);
    }

    @SuppressLint("SetTextI18n")
    public void setData(int prog) {
        progress.setText(prog + "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bar.setProgress(prog, true);
        } else {
            bar.setProgress(prog);
        }

        if (prog < 24) {
            t25.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
        } else {
            if (prog < 49) {
                t50.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
            } else {
                if (prog < 74) {
                    t75.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                } else {
                    t100.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
                }
            }
        }
    }
}
