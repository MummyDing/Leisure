/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mummyding.app.leisure.ui.support;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.PagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * Created by mummyding on 15-11-13.
 */
public abstract class AbsTopNavigationFragment extends Fragment{
    protected View parentView;
    protected ViewPager viewPager;
    protected   PagerAdapter pagerAdapter;
    private SmartTabLayout smartTabLayout;
    protected abstract  PagerAdapter initPagerAdapter();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_top_navigation,null);
        viewPager = (ViewPager) parentView.findViewById(R.id.inner_viewpager);
        smartTabLayout = (SmartTabLayout) getActivity().findViewById(R.id.tab_layout);
        smartTabLayout.setVisibility(View.VISIBLE);
        pagerAdapter = initPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        smartTabLayout.setViewPager(viewPager);
        return parentView;
    }

}
