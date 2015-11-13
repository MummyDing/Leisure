package com.mummyding.app.leisure.support.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mummyding on 15-11-13.
 */
public abstract class PagerAdapter extends FragmentStatePagerAdapter {

    private String [] titles;
    public PagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles =titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
