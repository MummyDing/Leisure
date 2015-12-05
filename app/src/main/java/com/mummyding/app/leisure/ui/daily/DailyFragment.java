package com.mummyding.app.leisure.ui.daily;

import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.database.cache.cache.DailyCache;
import com.mummyding.app.leisure.support.adapter.DailyAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;

/**
 * Created by mummyding on 15-11-21.
 */
public class DailyFragment extends BaseListFragment{



    @Override
    protected boolean setHeaderTab() {
        return false;
    }

    @Override
    protected void onCreateCache() {
        cache = new DailyCache(getContext(),handler);
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new DailyAdapter(getContext(),cache);
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

    }
}
