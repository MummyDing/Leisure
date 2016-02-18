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

package com.mummyding.app.leisure.database.cache.cache;

import android.database.Cursor;
import android.os.Handler;

import com.google.gson.Gson;
import com.mummyding.app.leisure.api.DailyApi;
import com.mummyding.app.leisure.database.cache.BaseCache;
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.model.daily.DailyBean;
import com.mummyding.app.leisure.model.daily.StoryBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xml.sax.DTDHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-26.
 * Daily Cache. function:<br>
 * <li>Get Daily data from ZhiHu api via net</li>
 * <li>Cache Daily data to database if it updates</li>
 * <li>Load Daily data from database</li>
 * <li>Notify to fragment/activity if work completed</li>
 * @author MummyDing
 * @version Leisure 1.0
 */
public class DailyCache extends BaseCache<StoryBean> {


    public DailyCache(Handler handler) {
        super(handler);
    }

    @Override
    protected void putData() {
        db.execSQL(mHelper.DELETE_TABLE_DATA + DailyTable.NAME);
       // db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<mList.size();i++){
            StoryBean storyBean = mList.get(i);
            values.put(DailyTable.TITLE,storyBean.getTitle());
            values.put(DailyTable.ID, storyBean.getId());
            values.put(DailyTable.IMAGE,storyBean.getImages()[0]);
            values.put(DailyTable.BODY,storyBean.getBody());
            values.put(DailyTable.LARGEPIC,storyBean.getLargepic());
            values.put(DailyTable.IS_COLLECTED,storyBean.isCollected());
            db.insert(DailyTable.NAME, null, values);
        }
        db.execSQL(DailyTable.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(StoryBean storyBean) {
        values.put(DailyTable.TITLE,storyBean.getTitle());
        values.put(DailyTable.ID, storyBean.getId());
        values.put(DailyTable.IMAGE, storyBean.getImages()[0]);
        values.put(DailyTable.BODY, storyBean.getBody() == null ? "":storyBean.getBody());
        values.put(DailyTable.LARGEPIC, storyBean.getLargepic());
        db.insert(DailyTable.COLLECTION_NAME, null, values);
    }
    @Override
    public synchronized void loadFromCache() {
        mList.clear();
        String sql = "select * from "+DailyTable.NAME+" order by "+DailyTable.ID+" desc";

        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            StoryBean storyBean = new StoryBean();
            storyBean.setTitle(cursor.getString(DailyTable.ID_TITLE));
            storyBean.setId(cursor.getInt(DailyTable.ID_ID));
            storyBean.setImages(new String[]{cursor.getString(DailyTable.ID_IMAGE)});
            storyBean.setBody(cursor.getString(DailyTable.ID_BODY));
            storyBean.setLargepic(cursor.getString(DailyTable.ID_LARGEPIC));
            storyBean.setCollected(cursor.getInt(DailyTable.ID_IS_COLLECTED));
            mList.add(storyBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
        cursor.close();
    }
    // load today's data
    public void load(){
        Request.Builder builder = new Request.Builder();
        builder.url(DailyApi.daily_url);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful() == false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    return;
                }
                String res = response.body().string();

                ArrayList<String> collectionTitles = new ArrayList<String>();
                for(int i = 0 ; i<mList.size() ; i++ ){
                    if(mList.get(i).isCollected() == 1){
                        collectionTitles.add(mList.get(i).getTitle());
                    }
                }

                List<StoryBean> oldList = new ArrayList<StoryBean>();
                List<StoryBean> newList = new ArrayList<StoryBean>();

                for(StoryBean storyBean:mList){
                    oldList.add(storyBean);
                }

                Gson gson = new Gson();
                DailyBean dailyBean = gson.fromJson(res, DailyBean.class);
                StoryBean[] storyBeans = dailyBean.getStories();
                for (StoryBean storyBeen : storyBeans) {
                    newList.add(storyBeen);
                }

                loadOld(dailyBean.getDate(),oldList,newList);

            }
        });
    }

    // load yesterday's data
    private  void loadOld(String date, final List<StoryBean> oldList, final List<StoryBean> newList){
        Request.Builder builder = new Request.Builder();
        builder.url(DailyApi.daily_old_url + date);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful() == false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    return;
                }
                String res = response.body().string();

                ArrayList<Integer> collectionIDs = new ArrayList<Integer>();
                for(int i = 0 ; i<oldList.size() ; i++ ){
                    if(oldList.get(i).isCollected() == 1){
                        collectionIDs.add(oldList.get(i).getId());
                    }
                }

                // clear old data
                mList.clear();


                Gson gson = new Gson();
                StoryBean[] storyBeans = (gson.fromJson(res, DailyBean.class)).getStories();
                for (StoryBean storyBeen : storyBeans) {
                    newList.add(storyBeen);
                }

                for(StoryBean storyBean: newList){
                    mList.add(storyBean);
                }

                // setCollection flag
                for(Integer id:collectionIDs){
                    for(int i=0 ; i<mList.size() ; i++){
                        if(id.equals(mList.get(i).getId())){
                            mList.get(i).setCollected(1);
                        }
                    }
                }

                // notify
                mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
            }
        });
    }

}
