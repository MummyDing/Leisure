package com.mummyding.app.leisure.cache.cache.Collection;

import com.mummyding.app.leisure.cache.cache.ICache;
import com.mummyding.app.leisure.model.science.ArticleBean;

import java.util.List;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionScienceCache implements ICache<ArticleBean>{
    @Override
    public void addToCollection(ArticleBean object) {

    }

    @Override
    public void execSQL(String sql) {

    }

    @Override
    public List<ArticleBean> getmList() {
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
