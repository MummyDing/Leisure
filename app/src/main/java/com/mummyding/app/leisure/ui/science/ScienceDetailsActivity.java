package com.mummyding.app.leisure.ui.science;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.database.cache.cache.ScienceCache;
import com.mummyding.app.leisure.database.table.ScienceTable;
import com.mummyding.app.leisure.model.science.ArticleBean;
import com.mummyding.app.leisure.model.science.ScienceBean;
import com.mummyding.app.leisure.support.DisplayUtil;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.ui.support.BaseDetailsActivity;

/**
 * Created by MummyDing on 16-2-18.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceDetailsActivity extends BaseDetailsActivity {
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
        onDataRefresh();
    }

    @Override
    protected void onDataRefresh() {
        scrollView.setVisibility(View.VISIBLE);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                topImage.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
            }
        });
        contentView.loadUrl(articleBean.getUrl());

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
