package com.oldi.football.ui.news;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.oldi.football.AchievementsActivity;
import com.oldi.football.Connection;
import com.oldi.football.R;
import com.oldi.football.TabbedActivity;

import java.io.InputStream;
import java.net.URL;

public class NewsPart extends RelativeLayout {

    private TextView header, description;
    private ImageView pic;
    private final Context context;

    public NewsPart(Context context) {
        super(context);
        this.context = context;
        initControl(context);
    }

    public NewsPart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context);
    }

    public NewsPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initControl(context);
    }

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.part_news, this);

        header = findViewById(R.id.news_header);
        description = findViewById(R.id.news_desk);
        pic = findViewById(R.id.pic);
    }

    public void setData(String hdr, String dscr, String picUrl) {
        header.setText(hdr);
        description.setText(dscr);
        Bitmap bitmap;
        try {
            if (picUrl.equals(Connection.imagesUrl)) {
                pic.setImageResource(R.drawable.red_logo);
            } else {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(picUrl).getContent());
                if (bitmap.getWidth() >= bitmap.getHeight()) {
                    bitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2 - bitmap.getHeight() / 2, 0, bitmap.getHeight(), bitmap.getHeight());
                }
                pic.setImageBitmap(bitmap);
            }
        } catch (Exception exception) {
            pic.setImageResource(R.drawable.red_logo);
        }
    }
}