package com.example.football.components;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Loading extends View {

    private Movie mMovie;
    private InputStream mStream;
    private long mMoviestart;

    public Loading(Context context) {
        super(context);

        // TODO Auto-generated constructor stub
    }
    public Loading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void SetData(InputStream stream) {
        mStream = stream;
        mMovie = Movie.decodeStream(mStream);
    }

    public Loading(Context context, InputStream stream) {
        super(context);

        mStream = stream;
        mMovie = Movie.decodeStream(mStream);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie != null) {
            canvas.drawColor(Color.TRANSPARENT);
            super.onDraw(canvas);
            final long now = SystemClock.uptimeMillis();
            if (mMoviestart == 0) {
                mMoviestart = now;
            }
            final int relTime = (int) ((now - mMoviestart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, 20, 20);
            this.invalidate();
        }
    }
}