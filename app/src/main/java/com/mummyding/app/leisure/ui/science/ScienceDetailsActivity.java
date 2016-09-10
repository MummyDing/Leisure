package com.mummyding.app.leisure.ui.science;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.database.cache.cache.ScienceCache;
import com.mummyding.app.leisure.database.table.ScienceTable;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.model.science.ScienceBean;
import com.mummyding.app.leisure.support.DisplayUtil;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.parser.ScienceContentParser;
import com.mummyding.app.leisure.ui.support.BaseDetailsActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by MummyDing on 16-2-18.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceDetailsActivity extends BaseDetailsActivity {

    private static final String TAG = "ScienceDetailsActivity";
    private ScienceCache mCache;
    private ArticleBean articleBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCache = new ScienceCache();
        articleBean = (ArticleBean) getIntent().getSerializableExtra(getString(R.string.id_science));
        isCollected = (articleBean.getIs_collected()==1? true:false);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        contentView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
    }

    @Override
    protected void onDataRefresh() {

        Utils.getRawHtmlFromUrl(articleBean.getUrl(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String rawData = response.body().string();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ScienceContentParser myParse = new ScienceContentParser(rawData);
                        String data = myParse.getEndStr();
                        scrollView.setVisibility(View.VISIBLE);
                        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                                topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
                            }
                        });
                        contentView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"guokr.css\" />" + data, "text/html", "utf-8", null);
                    }
                });
            }
        });
        if(HttpUtil.isWIFI == true || Settings.getInstance().getBoolean(Settings.NO_PIC_MODE, false) == false) {
            setMainContentBg(articleBean.getImage_info().getUrl());
        }

        hideLoading();
    }

    @Override
    protected void removeFromCollection() {
        mCache.execSQL(ScienceTable.updateCollectionFlag(articleBean.getTitle(), 0));
        mCache.execSQL(ScienceTable.deleteCollectionFlag(articleBean.getTitle()));
    }

    @Override
    protected void addToCollection() {
        mCache.execSQL(ScienceTable.updateCollectionFlag(articleBean.getTitle(),1));
        mCache.addToCollection(articleBean);
    }

    @Override
    protected String getShareInfo() {
        return "["+articleBean.getTitle()+"]:"+articleBean.getUrl()+" ( "+getString(R.string.text_share_from)+getString(R.string.app_name)+")";
    }
}
