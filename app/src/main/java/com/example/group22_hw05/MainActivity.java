package com.example.group22_hw05;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class MainActivity extends AppCompatActivity {

    ListView listViewMain;
    ProgressDialog progressDialog;
    ArrayList<Source> result = new ArrayList<>();
    ArrayList<String> sourceName = new ArrayList<>();
    ArrayList<String> sourceID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MainActivity");

        listViewMain = findViewById(R.id.listViewMain);
        progressDialog = new ProgressDialog(this);

        if (isConnected()) {

            new GetNewsAsync().execute("https://newsapi.org/v2/sources?apiKey=a8e636e2de8a49889813d4d67900394d");

        } else
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetNewsAsync extends AsyncTask<String, Void, ArrayList<Source>> {
        @Override
        protected void onPreExecute() {
            showProgressDialog("Loading Sources...");
        }

        @Override
        protected ArrayList<Source> doInBackground(String... params) {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray sources = root.getJSONArray("sources");
                    for (int i = 0; i < sources.length() && i < 20; i++) {
                        JSONObject sourcesJSONObject = sources.getJSONObject(i);
                        Source source = new Source();
                        source.id = sourcesJSONObject.getString("id");
                        source.name = sourcesJSONObject.getString("name");

                        result.add(source);
                        sourceName.add(source.name);
                        sourceID.add(source.id);
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
            return result;
        }


        @Override
        protected void onPostExecute(final ArrayList<Source> result) {
            hideProgressDialog();
            if (result != null) {

                Log.d("result", String.valueOf(result));
                Log.d("sourceName", String.valueOf(sourceName));
                Log.d("sourceID", String.valueOf(sourceID));

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, sourceName);
                listViewMain.setAdapter(adapter);
                listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Log.d("Demo", "Clicked item " + (position+1) + ", news "+ sourceName.toArray()[position]);
                        //showProgressDialog("Loading Stories...");
                        Intent newsIntent = new Intent(MainActivity.this, NewsActivity.class);
                        newsIntent.putExtra("SourceName", (String) sourceName.toArray()[position]);
                        newsIntent.putExtra("SourceID", (String) sourceID.toArray()[position]);
                        startActivity(newsIntent);
                    }
                });
            } else {
                Log.d("demo", "null result");
            }
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
