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

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.DividerItemDecoration;
import com.mummyding.app.leisure.support.adapter.NewsAdapter;
import com.mummyding.app.leisure.support.sax.SAXNewsParse;
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
    private RequestQueue queue;
    private List<NewsBean> items = new ArrayList<>();
    private NewsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected static final String TYPE_UTF8_CHARSET = "charset=UTF-8";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_news,null);
        initData();
        return parentView;
    }
    void initData(){

        adapter = new NewsAdapter(getContext(),items);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        final String url = getArguments().getString("url");
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        loadNewsFromNet(url);

        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewsFromNet(url);
            }
        });
    }
    private void loadNewsFromNet(String url){
        queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {

            @TargetApi(Build.VERSION_CODES.KITKAT)

            @Override
            public void onResponse(String s) {
                InputStream is =
                        new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
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
                handler.sendEmptyMessage(0);
                refreshView.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Utils.showToast("网络异常 刷新失败");
                refreshView.setRefreshing(false);
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    String type = response.headers.get("Content-Type");
                    if (type == null) {
                        // Log.d(LOG_TAG, "content type was null");
                        type = TYPE_UTF8_CHARSET;
                        response.headers.put("Content-Type", type);
                    } else if (!type.contains("UTF-8")) {
                        //   Log.d(LOG_TAG, "content type had UTF-8 missing");
                        type += ";" + TYPE_UTF8_CHARSET;
                        response.headers.put("Content-Type", type);
                    }
                } catch (Exception e) {
                    //print stacktrace e.g.
                }
                return super.parseNetworkResponse(response);
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            return false;
        }
    });
}
