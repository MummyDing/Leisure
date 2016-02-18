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
import com.mummyding.app.leisure.database.cache.BaseCache;
import com.mummyding.app.leisure.database.table.ScienceTable;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.model.science.ScienceBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mummyding on 15-11-26.
 * Science Cache. function:<br>
 * <li>Get Article data from Guokr Scientific api via net</li>
 * <li>Cache Article data to database if it updates</li>
 * <li>Load Article data from database</li>
 * <li>Notify to fragment/activity if work completed</li>
 * @author MummyDing
 * @version Leisure 1.0
 */
public class ScienceCache extends BaseCache<ArticleBean> {

    private ScienceTable table;

    public ScienceCache(){
        super();
    }

    public ScienceCache(Handler handler, String category, String url) {
        super(handler, category, url);
    }


    @Override
    protected void putData() {
        db.execSQL(mHelper.DELETE_TABLE_DATA +table.NAME+" where "+table.CATEGORY+"=\'"+mCategory+"\'");
       // db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<mList.size();i++){
            ArticleBean articleBean = mList.get(i);
            values.put(ScienceTable.TITLE,articleBean.getTitle());
            values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
            values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
            values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
            values.put(ScienceTable.INFO,articleBean.getInfo());
            values.put(ScienceTable.URL,articleBean.getUrl());
            values.put(ScienceTable.CATEGORY,mCategory);
            values.put(ScienceTable.IS_COLLECTED,articleBean.getIs_collected());
            db.insert(ScienceTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(ArticleBean articleBean) {
        values.put(ScienceTable.TITLE,articleBean.getTitle());
        values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
        values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
        values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
        values.put(ScienceTable.URL,articleBean.getUrl());
        values.put(ScienceTable.INFO,articleBean.getInfo());
        db.insert(ScienceTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized void loadFromCache() {
        mList.clear();
        String sql = null;
        if(mCategory == null){
            sql = "select * from "+table.NAME;
        }else {
            sql = "select * from "+table.NAME +" where "+table.CATEGORY+"=\'"+mCategory+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            ArticleBean articleBean = new ArticleBean();
            articleBean.setTitle(cursor.getString(ScienceTable.ID_TITLE));
            articleBean.setSummary(cursor.getString(ScienceTable.ID_DESCRIPTION));
            if(articleBean.getImage_info() == null){
                Utils.DLog(" "+articleBean.getImage_info());
            }else {
                articleBean.getImage_info().setUrl(cursor.getString(ScienceTable.ID_IMAGE));
            }
            articleBean.setReplies_count(cursor.getInt(ScienceTable.ID_COMMENT_COUNT));
            articleBean.setInfo(cursor.getString(ScienceTable.ID_INFO));
            articleBean.setIs_collected(cursor.getInt(ScienceTable.ID_IS_COLLECTED));
            articleBean.setUrl(cursor.getString(ScienceTable.ID_URL));
            mList.add(articleBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
        cursor.close();
    }

    @Override
    public void load() {
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                if (response.isSuccessful() == false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    return;
                }

                ArrayList<String> collectionTitles = new ArrayList<String>();
                for(int i = 0 ; i<mList.size() ; i++ ){
                    if(mList.get(i).getIs_collected() == 1){
                        collectionTitles.add(mList.get(i).getTitle());
                    }
                }

                mList.clear();
                Gson gson = new Gson();
                ArticleBean[] articleBeans = (gson.fromJson(response.body().string(), ScienceBean.class)).getResult();
                for (ArticleBean articleBean : articleBeans) {
                    mList.add(articleBean);
                }

                for(String title:collectionTitles){
                    for(int i=0 ; i<mList.size() ; i++){
                        if(title.equals(mList.get(i).getTitle())){
                            mList.get(i).setIs_collected(1);
                        }
                    }
                }
                mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
            }
        });

    }
}
