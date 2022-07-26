package com.oldi.football.ui.stats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oldi.football.OnSwipeTouchListener;
import com.oldi.football.R;

public class WebViewerActivity extends AppCompatActivity {
    private boolean swipeLR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewer);

        WebView web = findViewById(R.id.webview);
        web.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //progDailog.show();
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                //progDailog.dismiss();
            }
        });

        //View scroll = findViewById(R.id.webview_scroll);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CookieManager.getInstance().setAcceptCookie(true);
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(web, true);
            }

            WebSettings webSettings = web.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setMediaPlaybackRequiresUserGesture(false);
            web.setWebChromeClient(new WebChrome(this));
            System.out.println("LINK_________________________________________________- " + extras.getString("link"));
            web.loadUrl(extras.getString("link"));
            System.out.println("LOADED");
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (android.os.Build.VERSION.SDK_INT > 29) {
            if (swipeLR) {
                overridePendingTransition(R.anim.slide_lr, R.anim.slide_lr_out);
            } else {
                overridePendingTransition(R.anim.slide_rl, R.anim.slide_rl_out);
            }
        }
        System.gc();
    }


    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}