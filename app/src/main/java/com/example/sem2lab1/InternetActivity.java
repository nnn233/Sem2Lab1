package com.example.sem2lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

public class InternetActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        SimpleWebViewClient webViewClient = new SimpleWebViewClient(this);
        webView.setWebViewClient(webViewClient);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String link = extras.getString("link");
            try {
                webView.loadUrl(link);
            } catch (Exception e) {
                Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}