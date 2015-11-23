package com.mummyding.app.leisure.ui.reading;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.support.adapter.PagerAdapter;
import com.mummyding.app.leisure.ui.AbsTopNavigationFragment;

/**
 * Created by mummyding on 15-11-15.
 */
public class BaseReadingFragment extends AbsTopNavigationFragment{
    private PagerAdapter pagerAdapter;
    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getFragmentManager(), ReadingApi.Tag_Titles) {
            @Override
            public Fragment getItem(int position) {
                ReadingFragment fragment = new ReadingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
