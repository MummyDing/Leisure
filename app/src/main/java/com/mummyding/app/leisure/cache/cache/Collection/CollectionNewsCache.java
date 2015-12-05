package com.mummyding.app.leisure.cache.cache.Collection;

import android.database.Cursor;
import android.os.Handler;

import com.mummyding.app.leisure.cache.cache.BaseCollectionCache;
import com.mummyding.app.leisure.cache.cache.ICache;
import com.mummyding.app.leisure.cache.table.NewsTable;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.support.CONSTANT;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionNewsCache extends BaseCollectionCache<NewsBean>{

    private NewsTable table;

    public CollectionNewsCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle(cursor.getString(table.ID_TITLE));
            newsBean.setDescription(cursor.getString(table.ID_DESCRIPTION));
            newsBean.setLink(cursor.getString(table.ID_LINK));
            newsBean.setPubTime(cursor.getString(table.ID_PUBTIME));
            mList.add(newsBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
