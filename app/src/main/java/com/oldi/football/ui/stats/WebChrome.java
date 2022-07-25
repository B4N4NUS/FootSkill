package com.oldi.football.ui.stats;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

public class WebChrome extends WebChromeClient {
    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private int mOriginalOrientation;
    private int mOriginalSystemUIVisibility;

    WebViewerActivity activity;

    public WebChrome(WebViewerActivity act) {
        activity = act;
    }

    public Bitmap getDefaultVideoPoster() {
        if (mCustomView == null) {
            return null;
        } else {
            return null;
        }
    }

    public void onHideCustomView() {
        ((FrameLayout)activity.getWindow().getDecorView()).removeView(this.mCustomView);
        this.mCustomView = null;
        activity.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUIVisibility);
        activity.setRequestedOrientation(this.mOriginalOrientation);
        this.mCustomViewCallback.onCustomViewHidden();
        this.mCustomViewCallback = null;
    }

    public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback param) {
        if (this.mCustomView != null) {
            onHideCustomView();
            return;
        }
        this.mCustomView = paramView;
        this.mOriginalSystemUIVisibility =activity.getWindow().getDecorView().getSystemUiVisibility();
        this.mOriginalOrientation = activity.getRequestedOrientation();
        this.mCustomViewCallback = param;
        ((FrameLayout)activity.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
        activity.getWindow().getDecorView().setSystemUiVisibility(3846);

    }
}
