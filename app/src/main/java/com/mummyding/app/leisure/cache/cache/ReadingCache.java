package com.mummyding.app.leisure.cache.cache;

import android.content.Context;
import android.database.Cursor;

import com.mummyding.app.leisure.cache.table.ReadingTable;
import com.mummyding.app.leisure.model.reading.BookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-26.
 */
public class ReadingCache extends BaseCache{

    private ReadingTable table;
    private List<Object> readingList = new ArrayList<>();

    public ReadingCache(Context context) {
        super(context);
        table = new ReadingTable();
    }

    @Override
    protected void putData(List<? extends Object> list) {
        db.execSQL(mHelper.DROP_TABLE+table.NAME);
        db.execSQL(table.CREATE_TABLE);
        for(Object object:list){
            BookBean bookBean = (BookBean) object;
            values.put(ReadingTable.TITLE,bookBean.getTitle());
            values.put(ReadingTable.INFO,bookBean.getInfo());
            values.put(ReadingTable.IMAGE, bookBean.getImage());
            values.put(ReadingTable.IS_COLLECTED,bookBean.getIs_collected());
            db.insert(ReadingTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(Object object) {
        BookBean bookBean = (BookBean) object;
        values.put(ReadingTable.TITLE,bookBean.getTitle());
        values.put(ReadingTable.IMAGE, bookBean.getImage());
        values.put(ReadingTable.INFO,bookBean.getInfo());
        db.insert(ReadingTable.COLLECTION_NAME, null, values);
    }

    @Override
    public List<Object> loadFromCache() {
        Cursor cursor = query(table.NAME);
        while (cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(ReadingTable.ID_TITLE));
            bookBean.setImage(cursor.getString(ReadingTable.ID_IMAGE));
            bookBean.setInfo(cursor.getString(ReadingTable.ID_INFO));
            bookBean.setIs_collected(cursor.getInt(ReadingTable.ID_IS_COLLECTED));
            readingList.add(bookBean);
        }
        cursor.close();
        return readingList;
    }
}
