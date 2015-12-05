package com.mummyding.app.leisure.support.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.database.cache.cache.BaseCollectionCache;
import com.mummyding.app.leisure.database.cache.ICache;

import java.util.List;

/**
 * Created by mummyding on 15-12-3.
 */
public abstract class BaseListAdapter<M,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    protected List<M> mItems;
    protected Context mContext;
    protected ICache<M> mCache;

    protected boolean isCollection = false;


    public BaseListAdapter(Context context, ICache<M> cache) {
        mContext = context;
        mCache = cache;
        mItems = cache.getmList();

        if(cache instanceof BaseCollectionCache){
            isCollection = true;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    protected M getItem(int position){
        return mItems.get(position);
    }


}
