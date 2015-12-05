package com.mummyding.app.leisure.database.cache.collection;

import android.database.Cursor;
import android.os.Handler;

import com.mummyding.app.leisure.database.cache.cache.BaseCollectionCache;
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.model.daily.DailyBean;
import com.mummyding.app.leisure.support.CONSTANT;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionDailyCache extends BaseCollectionCache<DailyBean>{
    private DailyTable table;

    public CollectionDailyCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public synchronized void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            DailyBean dailyBean = new DailyBean();
            dailyBean.setTitle(cursor.getString(table.ID_TITLE));
            dailyBean.setDescription(cursor.getString(table.ID_DESCRIPTION));
            dailyBean.setImage(cursor.getString(table.ID_IMAGE));
            dailyBean.setInfo(cursor.getString(table.ID_INFO));
            mList.add(dailyBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
