package com.mummyding.app.leisure.database.cache.cache;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.database.DatabaseHelper;
import com.mummyding.app.leisure.database.cache.ICache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-12-3.
 */
public abstract class BaseCollectionCache<T> implements ICache<T> {

    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;


    protected List<T> mList = new ArrayList<>();

    protected Handler mHandler;

    public BaseCollectionCache(Handler mHandler) {
        this.mHandler = mHandler;
        mHelper = DatabaseHelper.instance(LeisureApplication.AppContext);
    }

    @Override
    public void addToCollection(T object) {

    }

    @Override
    public void execSQL(String sql) {
        db = mHelper.getWritableDatabase();
        db.execSQL(sql);
    }

    @Override
    public List<T> getmList() {
        return mList;
    }

    @Override
    public boolean hasData() {
        return !mList.isEmpty();
    }

    @Override
    public void load() {

    }


    @Override
    public void cache() {

    }

    protected Cursor query(String sql){
        return mHelper.getReadableDatabase().rawQuery(sql,null);
    }

}
