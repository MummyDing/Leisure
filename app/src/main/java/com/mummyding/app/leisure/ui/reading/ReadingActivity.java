package com.mummyding.app.leisure.ui.reading;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.database.cache.cache.ReadingCache;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.ReadingAdapter;
import com.mummyding.app.leisure.ui.support.BaseListFragment;
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
        protected boolean setRefreshView() {
            return false;
        }

        @Override
        protected void onCreateCache() {
            cache = new ReadingCache(handler,null,new String[]{url});
        }

        @Override
        protected RecyclerView.Adapter bindAdapter() {
            return new ReadingAdapter(getContext(),cache);
        }

        @Override
        protected void loadFromNet() {
            cache.load();
        }

        @Override
        protected void loadFromCache() {
            loadFromNet();
        }

        @Override
        protected boolean hasData() {
            return cache.hasData();
        }

        @Override
        protected void getArgs() {

        }

        @Override
        protected boolean setCache() {
            return false;
        }
    }
}
