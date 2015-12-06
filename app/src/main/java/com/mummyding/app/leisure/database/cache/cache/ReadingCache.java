

/*
 *
 *  * Copyright (C) 2015 MummyDing
 *  *
 *  * This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  * Leisure is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Leisure is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.database.cache.cache;


import android.database.Cursor;
import android.os.Handler;

import com.google.gson.Gson;
import com.mummyding.app.leisure.database.cache.BaseCache;
import com.mummyding.app.leisure.database.table.ReadingTable;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.model.reading.ReadingBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.io.IOException;

/**
 * Created by mummyding on 15-11-26.
 */
public class ReadingCache extends BaseCache<BookBean> {

    private ReadingTable table;

    public ReadingCache(Handler handler, String category, String[] urls) {
        super(handler, category, urls);
    }

    @Override
    protected void putData() {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<mList.size();i++){
            BookBean bookBean = mList.get(i);
            values.put(ReadingTable.TITLE,bookBean.getTitle());
            values.put(ReadingTable.INFO,bookBean.getInfo());
            values.put(ReadingTable.IMAGE, bookBean.getImage());
            values.put(ReadingTable.AUTHOR_INTRO,bookBean.getAuthor_intro() ==null ? "":bookBean.getAuthor_intro());
            values.put(ReadingTable.CATALOG,bookBean.getCatalog() == null? "":bookBean.getCatalog());
            values.put(ReadingTable.EBOOK_URL,bookBean.getEbook_url() == null?"":bookBean.getEbook_url());
            values.put(ReadingTable.CATEGORY,mCategory);
            values.put(ReadingTable.SUMMARY,bookBean.getSummary() == null?"":bookBean.getSummary());
            values.put(ReadingTable.IS_COLLECTED,bookBean.getIs_collected());
            db.insert(ReadingTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }


    @Override
    protected void putData(BookBean bookBean) {
        values.put(ReadingTable.TITLE,bookBean.getTitle());
        values.put(ReadingTable.INFO,bookBean.getInfo());
        values.put(ReadingTable.IMAGE, bookBean.getImage());
        values.put(ReadingTable.AUTHOR_INTRO,bookBean.getAuthor_intro() ==null ? "":bookBean.getAuthor_intro());
        values.put(ReadingTable.CATALOG,bookBean.getCatalog() == null? "":bookBean.getCatalog());
        values.put(ReadingTable.EBOOK_URL,bookBean.getEbook_url() == null?"":bookBean.getEbook_url());
        values.put(ReadingTable.SUMMARY,bookBean.getSummary() == null?"":bookBean.getSummary());

        db.insert(ReadingTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized void loadFromCache() {
        String sql = null;
        if(mCategory == null){
            sql = "select * from "+table.NAME;
        }else {
            sql = "select * from "+table.NAME +" where "+table.CATEGORY+"=\'"+mCategory+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(ReadingTable.ID_TITLE));
            bookBean.setImage(cursor.getString(ReadingTable.ID_IMAGE));
            bookBean.setInfo(cursor.getString(ReadingTable.ID_INFO));
            bookBean.setAuthor_intro(cursor.getString(ReadingTable.ID_AUTHOR_INTRO));
            bookBean.setCatalog(cursor.getString(ReadingTable.ID_CATALOG));
            bookBean.setEbook_url(cursor.getString(ReadingTable.ID_EBOOK_URL));
            bookBean.setSummary(cursor.getString(ReadingTable.ID_SUMMARY));
            bookBean.setIs_collected(cursor.getInt(ReadingTable.ID_IS_COLLECTED));
            mList.add(bookBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
        cursor.close();
    }

    @Override
    public void load() {

        for(int i = 0 ; i<mUrls.length; i++){
            String url = mUrls[i];
            Request.Builder builder = new Request.Builder();
            builder.url(url);
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
                    Gson gson = new Gson();
                    BookBean[] bookBeans = gson.fromJson(response.body().string(), ReadingBean.class).getBooks();
                    for (BookBean bookBean : bookBeans) {
                        mList.add(bookBean);
                    }
                    mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                }
            });
        }

    }
}
