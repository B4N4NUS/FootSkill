package com.oldi.football;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class NewsActivity extends YouTubeBaseActivity {
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayerView youTubePlayerView;

    String videoName = "dQw4w9WgXcQ";
    TextView tvHeader, tvDesc, tvName, tvDate;
    ImageView image;

    boolean swipeLR = true;

    public NewsActivity() {
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_news);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        View scroll = findViewById(R.id.news_scroll);
        scroll.setOnTouchListener(new OnSwipeTouchListener(scroll.getContext()) {
            public void onSwipeTop() {
                System.out.println("top");
            }

            public void onSwipeRight() {
                swipeLR = true;
                onBackPressed();
                System.out.println("right");
            }

            public void onSwipeLeft() {
                swipeLR = false;
                onBackPressed();
                System.out.println("left");
            }

            public void onSwipeBottom() {
                System.out.println("bot");
            }
        });

        tvDesc = findViewById(R.id.a_desc);
        tvHeader = findViewById(R.id.a_header);
        image = findViewById(R.id.a_pic);
        tvName = findViewById(R.id.a_name);
        tvDate = findViewById(R.id.a_date);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvHeader.setText(extras.getString("header"));
            tvDesc.setText(extras.getString("description"));
            tvName.setText("Автор: " + extras.getString("autor") + " " + extras.getString("lastname"));
            tvDate.setText("Новость от: " + extras.getString("date"));
            try {
                if (extras.getString("bitmap").equals(Connection.imagesUrl)) {
                    image.setMaxHeight(0);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(extras.getString("bitmap")).getContent());
                    image.setImageBitmap(bitmap);
                }
            } catch (Exception ignored) {
            }
            videoName = extras.getString("video").replace("https://www.youtube.com/embed/", "").replace("https://www.youtube.com/watch?v=", "");
            if (videoName.equals("null")) {
                videoName = "dQw4w9WgXcQ";
            } else {
                image.setMaxHeight(0);
            }
        }


        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoName);
                if (videoName.equals("dQw4w9WgXcQ")) {
                    youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                youTubePlayerView.setVisibility(View.GONE);
            }
        };

        if (videoName.equals("dQw4w9WgXcQ")) {
            Random rnd = new Random();
            if (rnd.nextInt(100) > 98) {
                youTubePlayerView.initialize("AIzaSyD9aYTypds1BCmuusedyVaNBs3u7Ad_O4o", onInitializedListener);
                image.setVisibility(View.GONE);
            } else {
                youTubePlayerView.setVisibility(View.GONE);
            }
        } else {
            youTubePlayerView.initialize("AIzaSyD9aYTypds1BCmuusedyVaNBs3u7Ad_O4o", onInitializedListener);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (swipeLR) {
            overridePendingTransition(R.anim.slide_lr, R.anim.slide_lr_out);
        } else {
            overridePendingTransition(R.anim.slide_rl, R.anim.slide_rl_out);
        }
    }
}