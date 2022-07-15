package com.oldi.football;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.InputStream;
import java.net.URL;

public class NewsActivity extends YouTubeBaseActivity {
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayerView youTubePlayerView;

    Bitmap pic;
    String header, description, videoName = "dQw4w9WgXcQ";
    TextView tvHeader, tvDesc, tvName, tvDate;
    ImageView image;

    public NewsActivity() {
    }

    public NewsActivity(String videoUrl, String header, String desc, Bitmap pic) {
        videoName = videoUrl.replace("https://www.youtube.com/watch?v=", "");
        this.header = header;
        this.description = desc;
        this.pic = pic;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_news);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        View scroll = findViewById(R.id.news_scroll);
        scroll.setOnTouchListener(new OnSwipeTouchListener(scroll.getContext()) {
            public void onSwipeTop() {
                System.out.println("top");
            }

            public void onSwipeRight() {
                onBackPressed();
                System.out.println("right");
            }

            public void onSwipeLeft() {
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvHeader.setText(extras.getString("header"));
            tvDesc.setText(extras.getString("description"));
            tvName.setText("Автор: " + extras.getString("autor") + " " +extras.getString("lastname"));
            tvDate.setText("Новость от: " + extras.getString("date"));
            try {
                if (extras.getString("bitmap").equals(Connection.imagesUrl)) {
                    image.setMaxHeight(0);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(extras.getString("bitmap")).getContent());
                    image.setImageBitmap(bitmap);
                }
            } catch (Exception ignored) {}
            videoName = extras.getString("video").replace("https://www.youtube.com/embed/", "").replace("https://www.youtube.com/watch?v=", "");
            if (videoName.equals("null")) {
                videoName = "dQw4w9WgXcQ";
            }
        }

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.cueVideo(videoName);
                //youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize("AIzaSyD9aYTypds1BCmuusedyVaNBs3u7Ad_O4o", onInitializedListener);
    }
}