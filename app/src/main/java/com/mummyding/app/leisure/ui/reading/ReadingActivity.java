package com.mummyding.app.leisure.ui.reading;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.model.reading.ReadingBean;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.HttpUtil;
import com.mummyding.app.leisure.ui.support.BaseListFragment;
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
        BookListFragment fragment = new BookListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }
    class BookListFragment extends BaseListFragment{

        @Override
        protected boolean setHeaderTab() {
            return false;
        }

        @Override
        protected void onCreateCache() {

        }

        @Override
        protected RecyclerView.Adapter bindAdapter() {
            return null;
        }

        @Override
        protected void loadFromNet() {

        }

        @Override
        protected void loadFromCache() {

        }

        @Override
        protected boolean hasData() {
            return false;
        }

        @Override
        protected void getArgs() {

        }
    }
}
