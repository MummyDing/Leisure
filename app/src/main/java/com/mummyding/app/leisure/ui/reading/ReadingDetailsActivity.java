package com.mummyding.app.leisure.ui.reading;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.api.ReadingApi;
import com.mummyding.app.leisure.model.reading.BookBean;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.support.adapter.PagerAdapter;
import com.mummyding.app.leisure.support.adapter.ReadingAdapter;
import com.mummyding.app.leisure.ui.WebViewActivity;

import static com.mummyding.app.leisure.R.id.tab_layout;

public class ReadingDetailsActivity extends AppCompatActivity {
    public static BookBean bookBean;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private SimpleDraweeView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_details);
        initData();
    }
    private void  initData(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        setSupportActionBar(toolbar);
        for(String title: ReadingApi.bookTab_Titles){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        bookBean = (BookBean) getIntent().getSerializableExtra("book");
        getSupportActionBar().setTitle(bookBean.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        adapter = new PagerAdapter(getSupportFragmentManager(),ReadingApi.bookTab_Titles) {
            @Override
            public Fragment getItem(int position) {
                ReadingTabFragment fragment = new ReadingTabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                fragment.setArguments(bundle);
                return fragment;
            }
        };
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ebook,menu);
        if(bookBean.getEbook_url()==null||bookBean.getEbook_url().equals(""))
            menu.getItem(0).setVisible(false);
        else Utils.showToast(bookBean.getEbook_url());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ebook:
                Intent intent = new Intent(ReadingDetailsActivity.this, WebViewActivity.class);
                intent.putExtra("url",ReadingApi.readEBook+Utils.RegexFind("/[0-9]+/",bookBean.getEbook_url()));
                startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
