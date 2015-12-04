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
import com.mummyding.app.leisure.ui.support.BaseListFragment;
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
public class NewsFragment extends BaseListFragment {

    private NewsCache newsCache;
    private String mCategory;
    private String mUrl;

    @Override
    protected void onCreateCache() {
        newsCache = new NewsCache(getContext(),handler,mCategory,mUrl);
    }

    @Override
    protected RecyclerView.Adapter bindAdapter() {
        return new NewsAdapter(getContext(),newsCache);
    }

    @Override
    protected void loadFromNet() {
        newsCache.load();
    }

    @Override
    protected void loadFromCache() {
        newsCache.loadFromCache();
    }

    @Override
    protected boolean hasData() {
        return newsCache.hasData();
    }

    @Override
    protected void getArgs() {
        mUrl = getArguments().getString(getString(R.string.id_url));
        mCategory = getArguments().getString(getString(R.string.id_category));
    }
}
