package com.mummyding.app.leisure.ui.reading;

import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.database.cache.cache.ReadingCache;
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
        cache = new ReadingCache(getContext(),handler,mCategory,mUrls);
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
