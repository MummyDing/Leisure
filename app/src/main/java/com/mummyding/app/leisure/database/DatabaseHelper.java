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

package com.mummyding.app.leisure.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.database.table.NewsTable;
import com.mummyding.app.leisure.database.table.ReadingTable;
import com.mummyding.app.leisure.database.table.ScienceTable;

/**
 * Created by mummyding on 15-11-26.<br>
 * DatabaseHelper Class. it uses to manage Leisure's cache.
 * @author MummyDing
 * @version Leisure 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private  static final String DB_NAME= "Leisure";
    private static  DatabaseHelper instance = null;
    private static final int DB_VERSION = 2;
    public static final String DELETE_TABLE_DATA = "delete from ";
    public static final String DROP_TABLE = "drop table if exists ";
    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DailyTable.CREATE_TABLE);
        db.execSQL(DailyTable.CREATE_COLLECTION_TABLE);

        db.execSQL(NewsTable.CREATE_TABLE);
        db.execSQL(NewsTable.CREATE_COLLECTION_TABLE);

        db.execSQL(ReadingTable.CREATE_TABLE);
        db.execSQL(ReadingTable.CREATE_COLLECTION_TABLE);

        db.execSQL(ScienceTable.CREATE_TABLE);
        db.execSQL(ScienceTable.CREATE_COLLECTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1){
            db.execSQL(DROP_TABLE+DailyTable.NAME);
            db.execSQL(DROP_TABLE+DailyTable.COLLECTION_NAME);
            db.execSQL(DailyTable.CREATE_TABLE);
            db.execSQL(DailyTable.CREATE_COLLECTION_TABLE);
        }
    }
    public static synchronized DatabaseHelper instance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context,DB_NAME,null,DB_VERSION);
        }
        return instance;
    }
}
