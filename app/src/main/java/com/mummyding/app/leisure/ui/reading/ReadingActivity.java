package com.mummyding.app.leisure.ui.reading;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.model.reading.ReadingBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.support.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.io.IOException;

public class ReadingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        initData();
    }
    private void initData(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        url = ReadingApi.searchByText+getIntent().getStringExtra(getString(R.string.id_search_text));
        Utils.showToast(url);
        Utils.DLog("url"+ url);
        BookListFragment fragment = new BookListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }
    class BookListFragment extends ReadingFragment{
        @Override
        protected void getData() {
        }
        @Override
        protected void loadNewsFromNet() {
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
                                BookBean[] bookBeans = gson.fromJson(response.body().string(), ReadingBean.class).getBooks();
                                for (BookBean bookBean : bookBeans) {
                                    items.add(bookBean);
                                }
                                handler.sendEmptyMessage(CONSTANT.ID_SUCCESS);
                            }
                        });
                    }
            }).start();
        }
    }
}
