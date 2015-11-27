package com.mummyding.app.leisure.cache.cache;

import android.content.Context;
import android.database.Cursor;
import android.nfc.tech.NfcB;

import com.mummyding.app.leisure.cache.table.DailyTable;
import com.mummyding.app.leisure.cache.table.NewsTable;
import com.mummyding.app.leisure.model.news.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-26.
 */
public class NewsCache extends BaseCache{

    private NewsTable table;
    private List<Object> newsList = new ArrayList<>();

    public NewsCache(Context context) {
        super(context);
        table = new NewsTable();
    }

    @Override
    protected void putData(List<? extends Object> list) {
        db.execSQL(mHelper.DROP_TABLE+table.NAME);
        db.execSQL(table.CREATE_TABLE);
        for(Object object : list){
            NewsBean newsBean = (NewsBean) object;
            values.put(NewsTable.TITLE,newsBean.getTitle());
            values.put(NewsTable.DESCRIPTION,newsBean.getDescription());
            values.put(NewsTable.PUBTIME,newsBean.getPubTime());
            values.put(NewsTable.IS_COLLECTED,newsBean.getIs_collected());
            db.insert(NewsTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(Object object) {
        NewsBean newsBean = (NewsBean) object;
        values.put(NewsTable.TITLE,newsBean.getTitle());
        values.put(NewsTable.DESCRIPTION,newsBean.getDescription());
        values.put(NewsTable.PUBTIME,newsBean.getPubTime());
        db.insert(NewsTable.COLLECTION_NAME, null, values);
    }

    @Override
    public List<Object> loadFromCache() {
        Cursor cursor = query(table.NAME);
        while (cursor.moveToNext()) {
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle(cursor.getString(NewsTable.ID_TITLE));
            newsBean.setDescription(cursor.getString(NewsTable.ID_DESCRIPTION));
            newsBean.setPubTime(cursor.getString(NewsTable.ID_PUBTIME));
            newsBean.setIs_collected(cursor.getInt(NewsTable.ID_IS_COLLETED));
            newsList.add(newsBean);
        }
        cursor.close();
        return newsList;
    }
}
