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

package com.mummyding.app.leisure.ui.reading;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.database.cache.cache.ReadingCache;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.ReadingAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;

/**
 * Created by mummyding on 15-11-15.
 */
public class ReadingFragment extends BaseListFragment {

    private int pos;
    private String mCategory;
    private String [] mUrls;



    @Override
    protected void onCreateCache() {
        cache = new ReadingCache(handler,mCategory,mUrls);
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new ReadingAdapter(getContext(),cache);
    }

    @Override
    protected void loadFromNet() {
        cache.load();
    }

    @Override
    protected void loadFromCache() {
        cache.loadFromCache();
    }

    @Override
    protected boolean hasData() {
        return cache.hasData();
    }

    @Override
    protected void getArgs() {
        pos = getArguments().getInt(getString(R.string.id_pos));
        mCategory = getArguments().getString(getString(R.string.id_category));
        final String[] tags = ReadingApi.getTags(ReadingApi.getApiTag(pos));
        mUrls = new String[tags.length];
        for(int i = 0; i < tags.length;i++){
            mUrls[i] = ReadingApi.searchByTag+tags[i];
        }
    }
}
