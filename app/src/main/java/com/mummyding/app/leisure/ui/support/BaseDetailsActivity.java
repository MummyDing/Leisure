package com.mummyding.app.leisure.ui.support;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.DisplayUtil;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.ImageUtil;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseDetailsActivity extends SwipeBackActivity {

    private static final String TAG = "BaseDetailsActivity";
    protected Toolbar toolbar;
    protected WebView contentView;
    protected SimpleDraweeView topImage;
    protected NestedScrollView scrollView;
    protected FrameLayout mainContent;
    protected ProgressBar progressBar;
    protected ProgressBar progressBarTopPic;
    protected ImageButton networkBtn;
    protected boolean isCollected;


    protected abstract void onDataRefresh();

    private int mLang = -1;

    protected void loadSettings(){
        // Language
        mLang = Utils.getCurrentLanguage();
        if (mLang > -1) {
            Utils.changeLanguage(this, mLang);
        }

        //set Theme
        if(Settings.isNightMode){
            this.setTheme(R.style.NightTheme);
        }else{
            this.setTheme(R.style.DayTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        loadSettings();
        setContentView(getLayoutID());
    }

    protected int getLayoutID(){
        return R.layout.activity_base_details;
    }


    public void displayLoading() {
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoading() {
        if(progressBar != null){
            progressBar.setVisibility(View.GONE);
            progressBarTopPic.setVisibility(View.VISIBLE);
        }
    }

    public void displayNetworkError() {
        if(networkBtn != null){
            networkBtn.setVisibility(View.VISIBLE);
        }
    }

    protected void initView() {
        /**
         * 测试用 非正式代码 ---By MummyDing
         */

        //对toolbar进行下移
        int height = DisplayUtil.getScreenHeight(LeisureApplication.AppContext);
        LinearLayout ll = (LinearLayout) findViewById(R.id.stbar);
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) ll.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            llp.height = (int) (height * 0.03);
            ll.setLayoutParams(llp);
        }

        mainContent = (FrameLayout) findViewById(R.id.main_content);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarTopPic=(ProgressBar) findViewById(R.id.progressBarTopPic);
        networkBtn = (ImageButton) findViewById(R.id.networkBtn);
        topImage = (SimpleDraweeView) findViewById(R.id.topImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.top_gradient));
        contentView = (WebView) findViewById(R.id.content_view);

        contentView.getSettings().setJavaScriptEnabled(true);

        // 开启缓存
        contentView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        contentView.getSettings().setDomStorageEnabled(true);
        contentView.getSettings().setDatabaseEnabled(true);

        contentView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                displayNetworkError();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                contentView.loadUrl(url);
                return false;
            }
        });

        if(HttpUtil.isWIFI == false) {
            contentView.getSettings().setBlockNetworkImage(Settings.getInstance().getBoolean(Settings.NO_PIC_MODE, false));
        }else {
            // fix issue #13
            contentView.getSettings().setBlockNetworkImage(false);
        }

        /**
         * 网络异常就显示
         */
        networkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                onDataRefresh();
            }
        });

        onDataRefresh();
    }



    /**
     * 设置布局背景，其实就是边缘空隙的颜色，颜色取自顶部图片的主色调
     *
     * @param url
     */
    protected void setMainContentBg(final String url) {
        if (Utils.hasString(url) == false) {
            setDefaultColor();
            return;
        }

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();

        HttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        Log.d(TAG, "onFailure: "+ url);
                        setDefaultColor();
                    }
                });
            }
            @Override
            public void onResponse(Response response) throws IOException {
                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(bitmap == null){
                            setDefaultColor();
                            Log.d(TAG, "onResponse bitmap null: " + url);
                            return;
                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                            topImage.setBackground(new BitmapDrawable(getResources(), bitmap));
                        } else{
                            topImage.setImageURI(Uri.parse(url));
                        }
                        Log.d(TAG, "onResponse: " + url);
                        mainContent.setBackgroundColor(ImageUtil.getImageColor(bitmap));
                        progressBarTopPic.setVisibility(View.GONE);
                    }
                });
            }
        });
    }


    protected void setDefaultColor(){
        mainContent.setBackgroundColor(Color.rgb(67,76,66));
        progressBarTopPic.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share,menu);
        updateCollectionMenu(menu.findItem(R.id.menu_collect));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getShareInfo());
            startActivity(Intent.createChooser(sharingIntent,getString(R.string.hint_share_to)));
            return super.onOptionsItemSelected(item);
        }else if(item.getItemId() == R.id.menu_collect){
            if(isCollected){
                removeFromCollection();
                isCollected = false;
                updateCollectionMenu(item);
                Snackbar.make(mainContent, R.string.notify_remove_from_collection,Snackbar.LENGTH_SHORT).show();
            }else {
                addToCollection();
                isCollected = true;
                updateCollectionMenu(item);
                Snackbar.make(mainContent, R.string.notify_add_to_collection,Snackbar.LENGTH_SHORT).show();

            }
        }
        return true;
    }
    protected void updateCollectionMenu(MenuItem item){
        if(isCollected){
            item.setIcon(R.mipmap.ic_star_black);
        }else {
            item.setIcon(R.mipmap.ic_star_white);
        }
    }
    protected abstract void removeFromCollection();
    protected abstract void addToCollection();
    protected abstract String getShareInfo();

}
