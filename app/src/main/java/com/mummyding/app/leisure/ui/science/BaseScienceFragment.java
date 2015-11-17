package com.mummyding.app.leisure.ui.science;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mummyding.app.leisure.api.ScienceApi;
import com.mummyding.app.leisure.support.adapter.PagerAdapter;
import com.mummyding.app.leisure.ui.AbsTopNavigationFragment;

/**
 * Created by mummyding on 15-11-17.
 */
public class BaseScienceFragment extends AbsTopNavigationFragment {
    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getFragmentManager(), ScienceApi.channel_title) {
            @Override
            public Fragment getItem(int position) {
                ScienceFragment fragment = new ScienceFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
