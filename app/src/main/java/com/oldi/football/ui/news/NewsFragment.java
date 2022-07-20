package com.oldi.football.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oldi.football.Connection;
import com.oldi.football.NewsActivity;
import com.oldi.football.R;

public class NewsFragment extends Fragment {
    private final NewsInfo info;

    public NewsFragment() {
        info = Connection.getNews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = view.findViewById(R.id.news_place);

        NewsPart part;

        for (int i = 0; i < info.getLength(); i++) {
            part = new NewsPart(view.getContext());
            int finalI = i;
            part.setOnClickListener(e -> {
                Intent switchActivityIntent = new Intent(getActivity(), NewsActivity.class);
                switchActivityIntent.putExtra("header", info.headers.get(finalI));
                switchActivityIntent.putExtra("autor", info.autors.get(finalI));
                switchActivityIntent.putExtra("date", info.dates.get(finalI));
                switchActivityIntent.putExtra("lastname", info.lastnames.get(finalI));
                switchActivityIntent.putExtra("bitmap", info.bitmaps.get(finalI));
                switchActivityIntent.putExtra("description", info.descriptions.get(finalI));
                switchActivityIntent.putExtra("video", info.vids.get(finalI));
                startActivity(switchActivityIntent);
                if (android.os.Build.VERSION.SDK_INT > 29) {
                    getActivity().overridePendingTransition(R.anim.slide_rl, R.anim.slide_rl_out);
                }
            });
            part.setData(info.headers.get(i), info.dates.get(i), info.bitmaps.get(i));
            layout.addView(part);
        }
    }
}