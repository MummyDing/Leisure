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
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.model.daily.StoryBean;
import com.mummyding.app.leisure.support.CONSTANT;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionDailyCache extends BaseCollectionCache<StoryBean>{
    private DailyTable table;

    public CollectionDailyCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public synchronized void loadFromCache() {
        Cursor cursor = query(DailyTable.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            StoryBean storyBean = new StoryBean();
            storyBean.setTitle(cursor.getString(DailyTable.ID_TITLE));
            storyBean.setId(cursor.getInt(DailyTable.ID_ID));
            storyBean.setImages(new String[]{cursor.getString(DailyTable.ID_IMAGE)});
            storyBean.setBody(cursor.getString(DailyTable.ID_BODY));
            storyBean.setLargepic(cursor.getString(DailyTable.ID_LARGEPIC));
            mList.add(storyBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
