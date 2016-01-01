/*
 *  *  Copyright (C) 2015 MummyDing
 *  *
 *  *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  *  Leisure is free software: you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation, either version 3 of the License, or
 *  *  (at your option) any later version.
 *  *                             ｀
 *  *  Leisure is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *  *
 *  *  You should have received a copy of the GNU General Public License
 *  *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.ui.daily;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.daily.DailyDetailsBean;
import com.mummyding.app.leisure.model.daily.StoryBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class DailyDetailsActivity extends AppCompatActivity {

    private SimpleDraweeView simpleDraweeView;
    private Toolbar toolbar;
    private WebView webView;
    private ProgressBar progressBar;
    private DailyDetailsBean dailyDetailsBean;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_details);
        getData();
        initView();
    }
    private void getData(){
        url = getIntent().getStringExtra(getString(R.string.id_url));

    }
    private void initView(){
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.ivImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        setSupportActionBar(toolbar);
        loadDataFromNet();
    }

    private void loadDataFromNet(){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                Gson gson = new Gson();
                dailyDetailsBean = gson.fromJson(res, DailyDetailsBean.class);
                handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    break;
                case CONSTANT.ID_SUCCESS:
                    getSupportActionBar().setTitle(dailyDetailsBean.getTitle());
                    simpleDraweeView.setImageURI(Uri.parse(dailyDetailsBean.getImage()));
                    webView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"dailycss.css\" />"+dailyDetailsBean.getBody(), "text/html", "utf-8", null);
                    break;
            }
            progressBar.setVisibility(View.GONE);
            return true;
        }
    });
}
