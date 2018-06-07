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

package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.database.cache.BaseCollectionCache;
import com.mummyding.app.leisure.database.cache.ICache;
import com.mummyding.app.leisure.support.HttpUtil;

import java.util.List;

/**
 * Created by mummyding on 15-12-3.<br>
 * Abstract. It provides a common framework for RecyclerView adapter.<br>
 * All RecyclerView adapter inherits from this method.
 * @author MummyDing
 * @version Leisure 1.0
 */
public abstract class BaseListAdapter<M,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    protected List<M> mItems;
    protected Context mContext;
    protected ICache<M> mCache;

    protected boolean isCollection = false;


    public BaseListAdapter(Context context, ICache<M> cache) {
        mContext = context;
        mCache = cache;
        mItems = cache.getList();

        if(cache instanceof BaseCollectionCache){
            isCollection = true;
        }

        HttpUtil.readNetworkState();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    protected M getItem(int position){
        return mItems.get(position);
    }


}
