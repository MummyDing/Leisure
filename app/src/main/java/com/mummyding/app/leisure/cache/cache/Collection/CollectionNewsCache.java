package com.mummyding.app.leisure.cache.cache.Collection;

import com.mummyding.app.leisure.cache.cache.ICache;
import com.mummyding.app.leisure.model.news.NewsBean;

import java.util.List;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionNewsCache implements ICache<NewsBean>{
    @Override
    public void addToCollection(NewsBean object) {

    }

    @Override
    public void execSQL(String sql) {

    }

    @Override
    public List<NewsBean> getmList() {
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
