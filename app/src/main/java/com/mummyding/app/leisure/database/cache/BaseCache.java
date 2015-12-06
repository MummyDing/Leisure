/*
 *
 *  * Copyright (C) 2015 MummyDing
 *  *
 *  * This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  * Leisure is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Leisure is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.database.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.database.DatabaseHelper;
import com.mummyding.app.leisure.database.cache.ICache;
import com.mummyding.app.leisure.database.cache.cache.DailyCache;
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.support.CONSTANT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-26.
 */
public abstract class BaseCache<T> implements ICache<T> {
    protected Context mContext = LeisureApplication.AppContext;
    protected DatabaseHelper mHelper;
    protected SQLiteDatabase db;

    protected ContentValues values;
    protected List<T> mList = new ArrayList<>();

    protected Handler mHandler;
    protected String mCategory;

    protected String mUrl;
    protected String[] mUrls;

    protected BaseCache(Handler handler,String category){
        mHelper = DatabaseHelper.instance(mContext);
        mCategory = category;
        mHandler = handler;
    }
    protected BaseCache(Handler handler,String category,String[] urls){
        this(handler,category);
        mUrls = urls;
    }
    protected BaseCache(Handler handler,String category,String url){
        this(handler,category);
        mUrl = url;
    }
    protected BaseCache(Handler handler){
        this(handler,null);
    }

    protected abstract void putData();
    protected abstract void putData(T object);
    public synchronized void cache(){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData();
        db.setTransactionSuccessful();
        db.endTransaction();
       // db.close();
    }
    public synchronized void addToCollection(T object){
        db = mHelper.getWritableDatabase();
        db.beginTransaction();
        values = new ContentValues();
        putData(object);
        db.setTransactionSuccessful();
        db.endTransaction();
        // db.close();
    }
    public synchronized void execSQL(String sql){
        db = mHelper.getWritableDatabase();
        db.execSQL(sql);
       // db.close();
    }

    public  List<T> getmList(){
        return mList;
    }
    public boolean hasData(){
        return !mList.isEmpty();
    }

    public  abstract void loadFromCache();
    protected Cursor query(String sql){
        return mHelper.getReadableDatabase().rawQuery(sql,null);
    }


}
