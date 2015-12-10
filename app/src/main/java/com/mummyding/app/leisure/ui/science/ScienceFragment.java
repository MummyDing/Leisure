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

package com.mummyding.app.leisure.ui.science;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ScienceApi;
import com.mummyding.app.leisure.database.cache.cache.ScienceCache;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.ScienceAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;


/**
 * Created by mummyding on 15-11-17.
 */

public class ScienceFragment extends BaseListFragment{

    private String mCategory;
    private String mUrl;

    @Override
    protected void onCreateCache() {
        cache = new ScienceCache(handler,mCategory,mUrl);
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new ScienceAdapter(getContext(),cache);
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
        mUrl = ScienceApi.science_channel_url+ScienceApi.channel_tag[getArguments().getInt(getString(R.string.id_pos))];
        mCategory = getArguments().getString(getString(R.string.id_category));
    }
}
