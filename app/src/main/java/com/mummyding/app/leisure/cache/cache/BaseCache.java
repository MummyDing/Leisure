package com.mummyding.app.leisure.cache.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mummyding.app.leisure.cache.DatabaseHelper;

import java.util.List;

/**
 * Created by mummyding on 15-11-26.
 */
public abstract class BaseCache {
    protected Context mContext;
    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;
    protected ContentValues values;
    protected BaseCache(Context context){
        mContext = context;
        mHelper = DatabaseHelper.instance(mContext);
    }

    protected abstract void putData(List<? extends Object> list,String category);
    protected abstract void putData(Object object);
    public  void cache(List<? extends Object> list,String category){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData(list,category);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public void addToCollection(Object object){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData(object);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public void execSQL(String sql){
        db = mHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
    public abstract List< Object> loadFromCache(String category);
    protected Cursor query(String sql){
        return mHelper.getReadableDatabase().rawQuery(sql,null);
    }
}
