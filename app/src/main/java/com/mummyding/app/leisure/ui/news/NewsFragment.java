package com.mummyding.app.leisure.ui.news;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.cache.cache.NewsCache;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.DividerItemDecoration;
import com.mummyding.app.leisure.support.adapter.NewsAdapter;
import com.mummyding.app.leisure.support.sax.SAXDailyParse;
import com.mummyding.app.leisure.support.sax.SAXNewsParse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.yalantis.phoenix.PullToRefreshView;

import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mummyding on 15-11-13.
 */
public class NewsFragment extends Fragment {
    private View parentView;
    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<NewsBean> items = new ArrayList<>();
    private NewsAdapter adapter;
    private ImageView sad_face;
    private ProgressBar progressBar;

    private NewsCache cache ;

    private String url;
    private String category;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_news,null);
        initData();
        return parentView;
    }
    void initData(){
        adapter = new NewsAdapter(getContext(),items);
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        url = getArguments().getString(getString(R.string.id_url));
        category = getArguments().getString(getString(R.string.id_category));
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadNewsFromNet();
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
        cache = new NewsCache(LeisureApplication.AppContext);
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
                    public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                        if (response.isSuccessful() == false) {
                            handler.sendEmptyMessage(CONSTANT.ID_FAILURE);
                            return;
                        }
                        InputStream is =
                                new ByteArrayInputStream(response.body().string().getBytes(StandardCharsets.UTF_8));
                        try {
                            items.clear();
                            items.addAll(SAXNewsParse.parse(is));
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
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
                    Utils.DLog(getString(R.string.Text_Net_Exception));
                    break;
                case CONSTANT.ID_SUCCESS:
                    cache.cache(items, category);
                    items.clear();
                    loadCache();
                    break;
                case CONSTANT.ID_LOAD_FROM_NET:
                    refreshView.setRefreshing(true);
                    loadNewsFromNet();
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
    private void loadCache(){
        List<Object> tmpList = cache.loadFromCache(category);
        for (Object object : tmpList) {
            items.add((NewsBean) object);
        }
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.GONE);
            if(items.isEmpty()){
                handler.sendEmptyMessage(CONSTANT.ID_LOAD_FROM_NET);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
