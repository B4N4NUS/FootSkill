package com.example.football.ui.achievements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.football.R;

public class PrettyProgressBar extends ConstraintLayout {
    private TextView progress;
    private ProgressBar bar;

    private void Init(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.pretty_progressbar, this);

        progress = findViewById(R.id.progress);
        bar = findViewById(R.id.bar);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bar.setProgress(prog, true);
        }
    }
}
