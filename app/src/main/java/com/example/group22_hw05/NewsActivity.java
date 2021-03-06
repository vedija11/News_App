package com.example.group22_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    ProgressDialog progressDialog;
    ArrayList<News> newsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listView2 = findViewById(R.id.listView2);
        progressDialog = new ProgressDialog(this);

        Intent displayNewsIntent = getIntent();
        String newsHeading = displayNewsIntent.getStringExtra("SourceName");
        setTitle(newsHeading);
        Log.d("newsHeading......", newsHeading);
        String newsID = displayNewsIntent.getStringExtra("SourceID");
        Log.d("newsID......", newsID);

        new GetDataAsync().execute("https://newsapi.org/v2/top-headlines?sources=" + newsID + "&apiKey=a8e636e2de8a49889813d4d67900394d");
        Log.d("url", "https://newsapi.org/v2/top-headlines?sources=" + newsID + "&apiKey=a8e636e2de8a49889813d4d67900394d");


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
                        news.url = articlesJSONObject.getString("url");
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
        protected void onPostExecute(final ArrayList<News> newsData) {
            hideProgressDialog();
            if (newsData != null || newsData.toString() != "[]") {
                Log.d("newsData", String.valueOf(newsData));
                NewsAdapter adapter = new NewsAdapter(NewsActivity.this, R.layout.news_item, newsData);
                listView2.setAdapter(adapter);
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent webIntent = new Intent(NewsActivity.this, WebViewActivity.class);
                        webIntent.putExtra("webData", newsData.get(position).url);
                        startActivity(webIntent);
                    }
                });
            } else {
                Log.d("demo", "null newsData");
            }
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog("Loading Stories...");
        }
    }
    private void showProgressDialog(String message) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }

}
