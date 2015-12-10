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

package com.mummyding.app.leisure.ui.collection;

import android.os.Bundle;
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
        pagerAdapter = new PagerAdapter(getChildFragmentManager(),tabTitles) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new CollectionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        return pagerAdapter;
    }

    /**
     * destroy child fragments
     */
    @Override
    public void onDetach() {
        super.onDetach();
        if(getChildFragmentManager().getFragments()!=null){
            getChildFragmentManager().getFragments().clear();
        }
    }

}
