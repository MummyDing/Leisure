package com.mummyding.app.leisure.cache.cache;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;

import com.mummyding.app.leisure.api.DailyApi;
import com.mummyding.app.leisure.cache.table.DailyTable;
import com.mummyding.app.leisure.model.daily.DailyBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.sax.SAXDailyParse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mummyding on 15-11-26.
 */
public class DailyCache extends BaseCache<DailyBean>{
    private DailyTable table;

    public DailyCache(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected void putData() {
        db.execSQL(mHelper.DROP_TABLE + table.NAME);
        db.execSQL(table.CREATE_TABLE);
        for(int i=0;i<mList.size();i++){
            DailyBean dailyBean = mList.get(i);
            values.put(DailyTable.TITLE,dailyBean.getTitle());
            values.put(DailyTable.DESCRIPTION,dailyBean.getDescription());
            values.put(DailyTable.IMAGE,dailyBean.getImage());
            values.put(DailyTable.INFO, dailyBean.getInfo());
            values.put(DailyTable.IS_COLLECTED,dailyBean.getIs_collected());
            db.insert(DailyTable.NAME, null, values);
        }
        db.execSQL(table.SQL_INIT_COLLECTION_FLAG);
    }

    @Override
    protected void putData(DailyBean dailyBean) {
        values.put(DailyTable.TITLE,dailyBean.getTitle());
        values.put(DailyTable.DESCRIPTION,dailyBean.getDescription());
        values.put(DailyTable.IMAGE, dailyBean.getImage());
        values.put(DailyTable.INFO, dailyBean.getInfo());
        db.insert(DailyTable.COLLECTION_NAME, null, values);
    }
    @Override
    public synchronized List<DailyBean> loadFromCache() {
        String sql = "select * from "+table.NAME;
        Cursor cursor = query(sql);
        while (cursor.moveToNext()){
            DailyBean dailyBean = new DailyBean();
            dailyBean.setTitle(cursor.getString(DailyTable.ID_TITLE));
            dailyBean.setImage(cursor.getString(DailyTable.ID_IMAGE));
            dailyBean.setDescription(cursor.getString(DailyTable.ID_DESCRIPTION));
            dailyBean.setInfo(cursor.getString(DailyTable.ID_INFO));
            dailyBean.setIs_collected(cursor.getInt(DailyTable.ID_IS_COLLETED));
            mList.add(dailyBean);
        }
        cursor.close();
        return mList;
    }
    public void load(){
        Request.Builder builder = new Request.Builder();
        builder.url(DailyApi.daily_url);
        Request request = builder.build();
        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
            }
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful() == false) {
                    mHandler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    return;
                }
                InputStream is =
                        new ByteArrayInputStream(response.body().string().getBytes(StandardCharsets.UTF_8));
                try {
                    mList.addAll(SAXDailyParse.parse(is));
                    is.close();
                    mHandler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
