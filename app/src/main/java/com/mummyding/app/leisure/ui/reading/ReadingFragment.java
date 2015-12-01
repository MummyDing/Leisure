package com.mummyding.app.leisure.ui.reading;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.cache.cache.ReadingCache;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.model.reading.ReadingBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.DividerItemDecoration;
import com.mummyding.app.leisure.support.adapter.ReadingAdapter;
import com.mummyding.app.leisure.support.sax.SAXDailyParse;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.yalantis.phoenix.PullToRefreshView;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mummyding on 15-11-15.
 */
public class ReadingFragment extends Fragment {
    private View parentView;
    protected PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView sad_face;
    private ProgressBar progressBar;

    protected List<BookBean> items= new ArrayList<>();
    private List<BookBean> tmpItems = new ArrayList<>();
    private ReadingAdapter adapter;
    private int pos;
    private String category;
    private String url;
    private ReadingCache cache;
    private Thread thread;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_list, null);
        initData();
        return parentView;
    }
    protected void getData(){
        pos = getArguments().getInt(getString(R.string.id_pos));
        category = getArguments().getString(getString(R.string.id_category));
    }
    protected void initData(){
        getData();
        sad_face = (ImageView) parentView.findViewById(R.id.sad_face);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        refreshView = (PullToRefreshView) parentView.findViewById(R.id.pull_to_refresh);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        adapter = new ReadingAdapter(items,getContext());
        recyclerView.setAdapter(adapter);
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
        cache = new ReadingCache(LeisureApplication.AppContext);
        loadCache();
    }
    protected void loadNewsFromNet(){
        final String[] tags = ReadingApi.getTags(ReadingApi.getApiTag(pos));
        new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                for (int i = 0; i < ReadingApi.TAG_LEN; i++) {
                    url = ReadingApi.searchByTag + tags[i];
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
                            BookBean [] bookBeans = gson.fromJson(response.body().string(), ReadingBean.class).getBooks();
                            for(BookBean bookBean: bookBeans){
                                tmpItems.add(bookBean);
                            }
                            handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                        }
                    });
                }
            }
        }).start();

    }
    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CONSTANT.ID_FAILURE:
                    Utils.DLog(getString(R.string.Text_Net_Exception));
                    break;
                case CONSTANT.ID_SUCCESS:
                    cache.cache(tmpItems, category);
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
                List<Object> tmpList = cache.loadFromCache(category);
                for(int i = 0 ;i<tmpList.size();i++){
                    tmpItems.add((BookBean) tmpList.get(i));
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
    /*public void onDestroyView() {
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

    }*/

}
