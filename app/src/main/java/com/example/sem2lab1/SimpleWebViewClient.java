package com.example.sem2lab1;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SimpleWebViewClient extends WebViewClient {
    private Activity activity = null;

    public SimpleWebViewClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
        activity.startActivity(intent);
        return true;
    }
}