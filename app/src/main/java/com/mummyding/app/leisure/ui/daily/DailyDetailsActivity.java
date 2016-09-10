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
import android.support.v4.widget.NestedScrollView;
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
import com.mummyding.app.leisure.api.DailyApi;
import com.mummyding.app.leisure.database.cache.cache.DailyCache;
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.model.daily.DailyDetailsBean;
import com.mummyding.app.leisure.model.daily.StoryBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.DisplayUtil;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.ui.support.BaseDetailsActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DailyDetailsActivity extends BaseDetailsActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private boolean isShakeMode = false;
    private DailyDetailsBean dailyDetailsBean;
    private String url;
    private int id;
    private String imageUrl;
    private String title;
    private String body;
    private DailyCache cache;
    private StoryBean storyBean;

    @Override
    protected void onDataRefresh() {
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
                cache.execSQL(DailyTable.updateBodyContent(DailyTable.NAME,dailyDetailsBean.getTitle(),dailyDetailsBean.getBody()));
                cache.execSQL(DailyTable.updateBodyContent(DailyTable.COLLECTION_NAME,dailyDetailsBean.getTitle(),dailyDetailsBean.getBody()));
                cache.execSQL(DailyTable.updateLargePic(DailyTable.NAME,dailyDetailsBean.getTitle(),dailyDetailsBean.getImage()));
                cache.execSQL(DailyTable.updateLargePic(DailyTable.COLLECTION_NAME,dailyDetailsBean.getTitle(),dailyDetailsBean.getImage()));


                imageUrl = dailyDetailsBean.getImage();
                body = dailyDetailsBean.getBody();

                handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    private void getData(){
        storyBean = new StoryBean();
        url = getIntent().getStringExtra(getString(R.string.id_url));
        id = getIntent().getIntExtra(getString(R.string.id_id),0);
        body = getIntent().getStringExtra(getString(R.string.id_body));
        title = getIntent().getStringExtra(getString(R.string.id_title));
        imageUrl = getIntent().getStringExtra(getString(R.string.id_imageurl));
        isCollected = getIntent().getBooleanExtra(getString(R.string.id_collection),false);

        storyBean.setId(id);
        storyBean.setBody(body);
        storyBean.setTitle(title);
        storyBean.setLargepic(imageUrl);
        storyBean.setImages(new String[]{getIntent().getStringExtra(getString(R.string.id_small_image))});

    }

    protected void initView(){
        super.initView();
        cache = new DailyCache(handler);
        if(body == "" || body == null||imageUrl == null || imageUrl == "") {
            onDataRefresh();
        }else{
            handler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
        }
    }

    @Override
    protected void removeFromCollection() {
        cache.execSQL(DailyTable.updateCollectionFlag(storyBean.getTitle(),0));
        cache.execSQL(DailyTable.deleteCollectionFlag(storyBean.getTitle()));
    }

    @Override
    protected void addToCollection() {
        cache.execSQL(DailyTable.updateCollectionFlag(storyBean.getTitle(),1));
        cache.addToCollection(storyBean);
    }

    @Override
    protected String getShareInfo() {
        return "["+title+"]:"+ DailyApi.daily_story_base_url+id+" ("+getString(R.string.text_share_from)+getString(R.string.app_name)+")";
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    hideLoading();
                    displayNetworkError();
                    break;
                case CONSTANT.ID_SUCCESS:

                case CONSTANT.ID_FROM_CACHE:
                    // fix issue #13
                    if(HttpUtil.isWIFI == true || Settings.getInstance().getBoolean(Settings.NO_PIC_MODE, false) == false) {
                        setMainContentBg(imageUrl);
                    }
                    scrollView.setVisibility(View.VISIBLE);
                    scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                        }
                    });
                    contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"dailycss.css\" />"+ body, "text/html", "utf-8", null);
                    hideLoading();
                    break;
            }
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
