package com.mummyding.app.leisure.ui.news;

import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.database.cache.cache.NewsCache;
import com.mummyding.app.leisure.support.adapter.NewsAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;

/**
 * Created by mummyding on 15-11-13.
 */
public class NewsFragment extends BaseListFragment {

    private String mCategory;
    private String mUrl;

    @Override
    protected void onCreateCache() {
        cache = new NewsCache(getContext(),handler,mCategory,mUrl);
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new NewsAdapter(getContext(),cache);
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
        mUrl = getArguments().getString(getString(R.string.id_url));
        mCategory = getArguments().getString(getString(R.string.id_category));
    }
}
