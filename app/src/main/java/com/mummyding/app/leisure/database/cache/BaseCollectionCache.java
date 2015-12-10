/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mummyding.app.leisure.database.cache;

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
 * Abstract Class. It provides a common framework for collection cache.<br>
 * @author MummyDing
 * @version Leisure 1.0
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
