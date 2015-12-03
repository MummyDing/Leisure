package com.mummyding.app.leisure.ui.collection;

import android.support.v4.app.Fragment;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.adapter.PagerAdapter;
import com.mummyding.app.leisure.ui.support.AbsTopNavigationFragment;

/**
 * Created by mummyding on 15-12-2.
 */
public class BaseCollectionFragment extends AbsTopNavigationFragment{
    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        String [] tabTitles ={getContext().getString(R.string.daily),getContext().getString(R.string.reading)
                ,getContext().getString(R.string.news),getContext().getString(R.string.science)};
        pagerAdapter = new PagerAdapter(getFragmentManager(),tabTitles) {
            @Override
            public Fragment getItem(int position) {

                return null;
            }
        };
        return pagerAdapter;
    }
}
