/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mummyding.app.leisure.ui.reading;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.database.cache.cache.ReadingCache;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.ReadingAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;
public class SearchBooksActivity extends AppCompatActivity implements SensorEventListener {

    private Toolbar toolbar;
    private static String url;
    private int mLang = -1;

    private SensorManager mSensorManager;
    private boolean isShakeMode = false;
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

        setContentView(R.layout.activity_reading);
        initData();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }
    private void initData(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(Settings.searchID == 0) {
            url = ReadingApi.searchByText + getIntent().getStringExtra(getString(R.string.id_search_text));
        }else if(Settings.searchID == 1){
            url = ReadingApi.searchByTag + getIntent().getStringExtra(getString(R.string.id_search_text));
        }
        BookListFragment fragment = new BookListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }

    public static class BookListFragment extends BaseListFragment{

        @Override
        protected boolean setHeaderTab() {
            return false;
        }

        @Override
        protected boolean setRefreshView() {
            return false;
        }

        @Override
        protected void onCreateCache() {
            cache = new ReadingCache(handler,null,new String[]{url});
        }

        @Override
        protected RecyclerView.Adapter bindAdapter() {
            return new ReadingAdapter(getContext(),cache);
        }

        @Override
        protected void loadFromNet() {
            cache.load();
        }

        @Override
        protected void loadFromCache() {
            loadFromNet();
        }

        @Override
        protected boolean hasData() {
            return cache.hasData();
        }

        @Override
        protected void getArgs() {

        }

        @Override
        protected boolean setCache() {
            return false;
        }
    }

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
