package com.mummyding.app.leisure.cache.cache;

import android.content.Context;
import android.database.Cursor;

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
    protected void putData(List<? extends Object> list,String category) {
        db.execSQL(mHelper.DROP_TABLE+table.NAME);
        db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<list.size();i++){
            NewsBean newsBean = (NewsBean) list.get(i);
            values.put(NewsTable.TITLE,newsBean.getTitle());
            values.put(NewsTable.DESCRIPTION,newsBean.getDescription());
            values.put(NewsTable.PUBTIME,newsBean.getPubTime());
            values.put(NewsTable.IS_COLLECTED,newsBean.getIs_collected());
            values.put(NewsTable.LINK,newsBean.getLink());
            values.put(NewsTable.CATEGORY,category);
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
        values.put(NewsTable.LINK,newsBean.getLink());
        db.insert(NewsTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized List<Object> loadFromCache(String category) {
        String sql = null;
        if(category == null){
            sql = "select * from "+table.NAME;
        }else {
            sql = "select * from "+table.NAME +" where "+table.CATEGORY+"=\'"+category+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()) {
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle(cursor.getString(NewsTable.ID_TITLE));
            newsBean.setDescription(cursor.getString(NewsTable.ID_DESCRIPTION));
            newsBean.setPubTime(cursor.getString(NewsTable.ID_PUBTIME));
            newsBean.setLink(cursor.getString(NewsTable.ID_LINK));
            newsBean.setIs_collected(cursor.getInt(NewsTable.ID_IS_COLLECTED));
            newsList.add(newsBean);
        }
       // cursor.close();
        return newsList;
    }
}
