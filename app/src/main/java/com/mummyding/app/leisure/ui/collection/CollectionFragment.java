package com.mummyding.app.leisure.ui.collection;

import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.cache.cache.Collection.CollectionDailyCache;
import com.mummyding.app.leisure.cache.cache.Collection.CollectionNewsCache;
import com.mummyding.app.leisure.cache.cache.Collection.CollectionReadingCache;
import com.mummyding.app.leisure.cache.cache.Collection.CollectionScienceCache;
import com.mummyding.app.leisure.cache.cache.DailyCache;
import com.mummyding.app.leisure.cache.cache.ReadingCache;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.adapter.DailyAdapter;
import com.mummyding.app.leisure.support.adapter.NewsAdapter;
import com.mummyding.app.leisure.support.adapter.ReadingAdapter;
import com.mummyding.app.leisure.support.adapter.ScienceAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionFragment extends BaseListFragment {

    private int pos;
    @Override
    protected void onCreateCache() {
        switch (pos){
            case 0:
                cache = new CollectionDailyCache();
                break;
            case 1:
                cache = new CollectionReadingCache();
                break;
            case 2:
                cache = new CollectionNewsCache();
                break;
            case 3:
                cache = new CollectionScienceCache();
                break;
        }
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        switch (pos){
            case 0:
                return new DailyAdapter(getContext(),cache);
            case 1:
                return new ReadingAdapter(getContext(),cache);
            case 2:
                return new NewsAdapter(getContext(),cache);
            case 3:
                return new ScienceAdapter(getContext(),cache);
        }
        return null;
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
        pos = getArguments().getInt(getString(R.string.id_pos));
    }
}
