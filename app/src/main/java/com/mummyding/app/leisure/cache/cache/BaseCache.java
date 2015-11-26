package com.mummyding.app.leisure.cache.cache;

import android.content.Context;

import com.mummyding.app.leisure.cache.DatabaseHelper;

import java.util.List;

/**
 * Created by mummyding on 15-11-26.
 */
public abstract class BaseCache {
    protected Context mContext;
    protected DatabaseHelper mHelper;
    protected BaseCache(Context context){
        mContext = context;
        mHelper = DatabaseHelper.instance(mContext);
    }
    public abstract void cache(List<Object> list);
    public abstract void loadFromCache();
}
