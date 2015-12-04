package com.mummyding.app.leisure.ui.collection;

import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.cache.cache.Collection.CollectionDailyCache;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.adapter.DailyAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionDailyFragment extends BaseListFragment{
    @Override
    protected void onCreateCache() {
        cache = new CollectionDailyCache();
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new DailyAdapter(getContext(),cache);
    }

    @Override
    protected void loadFromNet() {
        handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
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

    }
}
