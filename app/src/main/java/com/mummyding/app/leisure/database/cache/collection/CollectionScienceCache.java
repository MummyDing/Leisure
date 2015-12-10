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
import com.mummyding.app.leisure.database.table.ScienceTable;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.support.CONSTANT;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionScienceCache extends BaseCollectionCache<ArticleBean> {


    private ScienceTable table;

    public CollectionScienceCache(Handler mHandler) {
        super(mHandler);
    }

    @Override
    public void loadFromCache() {
        Cursor cursor = query(table.SELECT_ALL_FROM_COLLECTION);
        while (cursor.moveToNext()){
            ArticleBean articleBean = new ArticleBean();
            articleBean.setTitle(cursor.getString(table.ID_TITLE));
            articleBean.setInfo(cursor.getString(table.ID_INFO));
            articleBean.getImage_info().setUrl(cursor.getString(table.ID_IMAGE));
            articleBean.setUrl(cursor.getString(table.ID_URL));
            articleBean.setReplies_count(cursor.getShort(table.ID_COMMENT_COUNT));
            articleBean.setSummary(cursor.getString(table.ID_DESCRIPTION));
            mList.add(articleBean);
        }
        mHandler.sendEmptyMessage(CONSTANT.ID_FROM_CACHE);
    }
}
