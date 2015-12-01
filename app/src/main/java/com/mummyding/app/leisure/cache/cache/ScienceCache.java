package com.mummyding.app.leisure.cache.cache;

import android.content.Context;
import android.database.Cursor;

import com.mummyding.app.leisure.cache.table.ScienceTable;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.support.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-26.
 */
public class ScienceCache extends BaseCache{

    private ScienceTable table;
    private List<Object> scienceList = new ArrayList<>();

    public ScienceCache(Context context) {
        super(context);
        table = new ScienceTable();
    }

    @Override
    protected void putData(List<? extends Object> list,String category) {
        db.execSQL(mHelper.DROP_TABLE+table.NAME);
        db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<list.size();i++){
            ArticleBean articleBean = (ArticleBean) list.get(i);
            values.put(ScienceTable.TITLE,articleBean.getTitle());
            values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
            values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
            values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
            values.put(ScienceTable.INFO,articleBean.getInfo());
            values.put(ScienceTable.URL,articleBean.getUrl());
            values.put(ScienceTable.CATEGORY,category);
            values.put(ScienceTable.IS_COLLECTED,articleBean.getIs_collected());
            db.insert(ScienceTable.NAME,null,values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(Object object) {
        ArticleBean articleBean = (ArticleBean) object;
        values.put(ScienceTable.TITLE,articleBean.getTitle());
        values.put(ScienceTable.DESCRIPTION,articleBean.getSummary());
        values.put(ScienceTable.IMAGE,articleBean.getImage_info().getUrl());
        values.put(ScienceTable.COMMENT_COUNT,articleBean.getReplies_count());
        values.put(ScienceTable.URL,articleBean.getUrl());
        values.put(ScienceTable.INFO,articleBean.getInfo());
        db.insert(ScienceTable.COLLECTION_NAME, null, values);
    }

    @Override
    public synchronized List<Object> loadFromCache(String category) {
        String sql = null;
        if(category == null){
            sql = "select * from "+table.NAME;
        }else {
            sql = "select * from "+table.NAME +" where "+table.CATEGORY+"=\'"+category+"\'";
        }
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            ArticleBean articleBean = new ArticleBean();
            articleBean.setTitle(cursor.getString(ScienceTable.ID_TITLE));
            articleBean.setSummary(cursor.getString(ScienceTable.ID_DESCRIPTION));
            if(articleBean.getImage_info() == null){
                Utils.DLog(" "+articleBean.getImage_info());
            }else {
                articleBean.getImage_info().setUrl(cursor.getString(ScienceTable.ID_IMAGE));
            }
            articleBean.setReplies_count(cursor.getInt(ScienceTable.ID_COMMENT_COUNT));
            articleBean.setInfo(cursor.getString(ScienceTable.ID_INFO));
            articleBean.setIs_collected(cursor.getInt(ScienceTable.ID_IS_COLLECTED));
            articleBean.setUrl(cursor.getString(ScienceTable.ID_URL));
            scienceList.add(articleBean);
        }
        cursor.close();
        return scienceList;
    }
}
