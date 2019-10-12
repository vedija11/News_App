package com.example.group22_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent displayNewsIntent = getIntent();
        ArrayList<String> newsList = displayNewsIntent.getStringArrayListExtra("Source");
        Log.d("NewsActivity", newsList.toString());
        String news = newsList.toString();

    }


}
