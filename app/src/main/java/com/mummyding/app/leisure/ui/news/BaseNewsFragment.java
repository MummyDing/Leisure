package com.mummyding.app.leisure.ui.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.mummyding.app.leisure.R;
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
    private String [] name;
    private String [] url;
    @Override
    protected PagerAdapter initPagerAdapter() {
        InputStream is = Utils.readFileFromRaw(R.raw.news_api);
        Document document = Utils.getDocmentByIS(is);
        NodeList urlList = document.getElementsByTagName("url");
         NodeList nameList = document.getElementsByTagName("name");
        int nodeLength = urlList.getLength();
        name = new String[nodeLength];
        url = new String[nodeLength];

        for(int i = 0; i < nodeLength;i++){
            url[i] =urlList.item(i).getTextContent();
            name[i] =nameList.item(i).getTextContent();
        }

        pagerAdapter = new PagerAdapter(getFragmentManager(),name) {
            @Override
            public Fragment getItem(int position) {
                NewsFragment fragment = new NewsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url",url[position]);
                bundle.putString("name",name[position]);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }
}
