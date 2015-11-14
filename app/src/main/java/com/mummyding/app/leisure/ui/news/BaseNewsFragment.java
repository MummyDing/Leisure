package com.mummyding.app.leisure.ui.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.Entity;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.PagerAdapter;
import com.mummyding.app.leisure.ui.AbsTopNavigationFragment;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-13.
 */
public class BaseNewsFragment extends AbsTopNavigationFragment {
    private PagerAdapter pagerAdapter;
    private String [] name ;
    private String [] url ;
    @Override
    protected PagerAdapter initPagerAdapter() {
        name = Entity.getNewsTitle();
        url = Entity.getNewsUrl();
        pagerAdapter = new PagerAdapter(getFragmentManager(),name) {
            @Override
            public Fragment getItem(int position) {
                Utils.DLog(name.length+" "+url.length);
                NewsFragment fragment = new NewsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url",url[position]);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
