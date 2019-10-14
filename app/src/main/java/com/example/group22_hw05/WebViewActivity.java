package com.example.group22_hw05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    ConstraintLayout CLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        CLayout = findViewById(R.id.CLayout);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.INVISIBLE);
        Intent displayURL = getIntent();
        String webURL = displayURL.getStringExtra("webData");
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                progressBar.setProgress(progress);
                if(progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        webView.loadUrl(webURL);
    }
}
