package com.mummyding.app.leisure.ui.science;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ScienceApi;
import com.mummyding.app.leisure.cache.cache.ScienceCache;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.model.science.ScienceBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.DividerItemDecoration;
import com.mummyding.app.leisure.support.adapter.ScienceAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by mummyding on 15-11-17.
 */
public class ScienceFragment extends Fragment{
    private View parentView;
    private List<ArticleBean> items = new ArrayList<>();
    private List<ArticleBean> tmpitems = new ArrayList<>();
    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView sad_face;
    private ScienceAdapter adapter;
    private String url;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_common_list,null);
        initData();
        return parentView;
    }
    private void initData(){
        url = ScienceApi.science_channel_url+ScienceApi.channel_tag[getArguments().getInt(getString(R.string.id_pos))];
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        adapter = new ScienceAdapter(getContext(),items);
        recyclerView.setAdapter(adapter);
        loadNewsFromNet();
        sad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sad_face.setVisibility(View.GONE);
                loadNewsFromNet();
            }
        });
    }
    private void loadNewsFromNet(){
        refreshView.setRefreshing(true);
        new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                HttpUtil.enqueue(request, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                    }
                    @Override
                    public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                        if (response.isSuccessful() == false) {
                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                            return;
                        }
                        Gson gson = new Gson();
                        ArticleBean[] articleBeans = (gson.fromJson(response.body().string(), ScienceBean.class)).getResult();
                        tmpitems.clear();
                        for(ArticleBean articleBean: articleBeans){
                            tmpitems.add(articleBean);
                        }
                        items.addAll(tmpitems);
                        handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                    }
                });
            }
        }).start();
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ScienceCache cache = new ScienceCache(getContext());
            refreshView.setRefreshing(false);
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    Utils.DLog(getString(R.string.Text_Net_Exception));
                    List<Object> tmpList = cache.loadFromCache();
                    for(Object object:tmpList){
                        items.add((ArticleBean) object);
                    }
                    break;
                case CONSTANT.ID_SUCCESS:
                    adapter.notifyDataSetChanged();
                    cache.cache(items);
                    break;
            }
            if(items.isEmpty()){
                sad_face.setVisibility(View.VISIBLE);
            }else {
                sad_face.setVisibility(View.GONE);
            }
            return false;
        }
    });
}
