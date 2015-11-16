package com.mummyding.app.leisure.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mummyding.app.leisure.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textView;
    private boolean isLoading = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initData();
    }
    private void initData(){
        webView = (WebView) findViewById(R.id.webview);
        textView = (TextView) findViewById(R.id.text_notify);
        final String url = getIntent().getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        /*webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled();*/
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                textView.setVisibility(View.GONE);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(isLoading) {
                    textView.setText("正在加载..." + newProgress*4 + "%");
                    if(newProgress > 25) {
                        isLoading =false;
                        textView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
