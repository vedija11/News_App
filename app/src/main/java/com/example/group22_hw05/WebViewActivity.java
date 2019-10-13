package com.example.group22_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import java.util.ArrayList;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);

        Intent displayURL = getIntent();
        ArrayList<String> webURL = displayURL.getStringArrayListExtra("webURL");
        String newsURL = webURL.get(2);
        webView.loadUrl(newsURL);
    }
}
