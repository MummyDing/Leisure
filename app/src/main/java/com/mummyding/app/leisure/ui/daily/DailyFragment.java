package com.mummyding.app.leisure.ui.daily;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.DailyApi;
import com.mummyding.app.leisure.cache.cache.DailyCache;
import com.mummyding.app.leisure.model.daily.DailyBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.DailyAdapter;
import com.mummyding.app.leisure.support.adapter.DividerItemDecoration;
import com.mummyding.app.leisure.support.sax.SAXDailyParse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yalantis.phoenix.PullToRefreshView;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mummyding on 15-11-21.
 */
public class DailyFragment extends Fragment{
    private View parentView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PullToRefreshView refreshView;
    private ImageView sad_face;
    private ProgressBar progressBar;

    private List<DailyBean> items = new ArrayList<>();
    private List<DailyBean> tmpItems = new ArrayList<>();
    private DailyAdapter adapter;

    private String url = DailyApi.daily_url;
    private DailyCache cache;
    private Thread thread;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView =inflater.inflate(R.layout.layout_common_list, container, false);
        initData();
        return parentView;
    }
    private void initData(){
        getActivity().findViewById(R.id.tab_layout).setVisibility(View.GONE);
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        mLayoutManager = new LinearLayoutManager(LeisureApplication.AppContext);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new DailyAdapter(items,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewsFromNet();
            }
        });
        sad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sad_face.setVisibility(View.GONE);
                loadNewsFromNet();
            }
        });
        cache =new DailyCache(LeisureApplication.AppContext);
        loadCache();
    }

    private void loadNewsFromNet(){
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
                    public void onResponse(Response response) throws IOException {
                        if(response.isSuccessful() == false) {
                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                            return;
                        }
                        InputStream is =
                                new ByteArrayInputStream(response.body().string().getBytes(StandardCharsets.UTF_8));
                        try {
                            tmpItems.addAll(SAXDailyParse.parse(is));
                            is.close();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                    }
                });
            }
        }).start();
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            refreshView.setRefreshing(false);
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    if(isAdded()) {
                        Utils.DLog(getString(R.string.Text_Net_Exception));
                    }
                    break;
                case CONSTANT.ID_SUCCESS:
                    cache.cache(tmpItems, null);
                    loadCache();
                    break;
                case CONSTANT.ID_LOAD_FROM_NET:
                    loadNewsFromNet();
                    break;
                case CONSTANT.ID_UPDATE_UI:
                    if(items.isEmpty()){
                        sad_face.setVisibility(View.VISIBLE);
                    }else {
                        sad_face.setVisibility(View.GONE);
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });
    private synchronized void loadCache(){
         thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tmpItems.clear();
                List<Object> tmpList = cache.loadFromCache(null);
                for(int i = 0 ;i<tmpList.size();i++){
                    tmpItems.add((DailyBean) tmpList.get(i));
                }
                tmpList.clear();
                items.clear();
                items.addAll(tmpItems);
                tmpItems.clear();
                if(progressBar.getVisibility() == View.VISIBLE){
                    if(items.isEmpty()){
                        handler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_NET);
                    }
                }
                handler.sendEmptyMessage(CONSTANT.ID_UPDATE_UI);
            }
        });
        thread.start();
    }

   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        items =null;
        adapter = null;
        parentView = null;
        cache = null;
        recyclerView = null;
        mLayoutManager = null;
        refreshView = null;
        sad_face = null;
        progressBar = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        items =null;
        adapter = null;
        parentView = null;
        cache = null;
        recyclerView = null;
        mLayoutManager = null;
        refreshView = null;
        sad_face = null;
        progressBar = null;

        Utils.DLog("-------***************调用了");
    }*/
}
