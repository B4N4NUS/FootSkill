package com.oldi.football.ui.news;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oldi.football.AchievementsActivity;
import com.oldi.football.Connection;
import com.oldi.football.NewsActivity;
import com.oldi.football.R;
import com.oldi.football.TabbedActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private NewsInfo info;
    private LinearLayout layout;

    public NewsFragment() {
        info = Connection.getNews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        info = Connection.getNews();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        layout = view.findViewById(R.id.news_place);

        NewsPart part;

//        part = new NewsPart(view.getContext());
//        part.setOnClickListener(e-> {
//            Intent switchActivityIntent = new Intent(getActivity(), NewsActivity.class);
//            switchActivityIntent.putExtra("header", info.getHeader(0));
//            switchActivityIntent.putExtra("description", info.getDescription(0));
//            //switchActivityIntent.putExtra("pic", info.getBitmap(0));
//            startActivity(switchActivityIntent);
//        });
//        part.setData("ADWAD", "WDAWDAWD", null);
//        layout.addView(part);

        for(int i = 0; i < info.getLength(); i++) {
            part = new NewsPart(view.getContext());
            int finalI = i;
            part.setOnClickListener(e-> {
                Intent switchActivityIntent = new Intent(getActivity(), NewsActivity.class);
                switchActivityIntent.putExtra("header", info.getHeader(finalI));
                switchActivityIntent.putExtra("autor", info.autors.get(finalI));
                switchActivityIntent.putExtra("date", info.dates.get(finalI));
                switchActivityIntent.putExtra("lastname", info.lastnames.get(finalI));
                switchActivityIntent.putExtra("bitmap", info.bitmaps.get(finalI));
                switchActivityIntent.putExtra("description", info.getDescription(finalI));
                switchActivityIntent.putExtra("video", info.vids.get(finalI));
                //switchActivityIntent.putExtra("pic", info.getBitmap(0));
                startActivity(switchActivityIntent);
            });
            part.setData(info.getHeader(i), info.dates.get(i), info.bitmaps.get(i));
            layout.addView(part);
        }

    }
}