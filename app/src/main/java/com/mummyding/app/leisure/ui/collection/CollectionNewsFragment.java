package com.mummyding.app.leisure.ui.collection;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.mummyding.app.leisure.ui.support.BaseListFragment;

/**
 * Created by mummyding on 15-12-4.
 */
public class CollectionNewsFragment extends BaseListFragment{
    @Override
    protected void onCreateCache() {

    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return null;
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
