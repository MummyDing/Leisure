package com.mummyding.app.leisure.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mummyding.app.leisure.cache.table.DailyTable;
import com.mummyding.app.leisure.cache.table.NewsTable;
import com.mummyding.app.leisure.cache.table.ReadingTable;
import com.mummyding.app.leisure.cache.table.ScienceTable;

/**
 * Created by mummyding on 15-11-26.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private  static final String DB_NAME= "Leisure";
    private static  DatabaseHelper instance = null;
    private static final int DB_VERSION = 1;

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
        db.execSQL(ScienceTable.COMMENT_COUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static synchronized DatabaseHelper instance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context,DB_NAME,null,DB_VERSION);
        }
        return instance;
    }
}
