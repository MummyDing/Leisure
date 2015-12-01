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
    protected void putData(List<? extends Object> list,String category) {
        db.execSQL(mHelper.DROP_TABLE+table.NAME);
        db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<list.size();i++){
            BookBean bookBean = (BookBean) list.get(i);
            values.put(ReadingTable.TITLE,bookBean.getTitle());
            values.put(ReadingTable.INFO,bookBean.getInfo());
            values.put(ReadingTable.IMAGE, bookBean.getImage());
            values.put(ReadingTable.AUTHOR_INTRO,bookBean.getAuthor_intro() ==null ? "":bookBean.getAuthor_intro());
            values.put(ReadingTable.CATALOG,bookBean.getCatalog() == null? "":bookBean.getCatalog());
            values.put(ReadingTable.EBOOK_URL,bookBean.getEbook_url() == null?"":bookBean.getEbook_url());
            values.put(ReadingTable.CATEGORY,category);
            values.put(ReadingTable.SUMMARY,bookBean.getSummary() == null?"":bookBean.getSummary());
            values.put(ReadingTable.IS_COLLECTED,bookBean.getIs_collected());
            db.insert(ReadingTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(Object object) {
        BookBean bookBean = (BookBean) object;
        values.put(ReadingTable.TITLE,bookBean.getTitle());
        values.put(ReadingTable.INFO,bookBean.getInfo());
        values.put(ReadingTable.IMAGE, bookBean.getImage());
        values.put(ReadingTable.AUTHOR_INTRO,bookBean.getAuthor_intro() ==null ? "":bookBean.getAuthor_intro());
        values.put(ReadingTable.CATALOG,bookBean.getCatalog() == null? "":bookBean.getCatalog());
        values.put(ReadingTable.EBOOK_URL,bookBean.getEbook_url() == null?"":bookBean.getEbook_url());
        values.put(ReadingTable.SUMMARY,bookBean.getSummary() == null?"":bookBean.getSummary());

        db.insert(ReadingTable.COLLECTION_NAME, null, values);
    }

    @Override
    public List<Object> loadFromCache(String category) {
        String sql = null;
        if(category == null){
            sql = "select * from "+table.NAME;
        }else {
            sql = "select * from "+table.NAME +" where "+table.CATEGORY+"=\'"+category+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            BookBean bookBean = new BookBean();
            bookBean.setTitle(cursor.getString(ReadingTable.ID_TITLE));
            bookBean.setImage(cursor.getString(ReadingTable.ID_IMAGE));
            bookBean.setInfo(cursor.getString(ReadingTable.ID_INFO));
            bookBean.setAuthor_intro(cursor.getString(ReadingTable.ID_AUTHOR_INTRO));
            bookBean.setCatalog(cursor.getString(ReadingTable.ID_CATALOG));
            bookBean.setEbook_url(cursor.getString(ReadingTable.ID_EBOOK_URL));
            bookBean.setSummary(cursor.getString(ReadingTable.ID_SUMMARY));
            bookBean.setIs_collected(cursor.getInt(ReadingTable.ID_IS_COLLECTED));
            readingList.add(bookBean);
        }
        cursor.close();
        return readingList;
    }
}
