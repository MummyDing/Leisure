package com.mummyding.app.leisure.ui.science;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ScienceApi;

/**
 * Created by mummyding on 15-11-17.
 */
public class ScienceFragment extends Fragment{
    private TextView textView;
    private View parentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_science_list,null);
        textView = (TextView) parentView.findViewById(R.id.text);
        textView.setText(ScienceApi.channel_title[getArguments().getInt("pos")]);
        return parentView;
    }
}
