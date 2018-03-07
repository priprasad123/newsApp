package com.example.priprasad.newsapplication;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String url= "https://newsapi.org/v2/top-headlines?country=us&apiKey=6fcf9cfe37024ac4b3ccdd0187c91568";
    ListView listNews;
    ProgressBar loader;
    CustomAdapter adapter;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listNews = (ListView) findViewById(R.id.listNews);
        loader = (ProgressBar) findViewById(R.id.loader);
        listNews.setEmptyView(loader);
        new Asynctask().execute();
    }

    public class Asynctask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String xml) {
            try {
                JSONObject jsonResponse = new JSONObject(xml);
                JSONArray jsonArray = jsonResponse.getJSONArray("articles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    map.put(KEY_AUTHOR, jsonObject.getString(KEY_AUTHOR));
                    map.put(KEY_TITLE, jsonObject.getString(KEY_TITLE));
                    map.put(KEY_DESCRIPTION, jsonObject.getString(KEY_DESCRIPTION));
                    map.put(KEY_URL, jsonObject.getString(KEY_URL));
                    map.put(KEY_URLTOIMAGE, jsonObject.getString(KEY_URLTOIMAGE));
                    map.put(KEY_PUBLISHEDAT, jsonObject.getString(KEY_PUBLISHEDAT));
                    dataList.add(map);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
            }

            adapter = new CustomAdapter(MainActivity.this,  dataList);
            listNews.setAdapter(adapter);
            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Detail_Activity.class);
                    i.putExtra("url", dataList.get(position).get(KEY_URL));
                    startActivity(i);
                }
            });

        }

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection urlconnection = null;

            try {
                URL urldata = new URL(url);
                urlconnection = (HttpURLConnection) urldata.openConnection();
                urlconnection.setRequestMethod("GET");
                return streamtostring(urlconnection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlconnection != null) {
                    urlconnection.disconnect();
                }
            }
            return null;
        }
    }


    String streamtostring(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String data;
        StringBuffer response = new StringBuffer();
        while ((data = bufferedReader.readLine()) != null) {
            response.append(data);
            response.append('\r');

        }
        if (stream != null) {
            stream.close();
        }
        return response.toString();

    }


}

