package com.mummyding.app.leisure.cache.cache.Collection;

import com.mummyding.app.leisure.cache.cache.ICache;
import com.mummyding.app.leisure.model.reading.BookBean;

import java.util.List;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionReadingCache implements ICache<BookBean>{


    @Override
    public void addToCollection(BookBean object) {

    }

    @Override
    public void execSQL(String sql) {

    }

    @Override
    public List<BookBean> getmList() {
        return null;
    }

    @Override
    public boolean hasData() {
        return false;
    }

    @Override
    public void load() {

    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void cache() {

    }
}
