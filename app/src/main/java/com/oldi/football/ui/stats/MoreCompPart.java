package com.oldi.football.ui.stats;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oldi.football.R;

public class MoreCompPart extends RelativeLayout {
    private TextView description, hypertext, hypertext2;
    private Context context;

    public MoreCompPart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context);
    }

    public Activity getActivity(Context context)
    {
        if (context == null)
        {
            return null;
        }
        else if (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity) context;
            }
            else
            {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.part_more_comp, this);

        description = findViewById(R.id.comp_desc);
        hypertext = findViewById(R.id.comp_example);
        hypertext2 = findViewById(R.id.comp_example2);
    }

    public void SetText(String title, String desc, String hyper) {
        description.setText(Html.fromHtml("<b>" + title + "</b>" + desc));
        hypertext.setOnClickListener(e-> {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hyper));
//            getActivity(context).startActivity(browserIntent);

            Intent switchActivityIntent = new Intent(getActivity(context), WebViewerActivity.class);
            switchActivityIntent.putExtra("link", hyper);
            switchActivityIntent.putExtra("message", "From: " + MoreComparisonActivity.class.getSimpleName());
            getActivity(context).startActivity(switchActivityIntent);
        });

        hypertext2.setVisibility(GONE);

        hypertext.setText("Пример тестирования");
    }

    public void SetText(String title, String desc, String hyper, String hyper2) {
        description.setText(Html.fromHtml("<b>" + title + "</b>" + desc));
        hypertext.setOnClickListener(e-> {
            Intent switchActivityIntent = new Intent(getActivity(context), WebViewerActivity.class);
            switchActivityIntent.putExtra("link", hyper);
            switchActivityIntent.putExtra("message", "From: " + MoreComparisonActivity.class.getSimpleName());
            getActivity(context).startActivity(switchActivityIntent);
        });

        hypertext2.setOnClickListener(e-> {
            Intent switchActivityIntent = new Intent(getActivity(context), WebViewerActivity.class);
            switchActivityIntent.putExtra("link", hyper2);
            switchActivityIntent.putExtra("message", "From: " + MoreComparisonActivity.class.getSimpleName());
            getActivity(context).startActivity(switchActivityIntent);
        });

        hypertext.setText("Пример тестирования (с места)");
        hypertext2.setText("Пример тестирования (с разбега)");
    }
}
