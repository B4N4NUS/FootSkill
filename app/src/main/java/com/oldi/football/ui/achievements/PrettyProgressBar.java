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

import com.oldi.football.R;

public class PrettyProgressBar extends ConstraintLayout {
    private TextView progress;
    private ProgressBar bar;
    private int prog = 0;

    private void Init(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.pretty_progressbar, this);

        progress = findViewById(R.id.progress);
        bar = findViewById(R.id.bar);
    }

    public void Animate() {
        bar.startAnimation(new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                if (prog != 0) {
                    setDuration((int) (prog * 1.0 / 100 * 1000));
                }
                float value =  prog * interpolatedTime;
                bar.setProgress((int) value);
            }
        });
    }

    public PrettyProgressBar(Context context) {
        super(context);
        Init(context);
    }

    public PrettyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }

    public PrettyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context);
    }

    @SuppressLint("SetTextI18n")
    public void setData(int prog) {
        progress.setText(prog + "");
        this.prog = prog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bar.setProgress(prog, true);
        }
    }
}
