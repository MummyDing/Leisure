package com.mummyding.app.leisure.cache.cache;

import java.util.List;

/**
 * Created by mummyding on 15-12-3.
 */
public interface ICache<T>{
    void addToCollection(T object);
    void execSQL(String sql);
    List<T> getmList();
    boolean hasData();
    void load();
    void loadFromCache();
    void cache();
}
