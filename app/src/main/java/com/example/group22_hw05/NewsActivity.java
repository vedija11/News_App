package com.example.group22_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView listView2;
    ArrayList<News> newsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listView2 = findViewById(R.id.listView2);
        //newsData.add(new News("dad2","hiiiii3", "22/21/2012"));
        //newsData.add(new News("dad3","hiiiii4", "23/21/2012"));
        //newsData.add(new News("dad4","hiiiii5", "24/21/2012"));
        //newsData.add(new News("dad5","hiiiii6", "25/21/2012"));
        //newsData.add(new News("dad6","hiiiii7", "26/21/2012"));

        Intent displayNewsIntent = getIntent();
        //final ArrayList<Source> newsList = (ArrayList<Source>) displayNewsIntent.getSerializableExtra("Source");
        //Log.d("NewsActivity", newsList.toString());
        //String news = newsList.toString();

        new GetDataAsync().execute("https://newsapi.org/v2/top-headlines?sources=abc-news&apiKey=a8e636e2de8a49889813d4d67900394d");
        NewsAdapter adapter = new NewsAdapter(this, R.layout.news_item, newsData);
        listView2.setAdapter(adapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("news activity", "Clicked " + (position + 1));

            }
        });

    }

    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<News>> {
        @Override
        protected ArrayList<News> doInBackground(String... params) {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("articles");
                    for (int i = 0; i < articles.length() && i < 20; i++) {
                        JSONObject articlesJSONObject = articles.getJSONObject(i);
                        News news = new News();
                        news.author = articlesJSONObject.getString("author");
                        news.title = articlesJSONObject.getString("title");
                        news.urlToImage = articlesJSONObject.getString("urlToImage");
                        news.publishedAt = articlesJSONObject.getString("publishedAt");

                        newsData.add(news);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return newsData;
        }


        @Override
        protected void onPostExecute(ArrayList<News> newsData) {
            if (newsData != null) {
                Log.d("newsData", String.valueOf(newsData));

            } else {
                Log.d("demo", "null newsData");
            }
        }

    }

}
