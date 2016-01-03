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

import com.mummyding.app.leisure.database.cache.BaseCache;
import com.mummyding.app.leisure.database.table.NewsTable;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.sax.SAXNewsParse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mummyding on 15-11-26.
 * News Cache. function:<br>
 * <li>Get News data from XinHuaNet api via net</li>
 * <li>Cache News data to database if it updates</li>
 * <li>Load News data from database</li>
 * <li>Notify to fragment/activity if work completed</li>
 * @author MummyDing
 * @version Leisure 1.0
 */
public class NewsCache extends BaseCache<NewsBean> {

    private NewsTable table;

    public NewsCache( Handler handler, String category, String url) {
        super(handler, category, url);
    }


    @Override
    protected void putData() {
        db.execSQL(mHelper.DELETE_TABLE_DATA +table.NAME+" where "+table.CATEGORY+"=\'"+mCategory+"\'");
        for(int i=0;i<mList.size();i++){
            NewsBean newsBean =  mList.get(i);
            values.put(NewsTable.TITLE,newsBean.getTitle());
            values.put(NewsTable.DESCRIPTION,newsBean.getDescription());
            values.put(NewsTable.PUBTIME,newsBean.getPubTime());
            values.put(NewsTable.IS_COLLECTED,newsBean.getIs_collected());
            values.put(NewsTable.LINK,newsBean.getLink());
            values.put(NewsTable.CATEGORY,mCategory);
            db.insert(NewsTable.NAME,null,values);
        }
    }


    @Override
    protected void putData(NewsBean newsBean) {
        values.put(NewsTable.TITLE,newsBean.getTitle());
        values.put(NewsTable.DESCRIPTION,newsBean.getDescription());
        values.put(NewsTable.PUBTIME,newsBean.getPubTime());
        values.put(NewsTable.LINK,newsBean.getLink());
        db.insert(NewsTable.COLLECTION_NAME, null, values);
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
        while (cursor.moveToNext()) {
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle(cursor.getString(NewsTable.ID_TITLE));
            newsBean.setDescription(cursor.getString(NewsTable.ID_DESCRIPTION));
            newsBean.setPubTime(cursor.getString(NewsTable.ID_PUBTIME));
            newsBean.setLink(cursor.getString(NewsTable.ID_LINK));
            newsBean.setIs_collected(cursor.getInt(NewsTable.ID_IS_COLLECTED));
            mList.add(newsBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
       // cursor.close();
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
                InputStream is =
                        new ByteArrayInputStream(response.body().string().getBytes(Charset.forName("UTF-8")));
                try {
                    ArrayList<String> collectionTitles = new ArrayList<String>();
                    for(int i = 0 ; i<mList.size() ; i++ ){
                        if(mList.get(i).getIs_collected() == 1){
                            collectionTitles.add(mList.get(i).getTitle());
                        }
                    }

                    mList.clear();
                    mList.addAll(SAXNewsParse.parse(is));
                    for(String title:collectionTitles){
                        for(int i=0 ; i<mList.size() ; i++){
                            if(title.equals(mList.get(i).getTitle())){
                                mList.get(i).setIs_collected(1);
                            }
                        }
                    }
                    is.close();
                    mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
