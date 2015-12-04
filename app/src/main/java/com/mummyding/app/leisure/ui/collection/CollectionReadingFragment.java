package com.mummyding.app.leisure.ui.collection;

import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.cache.cache.Collection.CollectionReadingCache;
import com.mummyding.app.leisure.support.adapter.ReadingAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionReadingFragment extends BaseListFragment{
    @Override
    protected void onCreateCache() {
        cache = new CollectionReadingCache();
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new ReadingAdapter(getContext(),cache);
    }

    @Override
    protected void loadFromNet() {
    }

    @Override
    protected void loadFromCache() {

    }

    @Override
    protected boolean hasData() {
        return false;
    }

    @Override
    protected void getArgs() {

    }
}
