package com.example.priprasad.newsapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by priprasad on 07-03-2018.
 */

public class Detail_Activity extends Activity {

    LinearLayout loader;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        loader = (LinearLayout) findViewById(R.id.loader);
        WebView webView = (WebView) findViewById(R.id.webLoader);
        try {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageCommitVisible(WebView view, String url) {
                    super.onPageCommitVisible(view, url);
                    loader.setVisibility(View.INVISIBLE);
                }
            });
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
