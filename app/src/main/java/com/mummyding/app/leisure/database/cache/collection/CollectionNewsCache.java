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

package com.mummyding.app.leisure.database.cache.collection;

import android.database.Cursor;
import android.os.Handler;

import com.mummyding.app.leisure.database.cache.BaseCollectionCache;
import com.mummyding.app.leisure.database.table.NewsTable;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.support.CONSTANT;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionNewsCache extends BaseCollectionCache<NewsBean>{

    private NewsTable table;

    public CollectionNewsCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle(cursor.getString(table.ID_TITLE));
            newsBean.setDescription(cursor.getString(table.ID_DESCRIPTION));
            newsBean.setLink(cursor.getString(table.ID_LINK));
            newsBean.setPubTime(cursor.getString(table.ID_PUBTIME));
            mList.add(newsBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
