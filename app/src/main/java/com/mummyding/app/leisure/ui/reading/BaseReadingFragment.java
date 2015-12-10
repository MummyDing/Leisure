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

package com.mummyding.app.leisure.ui.reading;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.PagerAdapter;
import com.mummyding.app.leisure.ui.support.AbsTopNavigationFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mummyding on 15-11-15.
 */
public class BaseReadingFragment extends AbsTopNavigationFragment{
    private PagerAdapter pagerAdapter;
    private List<ReadingFragment> fragments = new ArrayList<>();

    @Override
    protected PagerAdapter initPagerAdapter() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), ReadingApi.Tag_Titles) {
            @Override
            public Fragment getItem(int position) {
                ReadingFragment fragment = new ReadingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.id_pos),position);
                bundle.putString(getString(R.string.id_category),ReadingApi.Tag_Titles[position]);
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
