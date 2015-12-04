package com.mummyding.app.leisure.cache.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.mummyding.app.leisure.cache.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-12-3.
 */
public abstract class BaseCollectionCache<T> implements ICache<T>{

    protected Context mContext;
    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;

    protected ContentValues values;

    protected List<T> mList = new ArrayList<>();

    protected Handler mHandler;


    @Override
    public void addToCollection(T object) {

    }

    @Override
    public void execSQL(String sql) {

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
