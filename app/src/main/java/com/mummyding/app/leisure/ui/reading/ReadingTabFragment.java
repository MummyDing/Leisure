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
        pos = getArguments().getInt("pos");
        textView = (TextView) parentview.findViewById(R.id.text);
        textView.setText(ReadingApi.getBookInfo(pos, ReadingDetailsActivity.bookBean));
    }
}
