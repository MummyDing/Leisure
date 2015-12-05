package com.mummyding.app.leisure.database.cache.cache;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;

import com.mummyding.app.leisure.database.cache.BaseCache;
import com.mummyding.app.leisure.database.table.NewsTable;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.sax.SAXNewsParse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mummyding on 15-11-26.
 */
public class NewsCache extends BaseCache<NewsBean> {

    private NewsTable table;

    public NewsCache(Context context, Handler handler, String category, String url) {
        super(context, handler, category, url);
    }


    @Override
    protected void putData() {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);
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
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
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

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                if (response.isSuccessful() == false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    return;
                }
                InputStream is =
                        new ByteArrayInputStream(response.body().string().getBytes(StandardCharsets.UTF_8));
                try {
                    mList.addAll(SAXNewsParse.parse(is));
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
