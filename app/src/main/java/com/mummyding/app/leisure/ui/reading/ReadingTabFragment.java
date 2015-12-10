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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;

/**
 * Created by mummyding on 15-11-16.
 */
public class ReadingTabFragment extends Fragment{
    private View parentview;
    private TextView textView;
    private int pos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentview = View.inflate(getContext(), R.layout.layout_reading_tab, null);
        initData();
        return parentview;
    }
    private void initData(){
        pos = getArguments().getInt(getString(R.string.id_pos));
        textView = (TextView) parentview.findViewById(R.id.text);
        textView.setText(ReadingApi.getBookInfo(pos, ReadingDetailsActivity.bookBean));
    }
}
