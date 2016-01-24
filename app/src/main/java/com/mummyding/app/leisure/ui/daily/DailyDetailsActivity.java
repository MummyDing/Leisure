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

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.database.cache.cache.DailyCache;
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.model.daily.DailyDetailsBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class DailyDetailsActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private boolean isShakeMode = false;
    private int mLang = -1;
    private SimpleDraweeView simpleDraweeView;
    private Toolbar toolbar;
    private WebView webView;
    private ProgressBar progressBar;
    private DailyDetailsBean dailyDetailsBean;
    private String url;
    private String title;
    private int id;
    private String imageUrl;
    private String body;
    private DailyCache cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Language
        mLang = Utils.getCurrentLanguage();
        if (mLang > -1) {
            Utils.changeLanguage(this, mLang);
        }

        //set Theme
        if(Settings.isNightMode){
            this.setTheme(R.style.NightTheme);
        }else{
            this.setTheme(R.style.DayTheme);
        }

        setContentView(R.layout.activity_daily_details);
        getData();
        initView();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }
    private void getData(){
        url = getIntent().getStringExtra(getString(R.string.id_url));
        id = getIntent().getIntExtra(getString(R.string.id_id),0);
        title = getIntent().getStringExtra(getString(R.string.id_title));
        body = getIntent().getStringExtra(getString(R.string.id_body));
        imageUrl = getIntent().getStringExtra(getString(R.string.id_imageurl));

    }
    private void initView(){
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.ivImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        cache = new DailyCache(handler);
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
                //fix issue #6
                webView.loadUrl(url);
                return false;
            }
        });

        /*
         cache web page
         */

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        if(Settings.isNightMode) {
            webView.setBackgroundColor(ContextCompat.getColor(this, R.color.night_primary));
        }
        if(HttpUtil.isWIFI == false) {
            webView.getSettings().setBlockNetworkImage(Settings.getInstance().getBoolean(Settings.NO_PIC_MODE, false));
        }else {
            // fix issue #13
            webView.getSettings().setBlockNetworkImage(false);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle(title);
        if(body == "" || body == null||imageUrl == null || imageUrl == "") {
            loadDataFromNet();
            Utils.DLog("enter");
        }else{
            handler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
        }
        progressBar.setVisibility(View.VISIBLE);
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
                Utils.DLog(res);
                Gson gson = new Gson();
                dailyDetailsBean = gson.fromJson(res, DailyDetailsBean.class);
                cache.execSQL(DailyTable.updateBodyContent(DailyTable.NAME,id,dailyDetailsBean.getBody()));
                cache.execSQL(DailyTable.updateBodyContent(DailyTable.COLLECTION_NAME,id,dailyDetailsBean.getBody()));
                cache.execSQL(DailyTable.updateLargePic(DailyTable.NAME,id,dailyDetailsBean.getImage()));
                cache.execSQL(DailyTable.updateLargePic(DailyTable.COLLECTION_NAME,id,dailyDetailsBean.getImage()));


                imageUrl = dailyDetailsBean.getImage();
                body = dailyDetailsBean.getBody();

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
                case CONSTANT.ID_FROM_CACHE:
                    // fix issue #13
                    if(HttpUtil.isWIFI == true || Settings.getInstance().getBoolean(Settings.NO_PIC_MODE, false) == false) {
                        simpleDraweeView.setImageURI(Uri.parse(imageUrl));
                    }
                    webView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"dailycss.css\" />"+body, "text/html", "utf-8", null);
                    break;
            }
            progressBar.setVisibility(View.GONE);
            return false;
        }
    });

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        isShakeMode = Settings.getInstance().getBoolean(Settings.SHAKE_TO_RETURN,true);
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(isShakeMode == false){
            return;
        }

        float value[] = event.values;
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if((Math.abs(value[0]) + Math.abs(value[1]) + Math.abs(value[2]))>CONSTANT.shakeValue){
                onBackPressed();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
