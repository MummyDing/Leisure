package com.mummyding.app.leisure.database.cache.collection;

import android.database.Cursor;
import android.os.Handler;

import com.mummyding.app.leisure.database.cache.cache.BaseCollectionCache;
import com.mummyding.app.leisure.database.table.ReadingTable;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.support.CONSTANT;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionReadingCache extends BaseCollectionCache<BookBean> {


    private ReadingTable table;

    public CollectionReadingCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(table.ID_TITLE));
            bookBean.setSummary(cursor.getString(table.ID_SUMMARY));
            bookBean.setImage(cursor.getString(table.ID_INFO));
            bookBean.setImage(cursor.getString(table.ID_IMAGE));
            bookBean.setInfo(cursor.getString(table.ID_INFO));
            bookBean.setEbook_url(cursor.getString(table.ID_EBOOK_URL));
            bookBean.setAuthor_intro(cursor.getString(table.ID_AUTHOR_INTRO));
            bookBean.setCatalog(cursor.getString(table.ID_CATALOG));
            mList.add(bookBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
