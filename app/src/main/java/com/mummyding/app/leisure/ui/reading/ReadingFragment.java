package com.mummyding.app.leisure.ui.reading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mummyding.app.leisure.R;

/**
 * Created by mummyding on 15-11-15.
 */
public class ReadingFragment extends Fragment {
    private View parentView;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_list,null);
        textView = (TextView) parentView.findViewById(R.id.text);
        textView.setText(getArguments().getString("pos"));
        return parentView;
    }
}
